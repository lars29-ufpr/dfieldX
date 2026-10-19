package com.dfield.numerical;

import com.dfield.core.ODEExpression;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Calculador e classificador de pontos de equilíbrio / pontos críticos para EDOs de 1ª ordem autônomas.
 * x' = f(x)
 */
public final class CriticalPointCalculator {

    public enum Stability {
        STABLE_ATTRACTOR("Atrator (Estável)"),
        UNSTABLE_REPULSOR("Repulsor (Instável)"),
        SEMI_STABLE("Semi-estável / Não-hiperbólico");

        private final String label;
        Stability(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    public record CriticalPoint(double xStar, double lambda, Stability stability) {}

    private static final Pattern T_TOKEN_PATTERN = Pattern.compile("(?<![a-zA-Z0-9_])t(?![a-zA-Z0-9_])");

    private CriticalPointCalculator() {}

    /**
     * Verifica se a EDO é autônoma (não depende explicitamente do tempo t).
     */
    public static boolean isAutonomous(ODEExpression ode) {
        if (ode == null) return false;

        // 1. Verificação léxica rápida
        String expr = ode.getExpressionString();
        if (expr != null && T_TOKEN_PATTERN.matcher(expr).find()) {
            // Se encontrou o token 't', verifica se causa alteração de valor numérico
            double xSample = 1.2345;
            double f1 = ode.eval(0.0, xSample);
            double f2 = ode.eval(5.678, xSample);
            if (Double.isFinite(f1) && Double.isFinite(f2) && Math.abs(f1 - f2) > 1e-9) {
                return false;
            }
        }

        // 2. Verificação de sensibilidade temporal aleatória
        double[] testX = {-2.5, 0.0, 1.0, 3.14};
        for (double x : testX) {
            double v1 = ode.eval(-10.0, x);
            double v2 = ode.eval(10.0, x);
            if (Double.isFinite(v1) && Double.isFinite(v2) && Math.abs(v1 - v2) > 1e-9) {
                return false;
            }
        }

        return true;
    }

    /**
     * Encontra e classifica todos os pontos críticos x* no intervalo [xMin, xMax].
     */
    public static List<CriticalPoint> findEquilibria(ODEExpression ode, double xMin, double xMax) {
        if (!isAutonomous(ode) || xMin >= xMax) {
            return Collections.emptyList();
        }

        int samples = 800;
        double dx = (xMax - xMin) / samples;
        List<Double> rawRoots = new ArrayList<>();

        double prevX = xMin;
        double prevF = ode.eval(0.0, prevX);

        for (int i = 1; i <= samples; i++) {
            double currX = xMin + i * dx;
            double currF = ode.eval(0.0, currX);

            if (!Double.isFinite(prevF) || !Double.isFinite(currF)) {
                prevX = currX;
                prevF = currF;
                continue;
            }

            // Exatamente zero
            if (Math.abs(currF) < 1e-12) {
                rawRoots.add(currX);
            } else if (prevF * currF < 0) {
                // Mudança de sinal: refina por bissecção + secante
                double root = refineRoot(ode, prevX, currX);
                if (Double.isFinite(root)) {
                    rawRoots.add(root);
                }
            }

            prevX = currX;
            prevF = currF;
        }

        // Remove duplicatas próximas e classifica estabilidade
        List<CriticalPoint> result = new ArrayList<>();
        double clusterTol = (xMax - xMin) * 1e-4;

        for (double r : rawRoots) {
            boolean duplicate = false;
            for (CriticalPoint cp : result) {
                if (Math.abs(cp.xStar() - r) < clusterTol) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                double eps = Math.max(1e-5, Math.abs(r) * 1e-5);
                double fPlus = ode.eval(0.0, r + eps);
                double fMinus = ode.eval(0.0, r - eps);
                double lambda = (fPlus - fMinus) / (2.0 * eps);

                Stability st;
                if (lambda < -1e-5) {
                    st = Stability.STABLE_ATTRACTOR;
                } else if (lambda > 1e-5) {
                    st = Stability.UNSTABLE_REPULSOR;
                } else {
                    st = Stability.SEMI_STABLE;
                }

                result.add(new CriticalPoint(r, lambda, st));
            }
        }

        return result;
    }

    private static double refineRoot(ODEExpression ode, double a, double b) {
        double fa = ode.eval(0.0, a);
        double fb = ode.eval(0.0, b);
        if (!Double.isFinite(fa) || !Double.isFinite(fb)) return Double.NaN;

        for (int iter = 0; iter < 40; iter++) {
            double c = 0.5 * (a + b);
            double fc = ode.eval(0.0, c);
            if (!Double.isFinite(fc) || Math.abs(fc) < 1e-10 || (b - a) < 1e-9) {
                return c;
            }
            if (fa * fc < 0) {
                b = c;
                fb = fc;
            } else {
                a = c;
                fa = fc;
            }
        }
        return 0.5 * (a + b);
    }
}
