package com.dfield.ui;

import com.dfield.core.DirectionFieldCalculator;
import com.dfield.core.FieldSegment;
import com.dfield.core.ODEExpression;
import com.dfield.numerical.ODESolver;
import com.dfield.numerical.OrbitTrajectory;
import com.dfield.numerical.RK4Solver;
import com.dfield.numerical.TerminationReason;
import com.dfield.numerical.TrajectoryPoint;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.*;

/**
 * Janela principal e controladora de layout da aplicação dfieldX.
 * <p>
 * Integra a interface de entrada de parâmetros, o avaliador analítico {@link ODEExpression},
 * o gerador de campo {@link DirectionFieldCalculator} e o motor numérico {@link RK4Solver}.
 */
public class MainWindow extends BorderPane {

    // --- Componentes do Canvas e Visualização ---
    private final DirectionFieldCanvas canvas;
    private final Pane canvasContainer;

    // --- Controles de Entrada (Sidebar) ---
    private final TextField txtIndependentVar = new TextField("t");
    private final TextField txtDependentVar = new TextField("x");
    private final TextField txtEquation = new TextField("x^2 - t");
    private final TextField txtParameters = new TextField("a=1.0");

    private final TextField txtTMin = new TextField("-2.0");
    private final TextField txtTMax = new TextField("4.0");
    private final TextField txtXMin = new TextField("-2.0");
    private final TextField txtXMax = new TextField("4.0");

    private final TextField txtT0 = new TextField("0.0");
    private final TextField txtX0 = new TextField("1.0");

    private final Button btnUpdate = new Button("Atualizar Campo");
    private final Button btnTrace = new Button("Traçar");
    private final Button btnClear = new Button("Limpar Trajetórias");

    // --- Barra de Status ---
    private final Label lblStatus = new Label("Pronto.");
    private final Label lblCoords = new Label("t: -- | x: --");

    // --- Estado do Motor Matemático ---
    private ODEExpression currentODE;
    private final ODESolver solver = new RK4Solver();

    public MainWindow() {
        this.canvas = new DirectionFieldCanvas();
        this.canvasContainer = new Pane(canvas);

        initLayout();
        initStyle();
        initEventHandlers();

        // Dispara o cálculo inicial assim que a interface for montada
        Platform.runLater(this::updateDirectionField);
    }

    // =========================================================================
    // 1. CONSTRUÇÃO DO LAYOUT
    // =========================================================================

