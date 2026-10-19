package com.dfield.numerical;

/**
 * Representa uma Equação Diferencial Ordinária escalar de 1ª ordem da forma:
 * dx/dt = f(t, x).
 */
@FunctionalInterface
public interface FirstOrderODE {

    /**
     * Avalia a derivada dx/dt no ponto (t, x).
     *
     * @param t variável independente (horizontal)
     * @param x variável de estado (vertical)
     * @return a inclinação instantânea dx/dt
     */
    double eval(double t, double x);
}