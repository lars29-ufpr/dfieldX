package com.dfield.numerical;

/**
 * Método dos Trapézios / Crank-Nicolson (Implícito, 2ª ordem):
 * Encontra x_{n+1} tal que:
 * F(x_{n+1}) = -x_{n+1} + x_n + 0.5 * dt * [f(t_n, x_n) + f(t_n + dt, x_{n+1})] = 0
 */
public final class TrapezoidalSolver implements ODESolver {

    @Override
    public double step(FirstOrderODE ode, double t, double x, double dt) {
        double fCurrent = ode.eval(t, x);
        if (!Double.isFinite(fCurrent)) {
            return Double.NaN;
        }

        double tNext = t + dt;

        // F(Yp1) = -Yp1 + Y + 0.5 * dt * (f(t, Y) + f(t + dt, Yp1))
        return NewtonRaphsonRootFinder.solve(
                yNext -> -yNext + x + 0.5 * dt * (fCurrent + ode.eval(tNext, yNext)),
                x
        );
    }

    @Override
    public String getName() {
        return "Trapezoid";
    }
}