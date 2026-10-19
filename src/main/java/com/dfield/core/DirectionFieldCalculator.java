package com.dfield.core;

import com.dfield.numerical.FirstOrderODE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Motor de discretização e cálculo vetorial do campo de direções.
 * <p>
 * Transpõe a lógica geométrica do {@code SharedFunctions.m} do MATLAB, aplicando:
 * 1. Recuo de borda não-linear (anti-clipping padding) para evitar que setas toquem a moldura gráfica.
 * 2. Normalização visual homogênea: garante que o comprimento de cada segmento na tela
 *    (em pixels) seja estritamente uniforme e invariante à distorção de aspecto do canvas.
 */
public final class DirectionFieldCalculator {

    private DirectionFieldCalculator() {}

    /**
     * Calcula a malha de segmentos do campo de direções.
     *
     * @param ode             equação diferencial a ser avaliada
     * @param tMin            limite inferior do eixo horizontal
     * @param tMax            limite superior do eixo horizontal
     * @param xMin            limite inferior do eixo vertical
     * @param xMax            limite superior do eixo vertical
     * @param numGridPointsT  número de nós horizontais (densidade em t)
     * @param numGridPointsX  número de nós verticais (densidade em x)
     * @param canvasWidth     largura da área de plotagem em pixels
     * @param canvasHeight    altura da área de plotagem em pixels
     * @param segmentLengthPx comprimento desejado de cada segmento na tela em pixels
     * @return lista imutável com todos os segmentos calculados da malha
     */
    public static List<FieldSegment> calculate(
            FirstOrderODE ode,
            double tMin,
            double tMax,
            double xMin,
            double xMax,
            int numGridPointsT,
            int numGridPointsX,
            double canvasWidth,
            double canvasHeight,
            double segmentLengthPx
    ) {
        Objects.requireNonNull(ode, "A equação EDO não pode ser nula.");

        if (tMax <= tMin) {
            throw new IllegalArgumentException("tMax deve ser estritamente maior que tMin.");
        }
        if (xMax <= xMin) {
            throw new IllegalArgumentException("xMax deve ser estritamente maior que xMin.");
        }
        if (numGridPointsT < 2 || numGridPointsX < 2) {
            throw new IllegalArgumentException("A densidade da malha deve ser de pelo menos 2 pontos por eixo.");
        }
        if (canvasWidth <= 0 || canvasHeight <= 0) {
            return Collections.emptyList();
        }

        double deltaT = tMax - tMin;
        double deltaX = xMax - xMin;

        // Fatores de escala da tela: pixels por unidade matemática
        double scaleT = canvasWidth / deltaT;
        double scaleX = canvasHeight / deltaX;

        // 1. Recuo de borda não linear conforme SharedFunctions.m: delta / (N^1.5)
        double tStart = tMin + deltaT / Math.pow(numGridPointsT, 1.5);
        double tEnd = tMax - deltaT / Math.pow(numGridPointsT, 1.5);

        double xStart = xMin + deltaX / Math.pow(numGridPointsX, 1.5);
        double xEnd = xMax - deltaX / Math.pow(numGridPointsX, 1.5);

        double stepT = (tEnd - tStart) / (numGridPointsT - 1);
        double stepX = (xEnd - xStart) / (numGridPointsX - 1);

        int totalSegments = numGridPointsT * numGridPointsX;
        List<FieldSegment> segments = new ArrayList<>(totalSegments);

        // 2. Varredura cartesiana da malha
        for (int i = 0; i < numGridPointsT; i++) {
            double t = tStart + i * stepT;

            for (int j = 0; j < numGridPointsX; j++) {
                double x = xStart + j * stepX;

                double slope = ode.eval(t, x);

                if (!Double.isFinite(slope)) {
                    // Ponto de descontinuidade matemática ou assíntota singular
                    segments.add(FieldSegment.singular(t, x));
                    continue;
                }

                /*
                 * 3. Normalização Anisotrópica Homogênea em Pixels:
                 * No espaço de tela:
                 * du = scaleT * dt
                 * dv = scaleX * dx = scaleX * (slope * dt)
                 *
                 * Desejamos que o comprimento euclidiano em pixels seja exatamente L = segmentLengthPx:
                 * du^2 + dv^2 = L^2
                 * (scaleT * dt)^2 + (scaleX * slope * dt)^2 = L^2
                 * dt = L / sqrt(scaleT^2 + (scaleX * slope)^2)
                 * dx = slope * dt
                 */
                double denom = Math.hypot(scaleT, scaleX * slope);

                if (denom < 1e-15 || !Double.isFinite(denom)) {
                    // Previne divisão por zero numérica
                    segments.add(FieldSegment.singular(t, x));
                    continue;
                }

                double dt = segmentLengthPx / denom;
                double dx = slope * dt;

                segments.add(new FieldSegment(t, x, dt, dx, slope, true));
            }
        }

        return Collections.unmodifiableList(segments);
    }
}