package com.dfield.core;

/**
 * Representa um segmento de reta do campo de direções centrado no nó (t, x).
 *
 * @param t        coordenada horizontal do centro do segmento (no espaço da EDO)
 * @param x        coordenada vertical do centro do segmento (no espaço da EDO)
 * @param dt       largura total do segmento no domínio matemático (t2 - t1)
 * @param dx       altura total do segmento no domínio matemático (x2 - x1)
 * @param slope    inclinação avaliada m = f(t, x)
 * @param isFinite verdadeiro se a inclinação for finita e calculável
 */
public record FieldSegment(
        double t,
        double x,
        double dt,
        double dx,
        double slope,
        boolean isFinite
) {

    /**
     * Retorna a coordenada t inicial do segmento: t - dt / 2.
     */
    public double t1() {
        return t - 0.5 * dt;
    }

    /**
     * Retorna a coordenada x inicial do segmento: x - dx / 2.
     */
    public double x1() {
        return x - 0.5 * dx;
    }

    /**
     * Retorna a coordenada t final do segmento: t + dt / 2.
     */
    public double t2() {
        return t + 0.5 * dt;
    }

    /**
     * Retorna a coordenada x final do segmento: x + dx / 2.
     */
    public double x2() {
        return x + 0.5 * dx;
    }

    /**
     * Fábrica para um segmento singular/indefinido (quando f(t, x) resulta em NaN ou divisão por zero).
     */
    public static FieldSegment singular(double t, double x) {
        return new FieldSegment(t, x, 0.0, 0.0, Double.NaN, false);
    }
}