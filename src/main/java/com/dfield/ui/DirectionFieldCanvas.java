package com.dfield.ui;
import com.dfield.numerical.CriticalPointCalculator;
import com.dfield.numerical.CriticalPointCalculator.CriticalPoint;

import com.dfield.core.FieldSegment;
import com.dfield.numerical.OrbitTrajectory;
import com.dfield.numerical.TrajectoryPoint;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Componente gráfico interativo para renderização de campos de direções e curvas solução.
 * <p>
 * Implementa transformações de coordenadas bidirecionais (Mundo <-> Tela),
 * grelha cartesiana adaptativa, desenho vetorial com anti-aliasing e eventos de mouse.
 */
public class DirectionFieldCanvas extends Canvas {
    private boolean showCriticalPoints = false;
    private java.util.List<CriticalPoint> criticalPoints = java.util.Collections.emptyList();


    private java.util.List<com.dfield.numerical.IsoclineCalculator.Segment> isoclineSegments = java.util.Collections.emptyList();
    private java.util.List<com.dfield.numerical.IsoclineCalculator.Segment> zeroIsoclineSegments = java.util.Collections.emptyList();
    private boolean showIsoclines = false;
    private boolean lockZeroIsocline = true;

    // --- Paleta de Cores do Tema Dark (pplaneX) ---
    private static final Color COLOR_BACKGROUND = Color.web("#12161f");
    private static final Color COLOR_GRID_LINES = Color.web("#1c2438");
    private static final Color COLOR_AXES_ACCENT = Color.web("#00d2d3");
    private static final Color COLOR_AXES_BORDER = Color.web("#2c3e50");
    private static final Color COLOR_TEXT_MUTED  = Color.web("#8395a7");
    private static final Color COLOR_FIELD_SEGMENTS = Color.web("#576574");

    // Paleta cíclica para múltiplas órbitas em alto contraste
    private static final List<Color> ORBIT_COLORS = List.of(
            Color.web("#2ecc71"), // Verde-esmeralda
            Color.web("#ff6b6b"), // Coral vivo
            Color.web("#feca57"), // Amarelo sol
            Color.web("#48dbfb"), // Ciano elétrico
            Color.web("#ff9ff3"), // Rosa choque
            Color.web("#54a0ff"), // Azul néon
            Color.web("#1dd1a1"), // Menta
            Color.web("#ff9f43")  // Laranja intenso
    );

    // Tipografia de escala
    private static final Font FONT_TICKS = Font.font("Consolas", FontWeight.NORMAL, 10.5);

    // --- Estado do Domínio Matemático ---
    private double tMin = -2.0;
    private double tMax = 4.0;
    private double xMin = -2.0;
    private double xMax = 4.0;

    // --- Dados a Renderizar ---
    private List<FieldSegment> fieldSegments = Collections.emptyList();
    private final List<OrbitTrajectory> trajectories = new ArrayList<>();

    // --- Callbacks de Interação ---
    private BiConsumer<Double, Double> onMouseMove;
    private BiConsumer<Double, Double> onInitialPointSelected;

    public DirectionFieldCanvas() {
        initEventHandlers();

        // Redesenha sempre que a largura ou altura do canvas for redimensionada
        widthProperty().addListener(obs -> redraw());
        heightProperty().addListener(obs -> redraw());
    }

    /**
     * Permite que o Canvas responda a redimensionamentos no JavaFX.
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    // =========================================================================
    // 1. MAPEAMENTO DE COORDENADAS (Mundo <-> Tela)
    // =========================================================================

    /**
     * Mapeia a coordenada matemática t para a coordenada horizontal em pixels.
     */
    public double toCanvasX(double t) {
        double width = getWidth();
        return width * (t - tMin) / (tMax - tMin);
    }

    /**
     * Mapeia a coordenada matemática x para a coordenada vertical em pixels,
     * invertendo o eixo vertical (pois no JavaFX y cresce para baixo).
     */
    public double toCanvasY(double x) {
        double height = getHeight();
        return height * (1.0 - (x - xMin) / (xMax - xMin));
    }

