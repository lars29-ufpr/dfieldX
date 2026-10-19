package com.dfield.ui;

import com.dfield.core.ODEExpression;
import com.dfield.numerical.CriticalPointCalculator;
import com.dfield.numerical.CriticalPointCalculator.CriticalPoint;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;

/**
 * Janela dedicada ao gráfico cartesiano de taxa de variação: x' versus x.
 * Válido para equações autônomas dx/dt = f(x).
 */
public class PhasePlotWindow extends Stage {

    private static PhasePlotWindow activeInstance = null;

    private final Canvas canvas;
    private final Label lblInfo;
    private ODEExpression currentODE;
    private double xMin;
    private double xMax;

    public PhasePlotWindow(Stage owner, ODEExpression ode, double xMin, double xMax) {
        this.currentODE = ode;
        this.xMin = xMin;
        this.xMax = xMax;

        initOwner(owner);
        initModality(Modality.NONE);
        setTitle("dfieldX — Retrato 1D: x' vs x");
        setWidth(560);
        setHeight(460);
        setMinWidth(420);
        setMinHeight(360);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0f172a;");

        lblInfo = new Label();
        lblInfo.setStyle("-fx-text-fill: #94a3b8; -fx-font-family: monospace; -fx-font-size: 12px;");
        HBox topBar = new HBox(lblInfo);
        topBar.setPadding(new Insets(10, 14, 6, 14));
        topBar.setAlignment(Pos.CENTER_LEFT);
        root.setTop(topBar);

        canvas = new Canvas(540, 370);
        root.setCenter(canvas);

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty().subtract(48));

        canvas.widthProperty().addListener((obs, oldV, newV) -> render());
        canvas.heightProperty().addListener((obs, oldV, newV) -> render());

        Scene scene = new Scene(root);
        setScene(scene);

        setOnShown(e -> render());
        setOnCloseRequest(e -> activeInstance = null);
    }

    public static void show(Stage owner, ODEExpression ode, double xMin, double xMax) {
        if (activeInstance == null) {
            activeInstance = new PhasePlotWindow(owner, ode, xMin, xMax);
            activeInstance.show();
        } else {
            activeInstance.currentODE = ode;
            activeInstance.xMin = xMin;
            activeInstance.xMax = xMax;
            activeInstance.render();
            activeInstance.toFront();
        }
    }

    public static void updateIfOpen(ODEExpression ode, double xMin, double xMax) {
        if (activeInstance != null && activeInstance.isShowing()) {
            activeInstance.currentODE = ode;
            activeInstance.xMin = xMin;
            activeInstance.xMax = xMax;
            activeInstance.render();
        }
    }

    private void render() {
        if (currentODE == null || canvas.getWidth() <= 0 || canvas.getHeight() <= 0) return;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.setFill(Color.web("#0f172a"));
        gc.fillRect(0, 0, w, h);

        double padL = 60, padR = 25, padT = 25, padB = 40;
        double plotW = w - padL - padR;
        double plotH = h - padT - padB;
        if (plotW <= 10 || plotH <= 10) return;

        int samples = 350;
        double dx = (xMax - xMin) / samples;
        double fMin = Double.POSITIVE_INFINITY;
        double fMax = Double.NEGATIVE_INFINITY;

        double[] xVals = new double[samples + 1];
        double[] fVals = new double[samples + 1];

        for (int i = 0; i <= samples; i++) {
            double x = xMin + i * dx;
            double f = currentODE.eval(0.0, x);
            xVals[i] = x;
            fVals[i] = f;
            if (Double.isFinite(f)) {
                if (f < fMin) fMin = f;
                if (f > fMax) fMax = f;
            }
        }

        if (Double.isInfinite(fMin) || Double.isInfinite(fMax) || fMin == fMax) {
            fMin = -2.0;
            fMax = 2.0;
        }

        double maxAbs = Math.max(Math.abs(fMin), Math.abs(fMax));
        if (maxAbs < 1e-4) maxAbs = 1.0;
        maxAbs *= 1.15;
        fMin = -maxAbs;
        fMax = maxAbs;

        lblInfo.setText(String.format(Locale.US, "x: [%.2f, %.2f] | Faixa x': [%.2f, %.2f]", xMin, xMax, fMin, fMax));

        
        final double finalFMin = fMin;
        final double finalFMax = fMax;
        final double curXMin = xMin;
        final double curXMax = xMax;
        java.util.function.DoubleUnaryOperator toScreenX = (x) -> padL + ((x - curXMin) / (curXMax - curXMin)) * plotW;
        java.util.function.DoubleUnaryOperator toScreenY = (f) -> padT + ((finalFMax - f) / (finalFMax - finalFMin)) * plotH;

        // Borda sutil da área do gráfico
        gc.setStroke(Color.web("#1e293b"));
        gc.setLineWidth(1.0);
        gc.strokeRect(padL, padT, plotW, plotH);

        // Eixo horizontal (x' = 0)
        double zeroY = toScreenY.applyAsDouble(0.0);
        gc.setStroke(Color.web("#475569"));
        gc.setLineWidth(1.5);
        gc.strokeLine(padL, zeroY, padL + plotW, zeroY);

        gc.setFill(Color.web("#94a3b8"));
        gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 11));
        gc.fillText("x' = 0", padL + plotW - 42, zeroY - 6);
        gc.fillText("x", padL + plotW + 6, zeroY + 4);

        // Curva f(x)
        gc.setStroke(Color.web("#38bdf8"));
        gc.setLineWidth(2.2);
        boolean inSegment = false;
        gc.beginPath();

        for (int i = 0; i <= samples; i++) {
            double f = fVals[i];
            if (Double.isFinite(f)) {
                double sx = toScreenX.applyAsDouble(xVals[i]);
                double sy = toScreenY.applyAsDouble(Math.max(fMin, Math.min(fMax, f)));
                if (!inSegment) {
                    gc.moveTo(sx, sy);
                    inSegment = true;
                } else {
                    gc.lineTo(sx, sy);
                }
            } else {
                inSegment = false;
            }
        }
        gc.stroke();

        // Raízes / Equilíbrios no eixo x' = 0
        List<CriticalPoint> equilibria = CriticalPointCalculator.findEquilibria(currentODE, xMin, xMax);
        for (CriticalPoint cp : equilibria) {
            double cx = toScreenX.applyAsDouble(cp.xStar());
            double cy = zeroY;

            if (cp.stability() == CriticalPointCalculator.Stability.STABLE_ATTRACTOR) {
                gc.setFill(Color.web("#10b981"));
                gc.fillOval(cx - 5, cy - 5, 10, 10);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1.2);
                gc.strokeOval(cx - 5, cy - 5, 10, 10);
            } else if (cp.stability() == CriticalPointCalculator.Stability.UNSTABLE_REPULSOR) {
                gc.setFill(Color.web("#ef4444"));
                gc.fillOval(cx - 5, cy - 5, 10, 10);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1.2);
                gc.strokeOval(cx - 5, cy - 5, 10, 10);
            } else {
                gc.setFill(Color.web("#94a3b8"));
                gc.fillOval(cx - 4, cy - 4, 8, 8);
            }

            gc.setFill(Color.web("#f8fafc"));
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 10));
            String txt = String.format(Locale.US, "x*=%.2f", cp.xStar());
            gc.fillText(txt, cx - 18, (cp.lambda() < 0) ? cy + 18 : cy - 8);
        }
    }
}
