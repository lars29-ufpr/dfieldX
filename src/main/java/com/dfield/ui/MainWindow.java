package com.dfield.ui;

import com.dfield.util.I18n;

import com.dfield.core.DirectionFieldCalculator;
import com.dfield.core.DormandPrince45Solver;
import com.dfield.core.EquationExample;
import com.dfield.core.FieldSegment;
import com.dfield.core.ODEExpression;
import com.dfield.numerical.ODESolver;
import com.dfield.numerical.OrbitTrajectory;
import com.dfield.numerical.RK4Solver;
import com.dfield.numerical.TerminationReason;
import com.dfield.numerical.TrajectoryPoint;
import com.dfield.ui.GalleryDialog;
import com.dfield.ui.AboutDialog;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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
    private final TextField txtEquation = new TextField("a*x - t");
    private final TextField txtParameters = new TextField("a=1.0");
    private final Button btnSliders = new Button(I18n.get("btn.sliders"));
    private final Button btnIsoclines = new Button(I18n.get("btn.isoclines"));
    private final Button btnCriticalPoints = new Button(I18n.get("btn.equilibria"));
    private final Button btnPhasePlot = new Button(I18n.get("btn.phase_plot"));
    private boolean showEquilibria = false;

    private final TextField txtTMin = new TextField("-2.0");
    private final TextField txtTMax = new TextField("4.0");
    private final TextField txtXMin = new TextField("-2.0");
    private final TextField txtXMax = new TextField("4.0");

    private final TextField txtT0 = new TextField("0.0");
    private final TextField txtX0 = new TextField("1.0");

    private final Button btnUpdate = new Button(I18n.get("btn.update_field"));
    private final Button btnTrace = new Button(I18n.get("btn.solve"));
    private final Button btnClear = new Button(I18n.get("btn.clear"));
private final Button btnGallery = new Button(I18n.get("btn.gallery"));
    private final Button btnGuide = new Button(I18n.get("btn.guide"));