    /**
     * Converte uma posição horizontal em pixels na coordenada matemática t.
     */
    public double toWorldT(double canvasX) {
        double width = getWidth();
        if (width <= 0) return tMin;
        return tMin + (canvasX / width) * (tMax - tMin);
    }

    /**
     * Converte uma posição vertical em pixels na coordenada matemática x.
     */
    public double toWorldX(double canvasY) {
        double height = getHeight();
        if (height <= 0) return xMin;
        return xMin + (1.0 - (canvasY / height)) * (xMax - xMin);
    }

    // =========================================================================
    // 2. CONFIGURAÇÃO DE ESTADO
    // =========================================================================

    public void setDomain(double tMin, double tMax, double xMin, double xMax) {
        if (tMax <= tMin || xMax <= xMin) {
            throw new IllegalArgumentException("Limites de eixos inválidos: max deve ser estritamente maior que min.");
        }
        this.tMin = tMin;
        this.tMax = tMax;
        this.xMin = xMin;
        this.xMax = xMax;
        redraw();
    }

    public void setFieldSegments(List<FieldSegment> segments) {
        this.fieldSegments = segments != null ? List.copyOf(segments) : Collections.emptyList();
        redraw();
    }

    public void setTrajectories(List<OrbitTrajectory> trajectories) {
        this.trajectories.clear();
        if (trajectories != null) {
            this.trajectories.addAll(trajectories);
        }
        redraw();
    }

        public List<OrbitTrajectory> getTrajectories() {
        return Collections.unmodifiableList(this.trajectories);
    }

    public void addTrajectory(OrbitTrajectory trajectory) {
        if (trajectory != null) {
            this.trajectories.add(trajectory);
            redraw();
        }
    }

        
    public void setCriticalPoints(java.util.List<CriticalPoint> points, boolean show) {
        this.criticalPoints = (points != null) ? points : java.util.Collections.emptyList();
        this.showCriticalPoints = show;
        redraw();
    }

    public void setIsoclineData(java.util.List<com.dfield.numerical.IsoclineCalculator.Segment> dynamicSegments,
                                java.util.List<com.dfield.numerical.IsoclineCalculator.Segment> zeroSegments,
                                boolean show, boolean lockZero) {
        this.isoclineSegments = dynamicSegments != null ? dynamicSegments : java.util.Collections.emptyList();
        this.zeroIsoclineSegments = zeroSegments != null ? zeroSegments : java.util.Collections.emptyList();
        this.showIsoclines = show;
        this.lockZeroIsocline = lockZero;
        redraw();
    }

    public void clearTrajectories() {
        this.trajectories.clear();
        redraw();
    }

    public void setOnMouseMove(BiConsumer<Double, Double> listener) {
        this.onMouseMove = listener;
    }

    public void setOnInitialPointSelected(BiConsumer<Double, Double> listener) {
        this.onInitialPointSelected = listener;
    }

    @FunctionalInterface
    public interface BoundsChangeListener {
        void onBoundsChanged(double tMin, double tMax, double xMin, double xMax);
    }

    private BoundsChangeListener onBoundsChanged;
    private double dragAnchorX;
    private double dragAnchorY;
    private boolean isPanning = false;

    public void setOnBoundsChanged(BoundsChangeListener listener) {
        this.onBoundsChanged = listener;
    }


    // =========================================================================
    // 3. PIPELINE DE RENDERIZAÇÃO
    // =========================================================================

