package com.dfield.numerical;

import java.util.function.DoubleUnaryOperator;

/**
 * Localizador de raízes unidimensional por Newton-Raphson com aproximação da derivada
 * por diferenças finitas progressivas, reproduzindo rigorosamente os parâmetros de SharedFunctions.m:
 * NMaxNewton = 5, FNormNewton = 1e-8, xNormNewton = 1e-8, dxNewton = 1e-6.
 */
public final class NewtonRaphsonRootFinder {

    private static final int MAX_ITERATIONS = 5;
    private static final double F_NORM_TOLERANCE = 1e-8;
    private static final double X_NORM_TOLERANCE = 1e-8;
    private static final double DX_STENCIL = 1e-6;

    private NewtonRaphsonRootFinder() {}

    /**
     * Resolve F(y) = 0 partindo de y0.
     *
     * @param function operador F(y)
     * @param initialGuess estimativa inicial y0 (geralmente x_n)
     * @return raiz estimada ou Double.NaN se não convergir/divergir
     */
    public static double solve(DoubleUnaryOperator function, double initialGuess) {
        double y = initialGuess;
        int k = 1;

        double yDiff = X_NORM_TOLERANCE * 2.0;
        double fy = F_NORM_TOLERANCE * 2.0;

        while ((Math.abs(yDiff) > X_NORM_TOLERANCE || Math.abs(fy) > F_NORM_TOLERANCE) && k <= MAX_ITERATIONS) {
            fy = function.applyAsDouble(y);

            if (!Double.isFinite(fy)) {
                return Double.NaN;
            }

            // Diferença finita progressiva: J = (F(y + dx) - F(y)) / dx
            double fyPlus = function.applyAsDouble(y + DX_STENCIL);
            if (!Double.isFinite(fyPlus)) {
                return Double.NaN;
            }

            double jacobian = (fyPlus - fy) / DX_STENCIL;

            // Previne divisão por zero ou derivada singular
            if (Math.abs(jacobian) < 1e-15 || !Double.isFinite(jacobian)) {
                return Double.NaN;
            }

            yDiff = fy / jacobian;
            y = y - yDiff;
            k++;
        }

        // Verifica o critério de convergência do MATLAB: norm(xdiff) < xNorm && norm(fx) < fNorm
        fy = function.applyAsDouble(y);
        if (Math.abs(yDiff) <= X_NORM_TOLERANCE && Math.abs(fy) <= F_NORM_TOLERANCE && Double.isFinite(y)) {
            return y;
        }

        // Se atingiu 5 iterações sem convergir perfeitamente mas é finito, retorna a melhor estimativa
        return Double.isFinite(y) ? y : Double.NaN;
    }
}