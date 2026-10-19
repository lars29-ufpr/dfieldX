package com.dfield.numerical;

/**
 * Contrato de cálculo para um passo de integração numérica de EDOs.
 */
public interface ODESolver {

    /**
     * Avança o estado x em um passo temporal dt: x_{n+1} = Step(t_n, x_n, dt).
     *
     * @param ode a equação diferencial dx/dt = f(t, x)
     * @param t instante atual t_n
     * @param x valor de estado atual x_n
     * @param dt passo de integração (positivo para avanço, negativo para retrocesso)
     * @return o novo valor de estado x_{n+1}, ou Double.NaN em caso de divergência
     */
    double step(FirstOrderODE ode, double t, double x, double dt);

    /**
     * Retorna o identificador amigável do método para exibição na UI.
     */
    String getName();
}