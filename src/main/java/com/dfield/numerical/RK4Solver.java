package com.dfield.numerical;

/**
 * Método clássico de Runge-Kutta de 4ª ordem (RK4):
 * k1 = dt * f(t, x)
 * k2 = dt * f(t + dt/2, x + k1/2)
 * k3 = dt * f(t + dt/2, x + k2/2)
 * k4 = dt * f(t + dt, x + k3)
 * x_{n+1} = x + (k1 + 2*k2 + 2*k3 + k4) / 6
 */
public final class RK4Solver implements ODESolver {

    @Override
    public double step(FirstOrderODE ode, double t, double x, double dt) {
        double f1 = ode.eval(t, x);
        if (!Double.isFinite(f1)) return Double.NaN;
        double k1 = dt * f1;

        double f2 = ode.eval(t + 0.5 * dt, x + 0.5 * k1);
        if (!Double.isFinite(f2)) return Double.NaN;
        double k2 = dt * f2;

        double f3 = ode.eval(t + 0.5 * dt, x + 0.5 * k2);
        if (!Double.isFinite(f3)) return Double.NaN;
        double k3 = dt * f3;

        double f4 = ode.eval(t + dt, x + k3);
        if (!Double.isFinite(f4)) return Double.NaN;
        double k4 = dt * f4;

        double xNext = x + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
        return Double.isFinite(xNext) ? xNext : Double.NaN;
    }

    @Override
    public String getName() {
        return "RK4";
    }
}