    /**
     * Executa a repintura completa do canvas.
     */
    public void redraw() {
        double w = getWidth();
        double h = getHeight();

        if (w <= 0 || h <= 0) {
            return;
        }

        GraphicsContext gc = getGraphicsContext2D();

        // 1. Limpa o fundo
        gc.setFill(COLOR_BACKGROUND);
        gc.fillRect(0, 0, w, h);

        // 2. Renderiza a grelha e eixos coordenados
        drawCartesianGrid(gc, w, h);

        // 3. Renderiza os segmentos de inclinação do campo
        drawDirectionField(gc);

        // 3.5 Renderiza as Isóclinas
        drawIsoclines(gc);
        // 3.6 Renderiza Pontos Críticos / Equilíbrios
        drawCriticalPoints(gc);

        // 4. Renderiza as órbitas/curvas solução
        drawTrajectories(gc);
    }

    /**
     * Exporta um snapshot do plano (t, x) nos modos Escuro, Branco ou Transparente.
     */
    /**
     * Exporta o gráfico vetorial completo (eixos, malha, campo e trajetórias) em formato SVG.
     */
    public void exportSvg(java.io.File file, Color targetBg) throws java.io.IOException {
        double w = getWidth();
        double h = getHeight();
        if (w <= 0 || h <= 0) return;

        boolean isLight = targetBg != null && !targetBg.equals(Color.TRANSPARENT) && targetBg.getBrightness() > 0.5;
        String gridColor = isLight ? "#dfe4ea" : "#1c2438";
        String axisColor = isLight ? "#2f3542" : "#00d2d3";
        String segmentColor = isLight ? "#747d8c" : "#576574";

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(java.util.Locale.US,
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<svg width=\"%.1f\" height=\"%.1f\" viewBox=\"0 0 %.1f %.1f\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n", w, h, w, h));

        if (targetBg != null && !targetBg.equals(Color.TRANSPARENT)) {
            String hexBg = String.format("#%02x%02x%02x",
                (int)(targetBg.getRed() * 255),
                (int)(targetBg.getGreen() * 255),
                (int)(targetBg.getBlue() * 255));
            sb.append(String.format(java.util.Locale.US,
                "  <rect width=\"%.1f\" height=\"%.1f\" fill=\"%s\"/>\n", w, h, hexBg));
        }

        int numTicks = 6;
        double dtTick = (tMax - tMin) / numTicks;
        for (int i = 0; i <= numTicks; i++) {
            double tVal = tMin + i * dtTick;
            double cx = toCanvasX(tVal);
            sb.append(String.format(java.util.Locale.US,
                "  <line x1=\"%.2f\" y1=\"0\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"%s\" stroke-width=\"1\"/>\n",
                cx, cx, h, gridColor));
        }
        double dxTick = (xMax - xMin) / numTicks;
        for (int i = 0; i <= numTicks; i++) {
            double xVal = xMin + i * dxTick;
            double cy = toCanvasY(xVal);
            sb.append(String.format(java.util.Locale.US,
                "  <line x1=\"0\" y1=\"%.2f\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"%s\" stroke-width=\"1\"/>\n",
                cy, w, cy, gridColor));
        }

        if (tMin <= 0 && tMax >= 0) {
            double cx0 = toCanvasX(0);
            sb.append(String.format(java.util.Locale.US,
                "  <line x1=\"%.2f\" y1=\"0\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"%s\" stroke-width=\"1.8\"/>\n",
                cx0, cx0, h, axisColor));
        }
        if (xMin <= 0 && xMax >= 0) {
            double cy0 = toCanvasY(0);
            sb.append(String.format(java.util.Locale.US,
                "  <line x1=\"0\" y1=\"%.2f\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"%s\" stroke-width=\"1.8\"/>\n",
                cy0, w, cy0, axisColor));
        }

        sb.append("  <g stroke=\"").append(segmentColor).append("\" stroke-width=\"1.2\" stroke-linecap=\"round\">\n");
        for (var seg : fieldSegments) {
            double x1 = toCanvasX(seg.t1());
            double y1 = toCanvasY(seg.x1());
            double x2 = toCanvasX(seg.t2());
            double y2 = toCanvasY(seg.x2());
            sb.append(String.format(java.util.Locale.US,
                "    <line x1=\"%.2f\" y1=\"%.2f\" x2=\"%.2f\" y2=\"%.2f\"/>\n",
                x1, y1, x2, y2));
        }
        sb.append("  </g>\n");

