package com.dfield.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class DormandPrince45Solver {

    // Coeficientes do Butcher Tableau para Dormand-Prince (DP5/4)
    private static final double C2 = 1.0 / 5.0;
    private static final double A21 = 1.0 / 5.0;

    private static final double C3 = 3.0 / 10.0;
    private static final double A31 = 3.0 / 40.0;
    private static final double A32 = 9.0 / 40.0;

    private static final double C4 = 4.0 / 5.0;
    private static final double A41 = 44.0 / 45.0;
    private static final double A42 = -56.0 / 15.0;
    private static final double A43 = 32.0 / 9.0;

    private static final double C5 = 8.0 / 9.0;
    private static final double A51 = 19372.0 / 6561.0;
    private static final double A52 = -25360.0 / 2187.0;
    private static final double A53 = 64448.0 / 6561.0;
    private static final double A54 = -212.0 / 729.0;

    private static final double A61 = 9017.0 / 3168.0;
    private static final double A62 = -355.0 / 33.0;
    private static final double A63 = 46732.0 / 5247.0;
    private static final double A64 = 49.0 / 176.0;
    private static final double A65 = -5103.0 / 18656.0;

    // Ordem 5
    private static final double B1 = 35.0 / 384.0;
    private static final double B3 = 500.0 / 1113.0;
    private static final double B4 = 125.0 / 192.0;
    private static final double B5 = -2187.0 / 6784.0;
    private static final double B6 = 11.0 / 84.0;

    // Coeficientes do estimador de erro (Ordem 5 - Ordem 4)
    private static final double E1 = 71.0 / 57600.0;
    private static final double E3 = -71.0 / 16695.0;
    private static final double E4 = 71.0 / 1920.0;
    private static final double E5 = -17253.0 / 339200.0;
    private static final double E6 = 22.0 / 525.0;
    private static final double E7 = -1.0 / 40.0;

    public record StepResult(double nextT, double nextX, double nextH, boolean accepted) {}

    private DormandPrince45Solver() {}

    /**
     * Executa um único passo adaptativo de Dormand-Prince 5(4).
     */
    public static StepResult adaptiveStep(
            BiFunction<Double, Double, Double> f,
            double t, double x, double h,
            double tol, double hMin, double hMax) {

        double k1 = f.apply(t, x);
        if (!Double.isFinite(k1)) {
            return new StepResult(t, Double.NaN, h, false);
        }

        double k2 = f.apply(t + C2 * h, x + h * A21 * k1);
        double k3 = f.apply(t + C3 * h, x + h * (A31 * k1 + A32 * k2));
        double k4 = f.apply(t + C4 * h, x + h * (A41 * k1 + A42 * k2 + A43 * k3));
        double k5 = f.apply(t + C5 * h, x + h * (A51 * k1 + A52 * k2 + A53 * k3 + A54 * k4));
        double k6 = f.apply(t + h, x + h * (A61 * k1 + A62 * k2 + A63 * k3 + A64 * k4 + A65 * k5));

        double xNext5 = x + h * (B1 * k1 + B3 * k3 + B4 * k4 + B5 * k5 + B6 * k6);
        double k7 = f.apply(t + h, xNext5);

        if (!Double.isFinite(xNext5) || !Double.isFinite(k7)) {
            return new StepResult(t, Double.NaN, h * 0.5, false);
        }

        // Estimativa de erro local
        double error = Math.abs(h * (E1 * k1 + E3 * k3 + E4 * k4 + E5 * k5 + E6 * k6 + E7 * k7));
        double scale = tol + tol * Math.abs(xNext5);
        double errorRatio = error / scale;

        // Fator de segurança para novo tamanho de passo
        double factor = 0.9 * Math.pow(Math.max(1e-4, errorRatio), -0.2);
        factor = Math.max(0.2, Math.min(5.0, factor));

        double nextH = Math.copySign(Math.min(hMax, Math.max(hMin, Math.abs(h) * factor)), h);

        if (errorRatio <= 1.0 || Math.abs(h) <= hMin) {
            return new StepResult(t + h, xNext5, nextH, true);
        } else {
            return new StepResult(t, x, nextH, false);
        }
    }
}
