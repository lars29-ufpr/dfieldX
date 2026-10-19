package com.dfield.numerical;

/**
 * Método de Euler Explícito (Ordem 1):
 * x_{n+1} = x_n + dt * f(t_n, x_n)
 */
public final class ForwardEulerSolver implements ODESolver {

    @Override
    public double step(FirstOrderODE ode, double t, double x, double dt) {
        double f = ode.eval(t, x);
        if (!Double.isFinite(f)) {
            return Double.NaN;
        }
        double xNext = x + dt * f;
        return Double.isFinite(xNext) ? xNext : Double.NaN;
    }

    @Override
    public String getName() {
        return "Forward Euler";
    }
}