package com.dfield.numerical;

import com.dfield.core.ODEExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Extrai segmentos de reta que aproximam a curva de nível f(t, x) = c
 * em uma malha retangular através de interpolação linear nas arestas (Marching Squares).
 */
public class IsoclineCalculator {

    public record Segment(double t1, double x1, double t2, double x2) {}

    public static List<Segment> compute(ODEExpression ode, double c,
                                        double tMin, double tMax,
                                        double xMin, double xMax,
                                        int gridCols, int gridRows) {
        List<Segment> segments = new ArrayList<>();
        if (ode == null || gridCols < 2 || gridRows < 2) return segments;

        double dt = (tMax - tMin) / (gridCols - 1);
        double dx = (xMax - xMin) / (gridRows - 1);

        double[][] val = new double[gridCols][gridRows];
        for (int i = 0; i < gridCols; i++) {
            double t = tMin + i * dt;
            for (int j = 0; j < gridRows; j++) {
                double x = xMin + j * dx;
                try {
                    val[i][j] = ode.eval(t, x) - c;
                } catch (Exception e) {
                    val[i][j] = Double.NaN;
                }
            }
        }

        for (int i = 0; i < gridCols - 1; i++) {
            double tLeft = tMin + i * dt;
            double tRight = tLeft + dt;

            for (int j = 0; j < gridRows - 1; j++) {
                double xBottom = xMin + j * dx;
                double xTop = xBottom + dx;

                double bl = val[i][j];
                double br = val[i + 1][j];
                double tr = val[i + 1][j + 1];
                double tl = val[i][j + 1];

                if (Double.isNaN(bl) || Double.isNaN(br) || Double.isNaN(tr) || Double.isNaN(tl)) {
                    continue;
                }

                int mask = 0;
                if (bl > 0) mask |= 1;
                if (br > 0) mask |= 2;
                if (tr > 0) mask |= 4;
                if (tl > 0) mask |= 8;

                if (mask == 0 || mask == 15) continue;

                double[] pBottom = (mask & 3) == 1 || (mask & 3) == 2 ?
                        new double[]{tLeft + interp(bl, br) * dt, xBottom} : null;

                double[] pRight = ((mask >> 1) & 3) == 1 || ((mask >> 1) & 3) == 2 ?
                        new double[]{tRight, xBottom + interp(br, tr) * dx} : null;

                double[] pTop = ((mask >> 2) & 3) == 1 || ((mask >> 2) & 3) == 2 ?
                        new double[]{tLeft + interp(tl, tr) * dt, xTop} : null;

                double[] pLeft = (mask == 1 || mask == 8 || mask == 7 || mask == 14 || mask == 6 || mask == 9) ?
                        new double[]{tLeft, xBottom + interp(bl, tl) * dx} : null;

                switch (mask) {
                    case 1: case 14:
                        if (pBottom != null && pLeft != null) segments.add(new Segment(pBottom[0], pBottom[1], pLeft[0], pLeft[1]));
                        break;
                    case 2: case 13:
                        if (pBottom != null && pRight != null) segments.add(new Segment(pBottom[0], pBottom[1], pRight[0], pRight[1]));
                        break;
                    case 3: case 12:
                        if (pLeft != null && pRight != null) segments.add(new Segment(pLeft[0], pLeft[1], pRight[0], pRight[1]));
                        break;
                    case 4: case 11:
                        if (pTop != null && pRight != null) segments.add(new Segment(pTop[0], pTop[1], pRight[0], pRight[1]));
                        break;
                    case 5:
                        if (pBottom != null && pRight != null) segments.add(new Segment(pBottom[0], pBottom[1], pRight[0], pRight[1]));
                        if (pTop != null && pLeft != null) segments.add(new Segment(pTop[0], pTop[1], pLeft[0], pLeft[1]));
                        break;
                    case 10:
                        if (pBottom != null && pLeft != null) segments.add(new Segment(pBottom[0], pBottom[1], pLeft[0], pLeft[1]));
                        if (pTop != null && pRight != null) segments.add(new Segment(pTop[0], pTop[1], pRight[0], pRight[1]));
                        break;
                    case 6: case 9:
                        if (pBottom != null && pTop != null) segments.add(new Segment(pBottom[0], pBottom[1], pTop[0], pTop[1]));
                        break;
                    case 7: case 8:
                        if (pTop != null && pLeft != null) segments.add(new Segment(pTop[0], pTop[1], pLeft[0], pLeft[1]));
                        break;
                }
            }
        }
        return segments;
    }

    private static double interp(double v1, double v2) {
        double diff = v2 - v1;
        if (Math.abs(diff) < 1e-12) return 0.5;
        double t = -v1 / diff;
        return Math.max(0.0, Math.min(1.0, t));
    }
}
