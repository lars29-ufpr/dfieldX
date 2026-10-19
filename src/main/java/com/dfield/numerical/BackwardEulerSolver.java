package com.dfield.numerical;

/**
 * Euler Implícito / Backward Euler (1ª ordem):
 * Encontra x_{n+1} tal que:
 * F(x_{n+1}) = -x_{n+1} + x_n + dt * f(t_n + dt, x_{n+1}) = 0
 */
public final class BackwardEulerSolver implements ODESolver {

    @Override
    public double step(FirstOrderODE ode, double t, double x, double dt) {
        double fCurrent = ode.eval(t, x);
        if (!Double.isFinite(fCurrent)) {
            return Double.NaN;
        }

        double tNext = t + dt;

        // F(xNext) = -xNext + x + dt * f(t + dt, xNext)
        // Chute inicial: passo explícito de Euler x + dt * fCurrent
        double initialGuess = x + dt * fCurrent;

        return NewtonRaphsonRootFinder.solve(
                xNext -> -xNext + x + dt * ode.eval(tNext, xNext),
                Double.isFinite(initialGuess) ? initialGuess : x
        );
    }

    @Override
    public String getName() {
        return "Backward Euler";
    }
}