    private void initLayout() {
        // --- CENTRO: Container do Canvas com Binding de Redimensionamento ---
        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.heightProperty().bind(canvasContainer.heightProperty());

        // Ao redimensionar a área de plotagem, recalcula as inclinações anisotrópicas
        canvasContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0 && currentODE != null) {
                calculateAndRenderField();
            }
        });
        canvasContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0 && currentODE != null) {
                calculateAndRenderField();
            }
        });

        setCenter(canvasContainer);

        // --- ESQUERDA: Sidebar com ScrollPane ---
        VBox sidebar = buildSidebar();
        ScrollPane scrollPane = new ScrollPane(sidebar);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("sidebar-scroll");
        setLeft(scrollPane);

        // --- INFERIOR: Barra de Status ---
        HBox statusBar = buildStatusBar();
        setBottom(statusBar);
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(16));
        sidebar.setPrefWidth(300);
        sidebar.getStyleClass().add("sidebar-pane");

        // 1. Cabeçalho de Seção
        Label lblTitle = new Label("EDO ESCALAR 1D");
        lblTitle.getStyleClass().add("section-title");

        // 2. Variáveis
        HBox varsBox = new HBox(10);
        varsBox.getChildren().addAll(
                createLabeledField("Indep. (t):", txtIndependentVar),
                createLabeledField("Dep. (x):", txtDependentVar)
        );

        // 3. Equação Diferencial dx/dt
        VBox equationBox = new VBox(4,
                new Label("dx/dt = f(t, x):"),
                txtEquation
        );

        // 4. Parâmetros
        VBox paramsBox = new VBox(4,
                new Label("Parâmetros (ex: a=1.0, b=2.0):"),
                txtParameters
        );

        // 5. Limites dos Eixos
        Label lblDomainTitle = new Label("LIMITES DO DOMÍNIO");
        lblDomainTitle.getStyleClass().add("section-subtitle");

        GridPane domainGrid = new GridPane();
        domainGrid.setHgap(8);
        domainGrid.setVgap(8);
        domainGrid.addRow(0, new Label("tMin:"), txtTMin, new Label("tMax:"), txtTMax);
        domainGrid.addRow(1, new Label("xMin:"), txtXMin, new Label("xMax:"), txtXMax);

        // 6. Botão de Atualização
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.getStyleClass().addAll("btn", "btn-green");

        // 7. Lançamento de Trajetória Manual (t0, x0)
        Label lblOrbitTitle = new Label("CONDIÇÃO INICIAL (t0, x0)");
        lblOrbitTitle.getStyleClass().add("section-subtitle");

        HBox icBox = new HBox(8);
        icBox.setAlignment(Pos.CENTER_LEFT);
        icBox.getChildren().addAll(
                new Label("t0:"), txtT0,
                new Label("x0:"), txtX0,
                btnTrace
        );
        btnTrace.getStyleClass().addAll("btn", "btn-green");

        // 8. Botão de Limpeza
        btnClear.setMaxWidth(Double.MAX_VALUE);
        btnClear.getStyleClass().addAll("btn", "btn-secondary");

        sidebar.getChildren().addAll(
                lblTitle,
                varsBox,
                equationBox,
                paramsBox,
                lblDomainTitle,
                domainGrid,
                btnUpdate,
                new Separator(),
                lblOrbitTitle,
                icBox,
                btnClear
        );

        return sidebar;
    }

    private HBox buildStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(6, 16, 6, 16));
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.getStyleClass().add("status-bar");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        lblCoords.getStyleClass().add("coords-text");

        statusBar.getChildren().addAll(lblStatus, spacer, lblCoords);
        return statusBar;
    }

    private VBox createLabeledField(String label, TextField field) {
        VBox box = new VBox(4);
        box.getChildren().addAll(new Label(label), field);
        HBox.setHgrow(box, Priority.ALWAYS);
        return box;
    }

    // =========================================================================
    // 2. ESTILOS CSS
    // =========================================================================

    private void initStyle() {
        URL cssResource = getClass().getResource("/css/style.css");
        if (cssResource != null) {
            getStylesheets().add(cssResource.toExternalForm());
        }
    }

    // =========================================================================
    // 3. INTEGRAÇÃO E DISPARO DE AÇÕES
    // =========================================================================

    private void initEventHandlers() {
        // Atualiza campo
        btnUpdate.setOnAction(e -> updateDirectionField());

        // Lança órbita a partir do botão
        btnTrace.setOnAction(e -> {
            try {
                double t0 = Double.parseDouble(txtT0.getText().trim());
                double x0 = Double.parseDouble(txtX0.getText().trim());
                traceOrbit(t0, x0);
            } catch (NumberFormatException ex) {
                setStatus("Valores inválidos para (t0, x0).", true);
            }
        });

        // Limpa trajetórias
        btnClear.setOnAction(e -> {
            canvas.clearTrajectories();
            setStatus("Trajetórias removidas.", false);
        });

        // Clique interativo no canvas
        canvas.setOnInitialPointSelected((t0, x0) -> {
            txtT0.setText(String.format(Locale.US, "%.3f", t0));
            txtX0.setText(String.format(Locale.US, "%.3f", x0));
            traceOrbit(t0, x0);
        });

        // Telemetria do mouse
        canvas.setOnMouseMove((t, x) -> {
            lblCoords.setText(String.format(Locale.US, "t: %.3f | x: %.3f", t, x));
        });
    }

    /**
     * Valida os dados de entrada, instancia a nova expressão analítica e recalcula o campo.
     */
    private void updateDirectionField() {
        try {
            double tMin = Double.parseDouble(txtTMin.getText().trim());
            double tMax = Double.parseDouble(txtTMax.getText().trim());
            double xMin = Double.parseDouble(txtXMin.getText().trim());
            double xMax = Double.parseDouble(txtXMax.getText().trim());

            if (tMax <= tMin || xMax <= xMin) {
                setStatus("Limites inválidos: o valor máximo deve superar o mínimo.", true);
                return;
            }

            Map<String, Double> params = parseParameters(txtParameters.getText());

            this.currentODE = new ODEExpression(
                    txtEquation.getText(),
                    txtIndependentVar.getText(),
                    txtDependentVar.getText(),
                    params
            );

            canvas.setDomain(tMin, tMax, xMin, xMax);
            calculateAndRenderField();
            setStatus("Campo de direções atualizado com sucesso.", false);

        } catch (IllegalArgumentException ex) {
            setStatus("Erro na definição da EDO: " + ex.getMessage(), true);
        } catch (Exception ex) {
            setStatus("Erro ao processar entradas: " + ex.getMessage(), true);
        }
    }

    /**
     * Gera a malha e a envia ao canvas.
     */
    private void calculateAndRenderField() {
        if (currentODE == null || canvas.getWidth() <= 0 || canvas.getHeight() <= 0) {
            return;
        }

        int numGridT = 24;
        int numGridX = 24;
        double segmentLengthPx = 14.0;

        List<FieldSegment> segments = DirectionFieldCalculator.calculate(
                currentODE,
                canvas.getTMin(),
                canvas.getTMax(),
                canvas.getXMin(),
                canvas.getXMax(),
                numGridT,
                numGridX,
                canvas.getWidth(),
                canvas.getHeight(),
                segmentLengthPx
        );

        canvas.setFieldSegments(segments);
    }

    /**
     * Integra a equação diferencial numericamente de forma bidirecional (forward e backward).
     */
    private void traceOrbit(double t0, double x0) {
        if (currentODE == null) {
            setStatus("Defina uma EDO válida antes de traçar a solução.", true);
            return;
        }

        double tMin = canvas.getTMin();
        double tMax = canvas.getTMax();
        double xMin = canvas.getXMin();
        double xMax = canvas.getXMax();

        double deltaT = tMax - tMin;
        double dt = deltaT / 600.0;
        int maxSteps = 8000;

        // 1. Ramo para frente (Forward: t0 -> tMax)
        List<TrajectoryPoint> forwardBranch = new ArrayList<>();
        forwardBranch.add(new TrajectoryPoint(t0, x0));

        double t = t0;
        double x = x0;
        TerminationReason fReason = TerminationReason.NORMAL;

        for (int i = 0; i < maxSteps && t < tMax; i++) {
            double stepSize = Math.min(dt, tMax - t);
            double nextX = solver.step(currentODE, t, x, stepSize);

            if (!Double.isFinite(nextX)) {
                fReason = TerminationReason.BLOW_UP;
                break;
            }

            t += stepSize;
            x = nextX;
            forwardBranch.add(new TrajectoryPoint(t, x));

            // Interrupção nas bordas (com margem de 30%)
            if (x < xMin - (xMax - xMin) * 0.3 || x > xMax + (xMax - xMin) * 0.3) {
                fReason = TerminationReason.BOUNDS;
                break;
            }
        }

        // 2. Ramo para trás (Backward: t0 -> tMin)
        List<TrajectoryPoint> backwardBranch = new ArrayList<>();
        backwardBranch.add(new TrajectoryPoint(t0, x0));

        t = t0;
        x = x0;
        TerminationReason bReason = TerminationReason.NORMAL;

        for (int i = 0; i < maxSteps && t > tMin; i++) {
            double stepSize = Math.max(-dt, tMin - t);
            double nextX = solver.step(currentODE, t, x, stepSize);

            if (!Double.isFinite(nextX)) {
                bReason = TerminationReason.BLOW_UP;
                break;
            }

            t += stepSize;
            x = nextX;
            backwardBranch.add(new TrajectoryPoint(t, x));

            if (x < xMin - (xMax - xMin) * 0.3 || x > xMax + (xMax - xMin) * 0.3) {
                bReason = TerminationReason.BOUNDS;
                break;
            }
        }

        OrbitTrajectory trajectory = new OrbitTrajectory(forwardBranch, backwardBranch, fReason, bReason);
        canvas.addTrajectory(trajectory);

        setStatus(String.format(Locale.US, "Solução traçada de (%.3f, %.3f) com %d pontos.",
                t0, x0, trajectory.getFullOrderedTrajectory().size()), false);
    }

    private Map<String, Double> parseParameters(String raw) {
        Map<String, Double> map = new HashMap<>();
        if (raw == null || raw.isBlank()) {
            return map;
        }

        String[] tokens = raw.split("[,;\\s]+");
        for (String token : tokens) {
            if (token.contains("=")) {
                String[] parts = token.split("=");
                if (parts.length == 2) {
                    try {
                        map.put(parts[0].trim(), Double.parseDouble(parts[1].trim()));
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return map;
    }

    private void setStatus(String message, boolean isError) {
        lblStatus.setText(message);
        if (isError) {
            lblStatus.setStyle("-fx-text-fill: #ff6b6b;");
        } else {
            lblStatus.setStyle("-fx-text-fill: #48dbfb;");
        }
    }
}