package com.dfield.numerical;

/**
 * Método de Runge-Kutta de 2ª ordem (Ponto Médio / Heun clássico do MATLAB SharedFunctions.m):
 * k1 = dt * f(t, x)
 * k2 = dt * f(t + dt/2, x + k1/2)
 * x_{n+1} = x + k2
 */
public final class RK2Solver implements ODESolver {

    @Override
    public double step(FirstOrderODE ode, double t, double x, double dt) {
        double f1 = ode.eval(t, x);
        if (!Double.isFinite(f1)) {
            return Double.NaN;
        }
        double k1 = dt * f1;

        double f2 = ode.eval(t + 0.5 * dt, x + 0.5 * k1);
        if (!Double.isFinite(f2)) {
            return Double.NaN;
        }
        double k2 = dt * f2;

        double xNext = x + k2;
        return Double.isFinite(xNext) ? xNext : Double.NaN;
    }

    @Override
    public String getName() {
        return "RK2";
    }
}