        for (int i = 0; i < trajectories.size(); i++) {
            OrbitTrajectory traj = trajectories.get(i);
            Color oc = ORBIT_COLORS.get(i % ORBIT_COLORS.size());
            String hexColor = String.format("#%02x%02x%02x",
                (int)(oc.getRed() * 255), (int)(oc.getGreen() * 255), (int)(oc.getBlue() * 255));

            var pts = traj.getFullOrderedTrajectory();
            if (pts.isEmpty()) continue;

            sb.append(String.format("  <polyline fill=\"none\" stroke=\"%s\" stroke-width=\"2.0\" stroke-linecap=\"round\" stroke-linejoin=\"round\" points=\"", hexColor));
            for (int pIdx = 0; pIdx < pts.size(); pIdx++) {
                var pt = pts.get(pIdx);
                double cx = toCanvasX(pt.t());
                double cy = toCanvasY(pt.x());
                sb.append(String.format(java.util.Locale.US, "%.2f,%.2f ", cx, cy));
            }
            sb.append("\"/>\n");
        }

        sb.append("</svg>\n");

        try (java.io.FileWriter fw = new java.io.FileWriter(file, java.nio.charset.StandardCharsets.UTF_8)) {
            fw.write(sb.toString());
        }
    }

    public javafx.scene.image.WritableImage exportSnapshot(Color targetBg) {
        double w = getWidth();
        double h = getHeight();
        if (w <= 0 || h <= 0) return null;

        GraphicsContext gc = getGraphicsContext2D();

        // 1. Limpeza conforme o fundo solicitado
        if (targetBg == null || targetBg.equals(Color.TRANSPARENT)) {
            gc.clearRect(0, 0, w, h);
        } else {
            gc.setFill(targetBg);
            gc.fillRect(0, 0, w, h);
        }

        // 2. Renderização dos elementos gráficos
        drawCartesianGrid(gc, w, h);
        drawDirectionField(gc);
        drawTrajectories(gc);

        // 3. Captura do snapshot com o fundo parametrizado
        javafx.scene.SnapshotParameters params = new javafx.scene.SnapshotParameters();
        params.setFill(targetBg != null ? targetBg : Color.TRANSPARENT);
        javafx.scene.image.WritableImage snapshot = snapshot(params, null);

        // 4. Restaura o tema escuro nativo na tela do usuário
        redraw();

        return snapshot;
    }

    /**
     * Renderiza a grade de fundo, marcas de escala e valores numéricos dos eixos.
     */
    private void drawCartesianGrid(GraphicsContext gc, double w, double h) {
        gc.save();
        gc.setFont(FONT_TICKS);

        // Número de divisões adaptativas da malha
        int numTicks = 6;

        gc.setLineWidth(1.0);
        gc.setStroke(COLOR_GRID_LINES);

        // Linhas de grade verticais e marcações no eixo t
        double dtTick = (tMax - tMin) / numTicks;
        for (int i = 0; i <= numTicks; i++) {
            double tVal = tMin + i * dtTick;
            double cx = toCanvasX(tVal);

            // Linha suave de grade
            gc.setStroke(COLOR_GRID_LINES);
            gc.strokeLine(cx, 0, cx, h);

            // Rótulo numérico na base
            gc.setFill(COLOR_TEXT_MUTED);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.BOTTOM);
            gc.fillText(formatCoordinate(tVal), cx, h - 4);
        }

        // Linhas de grade horizontais e marcações no eixo x
        double dxTick = (xMax - xMin) / numTicks;
        for (int j = 0; j <= numTicks; j++) {
            double xVal = xMin + j * dxTick;
            double cy = toCanvasY(xVal);

            // Linha suave de grade
            gc.setStroke(COLOR_GRID_LINES);
            gc.strokeLine(0, cy, w, cy);

            // Rótulo numérico na lateral esquerda
            gc.setFill(COLOR_TEXT_MUTED);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.setTextBaseline(VPos.CENTER);
            gc.fillText(formatCoordinate(xVal), 6, cy);
        }

        // Destaca os eixos coordenados t=0 e x=0 (caso estejam dentro do domínio visível)
        gc.setLineWidth(1.25);
        gc.setStroke(COLOR_AXES_ACCENT);

        if (tMin <= 0 && tMax >= 0) {
            double zeroX = toCanvasX(0);
            gc.strokeLine(zeroX, 0, zeroX, h);
        }

        if (xMin <= 0 && xMax >= 0) {
            double zeroY = toCanvasY(0);
            gc.strokeLine(0, zeroY, w, zeroY);
        }

        // Borda perimetral discreta
        gc.setStroke(COLOR_AXES_BORDER);
        gc.strokeRect(0.5, 0.5, w - 1.0, h - 1.0);

        gc.restore();
    }

    /**
     * Renderiza as retas do campo de direções.
     */
    private void drawDirectionField(GraphicsContext gc) {
        if (fieldSegments.isEmpty()) {
            return;
        }

        gc.save();
        gc.setStroke(COLOR_FIELD_SEGMENTS);
        gc.setLineWidth(1.2);
        gc.setLineCap(StrokeLineCap.ROUND);

        for (FieldSegment seg : fieldSegments) {
            if (!seg.isFinite()) {
                continue;
            }

            double u1 = toCanvasX(seg.t1());
            double v1 = toCanvasY(seg.x1());
            double u2 = toCanvasX(seg.t2());
            double v2 = toCanvasY(seg.x2());

            gc.strokeLine(u1, v1, u2, v2);
        }

        gc.restore();
    }

    /**
     * Renderiza todas as trajetórias e seus pontos iniciais de lançamento.
     */
    
    
    private void drawCriticalPoints(GraphicsContext gc) {
        if (!showCriticalPoints || criticalPoints == null || criticalPoints.isEmpty()) {
            return;
        }

        gc.save();
        double w = getWidth();

        for (CriticalPoint cp : criticalPoints) {
            double cy = toCanvasY(cp.xStar());

            // Só desenha se estiver dentro dos limites verticais da tela
            if (cy < -20 || cy > getHeight() + 20) {
                continue;
            }

            // Define cor e estilo pelo tipo de estabilidade
            switch (cp.stability()) {
                case STABLE_ATTRACTOR -> {
                    gc.setStroke(Color.web("#10b981")); // Verde
                    gc.setFill(Color.web("#10b981"));
                    gc.setLineWidth(2.2);
                    gc.setLineDashes(null); // Contínua
                }
                case UNSTABLE_REPULSOR -> {
                    gc.setStroke(Color.web("#ef4444")); // Vermelho
                    gc.setFill(Color.web("#ef4444"));
                    gc.setLineWidth(2.0);
                    gc.setLineDashes(6.0, 5.0); // Tracejada
                }
                case SEMI_STABLE -> {
                    gc.setStroke(Color.web("#94a3b8")); // Cinza azulado
                    gc.setFill(Color.web("#94a3b8"));
                    gc.setLineWidth(1.8);
                    gc.setLineDashes(2.0, 4.0);
                }
            }

            // Reta horizontal de equilíbrio ao longo de todo o plano t
            gc.strokeLine(0, cy, w, cy);

            // Marcador no centro da visualização
            double markerX = w * 0.15;
            gc.fillOval(markerX - 5, cy - 5, 10, 10);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1.2);
            gc.setLineDashes(null);
            gc.strokeOval(markerX - 5, cy - 5, 10, 10);

            // Rótulo com valor do equilíbrio
            gc.setFill(Color.web("#f8fafc"));
            gc.setFont(javafx.scene.text.Font.font("Monospaced", javafx.scene.text.FontWeight.BOLD, 12));
            String label = String.format(java.util.Locale.US, " x* = %.3f (λ = %.2f)", cp.xStar(), cp.lambda());
            gc.fillText(label, markerX + 8, cy - 6);
        }

        gc.restore();
    }

    private void drawIsoclines(GraphicsContext gc) {
        if (!showIsoclines) return;

        gc.save();

        // Isóclina zero (c = 0) em dourado tracejado
        if (lockZeroIsocline && zeroIsoclineSegments != null && !zeroIsoclineSegments.isEmpty()) {
            gc.setStroke(Color.web("#fbbf24"));
            gc.setLineWidth(2.2);
            gc.setLineDashes(6.0, 4.0);
            for (var seg : zeroIsoclineSegments) {
                double p1x = toCanvasX(seg.t1());
                double p1y = toCanvasY(seg.x1());
                double p2x = toCanvasX(seg.t2());
                double p2y = toCanvasY(seg.x2());
                gc.strokeLine(p1x, p1y, p2x, p2y);
            }
            gc.setLineDashes(null);
        }

        // Isóclina dinâmica com valor c (magenta luminoso contínuo)
        if (isoclineSegments != null && !isoclineSegments.isEmpty()) {
            gc.setStroke(Color.web("#e879f9"));
            gc.setLineWidth(2.0);
            for (var seg : isoclineSegments) {
                double p1x = toCanvasX(seg.t1());
                double p1y = toCanvasY(seg.x1());
                double p2x = toCanvasX(seg.t2());
                double p2y = toCanvasY(seg.x2());
                gc.strokeLine(p1x, p1y, p2x, p2y);
            }
        }

        gc.restore();
    }

    private void drawTrajectories(GraphicsContext gc) {
        if (trajectories.isEmpty()) {
            return;
        }

        gc.save();
        gc.setLineWidth(1.8);
        gc.setLineCap(StrokeLineCap.ROUND);

        for (int i = 0; i < trajectories.size(); i++) {
            OrbitTrajectory trajectory = trajectories.get(i);
            Color orbitColor = ORBIT_COLORS.get(i % ORBIT_COLORS.size());

            List<TrajectoryPoint> points = trajectory.getFullOrderedTrajectory();
            if (points.isEmpty()) {
                continue;
            }

            // 1. Desenha a linha da curva solução
            gc.setStroke(orbitColor);
            gc.beginPath();

            boolean first = true;
            for (TrajectoryPoint pt : points) {
                double cx = toCanvasX(pt.t());
                double cy = toCanvasY(pt.x());

                if (first) {
                    gc.moveTo(cx, cy);
                    first = false;
                } else {
                    gc.lineTo(cx, cy);
                }
            }
            gc.stroke();

            // 2. Destaca o ponto de lançamento (t0, x0)
            TrajectoryPoint p0 = !trajectory.forwardBranch().isEmpty()
                    ? trajectory.forwardBranch().getFirst()
                    : trajectory.backwardBranch().getFirst();

            double p0X = toCanvasX(p0.t());
            double p0Y = toCanvasY(p0.x());
            double markerRadius = 4.0;

            // Halo/Borda externa escura para contraste
            gc.setFill(COLOR_BACKGROUND);
            gc.fillOval(p0X - markerRadius - 1.5, p0Y - markerRadius - 1.5,
                    (markerRadius + 1.5) * 2, (markerRadius + 1.5) * 2);

            // Núcleo brilhante na cor da curva
            gc.setFill(orbitColor);
            gc.fillOval(p0X - markerRadius, p0Y - markerRadius,
                    markerRadius * 2, markerRadius * 2);
        }

        gc.restore();
    }

    // =========================================================================
    // 4. INTERATIVIDADE E EVENTOS DE MOUSE
    // =========================================================================

    private void initEventHandlers() {
        // Dispara coordenadas matemáticas em tempo real para a barra de status
        setOnMouseMoved(this::handleMouseMoved);

        // Permite lançamento de soluções com o clique esquerdo do mouse
        setOnMouseClicked(this::handleMouseClicked);

        // Pan e Zoom interativos
        setOnMousePressed(this::handleMousePressed);
        setOnMouseDragged(this::handleMouseDragged);
        setOnMouseReleased(this::handleMouseReleased);
        setOnScroll(this::handleScroll);
    }

    private void handleMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY || event.getButton() == MouseButton.MIDDLE) {
            dragAnchorX = event.getX();
            dragAnchorY = event.getY();
            isPanning = true;
            event.consume();
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (!isPanning) return;

        double dxPixels = event.getX() - dragAnchorX;
        double dyPixels = event.getY() - dragAnchorY;

        double w = getWidth();
        double h = getHeight();
        if (w <= 0 || h <= 0) return;

        double dtSpan = tMax - tMin;
        double dxSpan = xMax - xMin;

        // Deslocamento no espaço matemático
        double deltaT = -dxPixels * (dtSpan / w);
        double deltaX = dyPixels * (dxSpan / h);

        tMin += deltaT;
        tMax += deltaT;
        xMin += deltaX;
        xMax += deltaX;

        dragAnchorX = event.getX();
        dragAnchorY = event.getY();

        if (onBoundsChanged != null) {
            onBoundsChanged.onBoundsChanged(tMin, tMax, xMin, xMax);
        } else {
            redraw();
        }
        event.consume();
    }

    private void handleMouseReleased(MouseEvent event) {
        if (isPanning) {
            isPanning = false;
            if (onBoundsChanged != null) {
                onBoundsChanged.onBoundsChanged(tMin, tMax, xMin, xMax);
            } else {
                redraw();
            }
            event.consume();
        }
    }

    private void handleScroll(ScrollEvent event) {
        double w = getWidth();
        double h = getHeight();
        if (w <= 0 || h <= 0) return;

        // Ponto focal sob o cursor no momento do scroll
        double cursorCanvasX = event.getX();
        double cursorCanvasY = event.getY();
        double focalT = toWorldT(cursorCanvasX);
        double focalX = toWorldX(cursorCanvasY);

        // Fator de escala: > 0 aproxima (zoom in), < 0 afasta (zoom out)
        double zoomFactor = (event.getDeltaY() > 0) ? 0.85 : 1.15;

        // Homotetia invariante em relação ao cursor
        tMin = focalT + zoomFactor * (tMin - focalT);
        tMax = focalT + zoomFactor * (tMax - focalT);
        xMin = focalX + zoomFactor * (xMin - focalX);
        xMax = focalX + zoomFactor * (xMax - focalX);

        redraw();

        if (onBoundsChanged != null) {
            onBoundsChanged.onBoundsChanged(tMin, tMax, xMin, xMax);
        }
        event.consume();
    }

    private void handleMouseMoved(MouseEvent event) {
        if (onMouseMove != null) {
            double worldT = toWorldT(event.getX());
            double worldX = toWorldX(event.getY());
            onMouseMove.accept(worldT, worldX);
        }
    }

    private void handleMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && onInitialPointSelected != null) {
            double worldT = toWorldT(event.getX());
            double worldX = toWorldX(event.getY());

            // Garante que o ponto clicado esteja no retângulo visível
            if (worldT >= tMin && worldT <= tMax && worldX >= xMin && worldX <= xMax) {
                onInitialPointSelected.accept(worldT, worldX);
            }
        }
    }

    private String formatCoordinate(double val) {
        if (Math.abs(val) < 1e-12) {
            return "0";
        }
        if (Math.abs(val) >= 1000 || (Math.abs(val) < 0.01 && val != 0.0)) {
            return String.format("%.1e", val);
        }
        return String.format("%.2f", val);
    }

    public double getTMin() { return tMin; }
    public double getTMax() { return tMax; }
    public double getXMin() { return xMin; }
    public double getXMax() { return xMax; }
}