private final Button btnExport = new Button(I18n.get("btn.export_png"));
    private final Button btnExportSvg = new Button(I18n.get("btn.export_svg"));
    private final Button btnAbout = new Button(I18n.get("btn.about"));

    // --- Barra de Status ---
    private final Label lblStatus = new Label("Pronto.");
    private final Label lblCoords = new Label("t: -- | x: --");

    // --- Estado do Motor Matemático ---
    private ODEExpression currentODE;
    private final java.util.List<double[]> activeInitialConditions = new java.util.ArrayList<>();

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
        VBox sidebar = new VBox(14);
        sidebar.setPadding(new Insets(16));
        sidebar.setMinWidth(330);
        sidebar.setPrefWidth(370.0);
        sidebar.getStyleClass().add("sidebar-pane");

        // 1. Cabeçalho de Seção
        Label lblTitle = new Label(I18n.get("section.ode"));
        lblTitle.getStyleClass().add("section-title");

        // 2. Variáveis com nomes completos
        HBox varsBox = new HBox(10);
        varsBox.getChildren().addAll(
                createLabeledField(I18n.get("label.independent"), txtIndependentVar),
                createLabeledField(I18n.get("label.dependent"), txtDependentVar)
        );

        // 3. Equação Diferencial dx/dt
        VBox equationBox = new VBox(4,
                new Label("dx/dt = f(t, x):"),
                txtEquation
        );

        // 4. Parâmetros e Sliders
        btnSliders.getStyleClass().addAll("btn", "btn-secondary");
        btnSliders.setAlignment(Pos.CENTER);
        btnSliders.setMinWidth(80);

        HBox paramRow = new HBox(8, txtParameters, btnSliders);
        HBox.setHgrow(txtParameters, Priority.ALWAYS);

        VBox paramsBox = new VBox(4,
                new Label(I18n.get("label.parameters")),
                paramRow
        );

        // 5. Limites dos Eixos (Grid com colunas proporcionais e labels sem reticências)
        Label lblDomainTitle = new Label(I18n.get("section.domain"));
        lblDomainTitle.getStyleClass().add("section-subtitle");

        GridPane domainGrid = new GridPane();
        domainGrid.setHgap(8);
        domainGrid.setVgap(8);

        Label lblTMin = new Label(I18n.get("label.tmin"));
        Label lblTMax = new Label(I18n.get("label.tmax"));
        Label lblXMin = new Label(I18n.get("label.xmin"));
        Label lblXMax = new Label(I18n.get("label.xmax"));
        lblTMin.setMinWidth(42);
        lblTMax.setMinWidth(42);
        lblXMin.setMinWidth(42);
        lblXMax.setMinWidth(42);

        domainGrid.addRow(0, lblTMin, txtTMin, lblTMax, txtTMax);
        domainGrid.addRow(1, lblXMin, txtXMin, lblXMax, txtXMax);

        // 6. Galeria de Modelos e Guia Didático
        btnGallery.setMaxWidth(Double.MAX_VALUE);
        btnGallery.getStyleClass().addAll("btn", "btn-purple");
        btnGuide.setMaxWidth(Double.MAX_VALUE);
        btnGuide.getStyleClass().addAll("btn", "btn-purple");
        HBox.setHgrow(btnGallery, Priority.ALWAYS);
        HBox.setHgrow(btnGuide, Priority.ALWAYS);

        HBox modelsGuideBox = new HBox(8, btnGallery, btnGuide);

        // 7. Análise de EDO (Isóclinas, Equilíbrios e x' vs x)
        btnIsoclines.setMaxWidth(Double.MAX_VALUE);
        btnCriticalPoints.setMaxWidth(Double.MAX_VALUE);
        btnPhasePlot.setMaxWidth(Double.MAX_VALUE);
        btnIsoclines.getStyleClass().addAll("btn", "btn-secondary");
        btnCriticalPoints.getStyleClass().addAll("btn", "btn-secondary");
        btnPhasePlot.getStyleClass().addAll("btn", "btn-secondary");

        HBox.setHgrow(btnIsoclines, Priority.ALWAYS);
        HBox.setHgrow(btnCriticalPoints, Priority.ALWAYS);
        HBox.setHgrow(btnPhasePlot, Priority.ALWAYS);

        HBox autonomousRow = new HBox(6, btnCriticalPoints, btnPhasePlot);
        VBox analysisButtonsBox = new VBox(6, btnIsoclines, autonomousRow);

        Tooltip tipAutonomous = new Tooltip("Disponível apenas para equações autônomas: dx/dt = f(x)");
        Tooltip.install(btnCriticalPoints, tipAutonomous);
        Tooltip.install(btnPhasePlot, tipAutonomous);

        // 8. Botão Principal de Atualização
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.getStyleClass().addAll("btn", "btn-green");

        // 9. Condição Inicial (t0, x0)
        Label lblOrbitTitle = new Label(I18n.get("section.initial_condition"));
        lblOrbitTitle.getStyleClass().add("section-subtitle");

        Label lblT0 = new Label("t₀:");
        Label lblX0 = new Label("x₀:");
        lblT0.setMinWidth(20);
        lblX0.setMinWidth(20);

        txtT0.setPrefWidth(75);
        txtX0.setPrefWidth(75);
        txtT0.setMinWidth(60);
        txtX0.setMinWidth(60);

        btnTrace.getStyleClass().addAll("btn", "btn-green");
        btnTrace.setMinWidth(80);
        btnTrace.setPrefWidth(85);

        HBox icBox = new HBox(6, lblT0, txtT0, lblX0, txtX0, btnTrace);
        icBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(txtT0, Priority.ALWAYS);
        HBox.setHgrow(txtX0, Priority.ALWAYS);

        // 10. Botão Limpar
        btnClear.setMaxWidth(Double.MAX_VALUE);
        btnClear.getStyleClass().addAll("btn", "btn-secondary");

        // 11. Botões de Exportação lado a lado
        btnExport.setMaxWidth(Double.MAX_VALUE);
        btnExportSvg.setMaxWidth(Double.MAX_VALUE);
        btnExport.getStyleClass().addAll("btn", "btn-secondary");
        btnExportSvg.getStyleClass().addAll("btn", "btn-secondary");
        HBox.setHgrow(btnExport, Priority.ALWAYS);
        HBox.setHgrow(btnExportSvg, Priority.ALWAYS);

        btnExport.setMinHeight(32);
        btnExportSvg.setMinHeight(32);
        btnAbout.setMinHeight(32);
        btnAbout.setMaxWidth(Double.MAX_VALUE);
        btnAbout.getStyleClass().addAll("btn", "btn-secondary");
        HBox.setHgrow(btnAbout, Priority.ALWAYS);
        HBox exportBox = new HBox(8, btnExport, btnExportSvg);
        btnExport.setMinHeight(32);
        btnExportSvg.setMinHeight(32);
        btnAbout.setMinHeight(32);
        btnAbout.setMaxWidth(Double.MAX_VALUE);
        btnAbout.getStyleClass().addAll("btn", "btn-secondary");

        sidebar.getChildren().addAll(
                lblTitle,
                varsBox,
                equationBox,
                paramsBox,
                lblDomainTitle,
                domainGrid,
                modelsGuideBox,
                analysisButtonsBox,
                btnUpdate,
                new Separator(),
                lblOrbitTitle,
                icBox,
                btnClear,
                exportBox,
                btnAbout
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
btnGallery.setOnAction(e -> {
    Stage owner = (Stage) this.getScene().getWindow();
    
    EquationExample currentState = new EquationExample(
            "Modelo Atual", "Personalizados", "Criado pelo usuário",
            txtIndependentVar.getText().trim(),
            txtDependentVar.getText().trim(),
            txtEquation.getText().trim(),
            txtParameters.getText().trim(),
            Double.parseDouble(txtTMin.getText().trim()),
            Double.parseDouble(txtTMax.getText().trim()),
            Double.parseDouble(txtXMin.getText().trim()),
            Double.parseDouble(txtXMax.getText().trim()),
            Double.parseDouble(txtT0.getText().trim()),
            Double.parseDouble(txtX0.getText().trim()),
            true
    );

    Optional<EquationExample> selected = GalleryDialog.showGallery(owner, currentState);
    selected.ifPresent(model -> {
        txtIndependentVar.setText(model.independentVar());
        txtDependentVar.setText(model.dependentVar());
        txtEquation.setText(model.expression());
        txtParameters.setText(model.defaultParameters() != null ? model.defaultParameters() : "");
        txtTMin.setText(String.valueOf(model.tMin()));
        txtTMax.setText(String.valueOf(model.tMax()));
        txtXMin.setText(String.valueOf(model.xMin()));
        txtXMax.setText(String.valueOf(model.xMax()));
        txtT0.setText(String.valueOf(model.defaultT0()));
        txtX0.setText(String.valueOf(model.defaultX0()));

        // Limpa trajetórias e órbitas anteriores ao carregar novo modelo
        btnClear.fire();
        btnUpdate.fire();
    });
});
        // Lança órbita a partir do botão
        btnTrace.setOnAction(e -> {
            try {
                double t0 = Double.parseDouble(txtT0.getText().trim());
                double x0 = Double.parseDouble(txtX0.getText().trim());
                traceOrbit(t0, x0);
            } catch (NumberFormatException ex) {
setStatus(I18n.get("status.invalid_coords"), true);
            }
        });

        // Limpa trajetórias
        btnClear.setOnAction(e -> {
            activeInitialConditions.clear();
            canvas.clearTrajectories();
setStatus(I18n.get("status.trajectories_cleared"), false);
        });

                        btnPhasePlot.setOnAction(e -> {
            if (currentODE == null) {
setStatus(I18n.get("status.define_valid_ode_phase"), true);
                return;
            }

            if (!com.dfield.numerical.CriticalPointCalculator.isAutonomous(currentODE)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(I18n.get("equilibria.non_autonomous.title"));
                alert.setHeaderText(I18n.get("phaseplot.non_autonomous.header"));
alert.setContentText(I18n.get("phaseplot.non_autonomous.content"));
                alert.initOwner((Stage) btnPhasePlot.getScene().getWindow());
                alert.showAndWait();
                return;
            }

            PhasePlotWindow.show(
                (Stage) btnPhasePlot.getScene().getWindow(),
                currentODE,
                canvas.getXMin(),
                canvas.getXMax()
            );
        });
        btnCriticalPoints.setOnAction(e -> {
            if (currentODE == null) {
                setStatus(I18n.get("status.ode_invalid_equilibria"), true);
                return;
            }

            if (!com.dfield.numerical.CriticalPointCalculator.isAutonomous(currentODE)) {
                showEquilibria = false;
                btnCriticalPoints.setStyle("");
                canvas.setCriticalPoints(java.util.Collections.emptyList(), false);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(I18n.get("equilibria.non_autonomous.title"));
                alert.setHeaderText(I18n.get("equilibria.non_autonomous.header"));
                alert.setContentText(I18n.get("equilibria.non_autonomous.content"));
                alert.initOwner((Stage) btnCriticalPoints.getScene().getWindow());
                alert.showAndWait();
                return;
            }

            showEquilibria = !showEquilibria;
            if (showEquilibria) {
                btnCriticalPoints.setStyle("-fx-border-color: #10b981; -fx-text-fill: #10b981; -fx-font-weight: bold;");
                updateAutonomousControlsState();
        updateEquilibriaIfActive();
                setStatus(I18n.get("status.equilibria_calculated"), false);
            } else {
                btnCriticalPoints.setStyle("");
                canvas.setCriticalPoints(java.util.Collections.emptyList(), false);
                setStatus(I18n.get("status.equilibria_disabled"), false);
            }
        });

        btnIsoclines.setOnAction(e -> {
            Stage owner = (Stage) btnIsoclines.getScene().getWindow();
            IsoclinesWindow.show(
                    owner, canvas, currentODE,
                    canvas.getTMin(), canvas.getTMax(),
                    canvas.getXMin(), canvas.getXMax()
            );
        });

        btnSliders.setOnAction(e -> {
            Stage owner = (Stage) btnSliders.getScene().getWindow();
            ParameterSlidersWindow.show(owner, txtParameters.getText(), updatedStr -> {
                
                txtParameters.setText(updatedStr);
                updateDirectionField();
                recalculateTrajectories();
                updateEquilibriaIfActive();
                IsoclinesWindow.updateIfOpen(currentODE, canvas.getTMin(), canvas.getTMax(), canvas.getXMin(), canvas.getXMax());
                PhasePlotWindow.updateIfOpen(currentODE, canvas.getXMin(), canvas.getXMax());
            });
        });
        btnGuide.setOnAction(e -> GuideWindow.show((javafx.stage.Stage) btnGuide.getScene().getWindow()));
        btnExport.setOnAction(e -> exportFieldImage());
        btnExportSvg.setOnAction(e -> exportFieldSvg());
        btnAbout.setOnAction(e -> AboutDialog.show((javafx.stage.Stage) btnAbout.getScene().getWindow()));
        // Clique interativo no canvas
                // Sincronização interativa do Pan e Zoom focal
        canvas.setOnBoundsChanged((newTMin, newTMax, newXMin, newXMax) -> {
            txtTMin.setText(String.format(java.util.Locale.US, "%.2f", newTMin));
            txtTMax.setText(String.format(java.util.Locale.US, "%.2f", newTMax));
            txtXMin.setText(String.format(java.util.Locale.US, "%.2f", newXMin));
            txtXMax.setText(String.format(java.util.Locale.US, "%.2f", newXMax));
            calculateAndRenderField();
        });

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
setStatus(I18n.get("status.invalid_bounds"), true);
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
            IsoclinesWindow.updateIfOpen(currentODE, canvas.getTMin(), canvas.getTMax(), canvas.getXMin(), canvas.getXMax());
                PhasePlotWindow.updateIfOpen(currentODE, canvas.getXMin(), canvas.getXMax());
setStatus(I18n.get("status.field_updated_success"), false);

        } catch (IllegalArgumentException ex) {
            System.err.println("[DEBUG EDO Error] " + ex.getMessage());
            ex.printStackTrace();
setStatus(I18n.format("status.ode_error_prefix", ex.getMessage()), true);
        } catch (Exception ex) {
            System.err.println("[DEBUG General Error] " + ex.getMessage());
            ex.printStackTrace();
setStatus(I18n.format("status.input_error_prefix", ex.getMessage()), true);
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
        reintegrateAllTrajectories();
        IsoclinesWindow.updateIfOpen(currentODE, canvas.getTMin(), canvas.getTMax(), canvas.getXMin(), canvas.getXMax());
                PhasePlotWindow.updateIfOpen(currentODE, canvas.getXMin(), canvas.getXMax());
    }

    /**
     * Integra a equação diferencial numericamente de forma bidirecional (forward e backward).
     */

    private void recalculateTrajectories() {
        if (currentODE == null || activeInitialConditions.isEmpty()) return;
        java.util.List<double[]> ics = new java.util.ArrayList<>(activeInitialConditions);
        activeInitialConditions.clear();
        canvas.clearTrajectories();
        for (double[] ic : ics) {
            traceOrbit(ic[0], ic[1], true);
        }
    }

            private void updateAutonomousControlsState() {
        boolean autonomous = currentODE != null && com.dfield.numerical.CriticalPointCalculator.isAutonomous(currentODE);
        // Mantemos os botões sempre clicáveis como recurso didático ativo
        btnCriticalPoints.setDisable(false);
        btnPhasePlot.setDisable(false);

        if (!autonomous) {
            if (showEquilibria) {
                showEquilibria = false;
                btnCriticalPoints.setStyle("");
                canvas.setCriticalPoints(java.util.Collections.emptyList(), false);
            }
        }
    }

    private void updateEquilibriaIfActive() {
        if (!showEquilibria || currentODE == null) {
            canvas.setCriticalPoints(java.util.Collections.emptyList(), false);
            return;
        }

        if (!com.dfield.numerical.CriticalPointCalculator.isAutonomous(currentODE)) {
            showEquilibria = false;
            btnCriticalPoints.setStyle("");
            canvas.setCriticalPoints(java.util.Collections.emptyList(), false);
            return;
        }

        var pts = com.dfield.numerical.CriticalPointCalculator.findEquilibria(
                currentODE, canvas.getXMin(), canvas.getXMax()
        );
        canvas.setCriticalPoints(pts, true);
    }

    private void traceOrbit(double t0, double x0) {
        traceOrbit(t0, x0, true);
    }

    private void traceOrbit(double t0, double x0, boolean record) {
        if (record) {
            activeInitialConditions.add(new double[]{t0, x0});
        }
        if (currentODE == null) {
            setStatus(I18n.get("status.ode_invalid_trace"), true);
            return;
        }

        double tMin = canvas.getTMin();
        double tMax = canvas.getTMax();
        double xMin = canvas.getXMin();
        double xMax = canvas.getXMax();

        double deltaT = tMax - tMin;
        double dt = deltaT / 600.0;
        int maxSteps = 8000;
double tol = 1e-6;
        double hMin = 1e-6;
        double hMaxForward = Math.max(0.05, (tMax - t0) / 20.0);
        double hMaxBackward = Math.max(0.05, (t0 - tMin) / 20.0);
        double boundMargin = (xMax - xMin) * 0.3;

        // 1. Ramo para frente (Forward: t0 -> tMax)
        List<TrajectoryPoint> forwardBranch = new ArrayList<>();
        forwardBranch.add(new TrajectoryPoint(t0, x0));

        double t = t0;
        double x = x0;
        double h = 0.05;
        TerminationReason fReason = TerminationReason.NORMAL;

        for (int i = 0; i < maxSteps && t < tMax; i++) {
            if (t + h > tMax) h = tMax - t;

            var res = DormandPrince45Solver.adaptiveStep(
(currT, currX) -> currentODE.eval(currT, currX),
                    t, x, h, tol, hMin, hMaxForward
            );

            if (Double.isNaN(res.nextX()) || Double.isInfinite(res.nextX())) {
                fReason = TerminationReason.BLOW_UP;
                break;
            }

            h = res.nextH();
            if (res.accepted()) {
                t = res.nextT();
                x = res.nextX();
                forwardBranch.add(new TrajectoryPoint(t, x));

           //     if (x < xMin - boundMargin || x > xMax + boundMargin) {
           //         fReason = TerminationReason.BOUNDS;
           //         break;
           //     }
if (Math.abs(x) > 10000 || Math.abs(t) > 10000) {
    fReason = TerminationReason.BOUNDS;
    break;
}        
    }
        }

        // 2. Ramo para trás (Backward: t0 -> tMin)
        List<TrajectoryPoint> backwardBranch = new ArrayList<>();
        backwardBranch.add(new TrajectoryPoint(t0, x0));

        t = t0;
        x = x0;
        h = -0.05;
        TerminationReason bReason = TerminationReason.NORMAL;

        for (int i = 0; i < maxSteps && t > tMin; i++) {
            if (t + h < tMin) h = tMin - t;

            var res = DormandPrince45Solver.adaptiveStep(
(currT, currX) -> currentODE.eval(currT, currX),
                    t, x, h, tol, hMin, hMaxBackward
            );

            if (Double.isNaN(res.nextX()) || Double.isInfinite(res.nextX())) {
                bReason = TerminationReason.BLOW_UP;
                break;
            }

            h = res.nextH();
            if (res.accepted()) {
                t = res.nextT();
                x = res.nextX();
                backwardBranch.add(new TrajectoryPoint(t, x));

                if (x < xMin - boundMargin || x > xMax + boundMargin) {
                    bReason = TerminationReason.BOUNDS;
                    break;
                }
            }
        }
    
        OrbitTrajectory trajectory = new OrbitTrajectory(forwardBranch, backwardBranch, fReason, bReason);
        canvas.addTrajectory(trajectory);

setStatus(I18n.format("status.solution_traced", t0, x0, trajectory.getFullOrderedTrajectory().size()), false);
    }

        private void reintegrateAllTrajectories() {
        if (currentODE == null || activeInitialConditions.isEmpty()) {
            return;
        }
        var ics = new java.util.ArrayList<>(activeInitialConditions);
        canvas.clearTrajectories();
        for (double[] ic : ics) {
            traceOrbit(ic[0], ic[1], false);
        }
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
    private void exportFieldSvg() {
        List<String> options = List.of(
                I18n.get("dialog.export.opt1"),
                I18n.get("dialog.export.opt2"),
                "3. Fundo Transparente (LaTeX / Vetorial)"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle(I18n.get("dialog.export_svg.title"));
        dialog.initStyle(javafx.stage.StageStyle.UTILITY);
        dialog.setResizable(false);
        dialog.setHeaderText(I18n.get("dialog.export_svg.header"));
        dialog.setContentText(I18n.get("dialog.export.content"));

        var cssSvg = getClass().getResource("/css/style.css");
        if (cssSvg != null) {
            dialog.getDialogPane().getStylesheets().add(cssSvg.toExternalForm());
        }

        var optResult = dialog.showAndWait();
        if (optResult.isEmpty()) return;

        String selected = optResult.get();
        Color bg;
        if (selected.startsWith("1")) {
            bg = Color.web("#12161f");
        } else if (selected.startsWith("2")) {
            bg = Color.WHITE;
        } else {
            bg = Color.TRANSPARENT;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Campo Vetorial (SVG)");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Vetor SVG (*.svg)", "*.svg")
        );
        fileChooser.setInitialFileName("dfieldX_campo.svg");

        var window = btnExportSvg.getScene() != null ? btnExportSvg.getScene().getWindow() : null;
        File file = fileChooser.showSaveDialog(window);
        if (file == null) return;

        try {
            canvas.exportSvg(file, bg);
setStatus(I18n.format("status.svg_exported", file.getName()), false);
        } catch (IOException ex) {
setStatus(I18n.get("status.svg_export_error"), true);
            ex.printStackTrace();
        }
    }

    private void exportFieldImage() {
        List<String> options = List.of(
                I18n.get("dialog.export.opt1"),
                I18n.get("dialog.export.opt2"),
                I18n.get("dialog.export.opt3")
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle(I18n.get("dialog.export_png.title"));
        dialog.initStyle(javafx.stage.StageStyle.UTILITY);
        dialog.setResizable(false);
        dialog.setHeaderText(I18n.get("dialog.export_png.header"));
        dialog.setContentText(I18n.get("dialog.export.content"));

        var cssPng = getClass().getResource("/css/style.css");
        if (cssPng != null) {
            dialog.getDialogPane().getStylesheets().add(cssPng.toExternalForm());
        }

        var optResult = dialog.showAndWait();
        if (optResult.isEmpty()) return;

        String selected = optResult.get();
        Color bg;
        if (selected.startsWith("1")) {
            bg = Color.web("#12161f");
        } else if (selected.startsWith("2")) {
            bg = Color.WHITE;
        } else {
            bg = Color.TRANSPARENT;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Campo de Direções (PNG)");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagem PNG (*.png)", "*.png")
        );
        fileChooser.setInitialFileName("dfieldX_campo.png");

        var window = btnExport.getScene() != null ? btnExport.getScene().getWindow() : null;
        File file = fileChooser.showSaveDialog(window);
        if (file == null) return;

        WritableImage image = canvas.exportSnapshot(bg);
        if (image == null) return;
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
setStatus(I18n.format("status.png_exported", file.getName()), false);
        } catch (IOException ex) {
setStatus(I18n.get("status.png_export_error"), true);
            ex.printStackTrace();
        }
    }
}
