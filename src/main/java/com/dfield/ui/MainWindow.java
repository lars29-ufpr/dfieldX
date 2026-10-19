package com.dfield.ui;

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
private final Button btnGallery = new Button("📚 Galeria de Modelos");
private final Button btnExport = new Button("📷 Exportar PNG");
    private final Button btnExportSvg = new Button("📐 Exportar SVG");

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
btnGallery.setMaxWidth(Double.MAX_VALUE);
btnGallery.getStyleClass().addAll("btn", "btn-purple");
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
btnGallery,
                btnUpdate,
                new Separator(),
                lblOrbitTitle,
                icBox,
                btnClear,
btnExport,
                btnExportSvg
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
                setStatus("Valores inválidos para (t0, x0).", true);
            }
        });

        // Limpa trajetórias
        btnClear.setOnAction(e -> {
            canvas.clearTrajectories();
            setStatus("Trajetórias removidas.", false);
        });
btnExport.setOnAction(e -> exportFieldImage());
        btnExportSvg.setOnAction(e -> exportFieldSvg());
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

                if (x < xMin - boundMargin || x > xMax + boundMargin) {
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
    private void exportFieldSvg() {
        List<String> options = List.of(
                "1. Fundo Escuro (Tema Nativo #12161f)",
                "2. Fundo Branco (Impressão / Artigos)",
                "3. Fundo Transparente (LaTeX / Vetorial)"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Exportar Gráfico SVG");
        dialog.setHeaderText("Configuração de Exportação Vetorial");
        dialog.setContentText("Esquema de fundo:");

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
            setStatus("Gráfico vetorial SVG exportado: " + file.getName(), false);
        } catch (IOException ex) {
            setStatus("Erro ao exportar arquivo SVG.", true);
            ex.printStackTrace();
        }
    }

    private void exportFieldImage() {
        List<String> options = List.of(
                "1. Fundo Escuro (Tema Nativo #12161f)",
                "2. Fundo Branco (Impressão / Artigos)",
                "3. Fundo Transparente (LaTeX / Inserção)"
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Exportar Imagem PNG");
        dialog.setHeaderText("Configuração de Exportação Gráfica");
        dialog.setContentText("Esquema de fundo:");

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
            setStatus("Imagem exportada com sucesso: " + file.getName(), false);
        } catch (IOException ex) {
            setStatus("Erro ao exportar imagem gráfica.", true);
            ex.printStackTrace();
        }
    }
}
