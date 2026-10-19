package com.dfield.numerical;

/**
 * Ponto pertencente à curva solução da EDO.
 *
 * @param t valor da variável independente
 * @param x valor da variável dependente
 */
public record TrajectoryPoint(double t, double x) {

    public TrajectoryPoint {
        if (Double.isNaN(t) || Double.isNaN(x)) {
            throw new IllegalArgumentException("Coordenadas não podem ser NaN: (" + t + ", " + x + ")");
        }
    }

    /**
     * Verifica se o ponto é finito (não diverge para infinito).
     */
    public boolean isFinite() {
        return Double.isFinite(t) && Double.isFinite(x);
    }
}