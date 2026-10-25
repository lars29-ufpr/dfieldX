package com.dfield.ui;

import com.dfield.util.I18n;

import com.dfield.numerical.IsoclineCalculator;
import com.dfield.core.ODEExpression;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;

public class IsoclinesWindow {

    private static Stage openStage = null;
    private static IsoclinesWindow instance = null;

    private final Stage stage;
    private final DirectionFieldCanvas canvas;
    private ODEExpression currentODE;
    private double tMin, tMax, xMin, xMax;

    private double currentC = 0.0;
    private boolean isVisible = true;
    private boolean lockZero = true;

    private final Label lblValueC = new Label("0.00");
    private final Label lblExplanation = new Label();

    public static void show(Stage ownerStage, DirectionFieldCanvas canvas,
                            ODEExpression ode, double tMin, double tMax, double xMin, double xMax) {
        if (openStage != null && openStage.isShowing()) {
            openStage.toFront();
            if (instance != null) {
                instance.updateODE(ode, tMin, tMax, xMin, xMax);
            }
            return;
        }

        Stage stage = new Stage();
        openStage = stage;
        stage.initOwner(ownerStage);
        stage.initModality(Modality.NONE);
        stage.setTitle(I18n.get("isoclines.title"));

        try {
            var iconStream = IsoclinesWindow.class.getResourceAsStream("/icons/dfieldX_icon.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }
        } catch (Exception ignored) {}

        instance = new IsoclinesWindow(stage, canvas, ode, tMin, tMax, xMin, xMax);
        stage.setOnHidden(e -> {
            if (canvas != null) {
                canvas.setIsoclineData(List.of(), List.of(), false, false);
            }
            openStage = null;
            instance = null;
        });
        stage.show();
    }

    public static void updateIfOpen(ODEExpression ode, double tMin, double tMax, double xMin, double xMax) {
        if (instance != null && openStage != null && openStage.isShowing()) {
            instance.updateODE(ode, tMin, tMax, xMin, xMax);
        }
    }

    private IsoclinesWindow(Stage stage, DirectionFieldCanvas canvas,
                            ODEExpression ode, double tMin, double tMax, double xMin, double xMax) {
        this.stage = stage;
        this.canvas = canvas;
        this.currentODE = ode;
        this.tMin = tMin;
        this.tMax = tMax;
        this.xMin = xMin;
        this.xMax = xMax;

        VBox root = new VBox(14);
        root.setPadding(new Insets(18));
        root.setStyle("-fx-background-color: #12161f;");

        Label title = new Label(I18n.get("isoclines.header"));
        title.setStyle("-fx-text-fill: #38bdf8; -fx-font-weight: bold; -fx-font-size: 13px;");

        // Painel Didático
        VBox didacticCard = new VBox(8);
        didacticCard.setPadding(new Insets(12));
        didacticCard.setStyle("-fx-background-color: #181d28; -fx-background-radius: 6px; -fx-border-color: #1e293b; -fx-border-radius: 6px;");

        Label lblIntro = new Label(I18n.get("isoclines.intro"));
        lblIntro.setWrapText(true);
        lblIntro.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");

        Label lblFormula = new Label("f(t, x) = c");
        lblFormula.setStyle("-fx-text-fill: #e879f9; -fx-font-size: 15px; -fx-font-weight: bold;");

        lblExplanation.setWrapText(true);
        lblExplanation.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 12px; -fx-line-spacing: 2px;");
        updateExplanationText(currentC);

        didacticCard.getChildren().addAll(lblIntro, lblFormula, lblExplanation);

        // Painel de Controle com Slider
        VBox controlCard = new VBox(10);
        controlCard.setPadding(new Insets(12));
        controlCard.setStyle("-fx-background-color: #181d28; -fx-background-radius: 6px; -fx-border-color: #1e293b; -fx-border-radius: 6px;");

        HBox sliderRow = new HBox(10);
        sliderRow.setAlignment(Pos.CENTER_LEFT);

        Label lblSliderName = new Label(I18n.get("isoclines.slope"));
        lblSliderName.setStyle("-fx-text-fill: #e879f9; -fx-font-weight: bold;");

        lblValueC.setStyle("-fx-text-fill: #ffffff; -fx-font-family: monospace; -fx-font-weight: bold; -fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnResetZero = new Button("c = 0");
        btnResetZero.setStyle("-fx-background-color: #21262d; -fx-text-fill: #fbbf24; -fx-font-weight: bold; -fx-cursor: hand;");

        sliderRow.getChildren().addAll(lblSliderName, lblValueC, spacer, btnResetZero);

        Slider sliderC = new Slider(-5.0, 5.0, 0.0);
        sliderC.setBlockIncrement(0.1);
        sliderC.setStyle("-fx-control-inner-background: #1e293b;");

        btnResetZero.setOnAction(e -> sliderC.setValue(0.0));

        sliderC.valueProperty().addListener((obs, oldV, newV) -> {
            currentC = newV.doubleValue();
            lblValueC.setText(String.format(Locale.US, "%.2f", currentC));
            updateExplanationText(currentC);
            recalculateAndRender();
        });

        CheckBox chkShow = new CheckBox(I18n.get("isoclines.show"));
        chkShow.setSelected(true);
        chkShow.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 12px;");
        chkShow.selectedProperty().addListener((obs, oldV, newV) -> {
            isVisible = newV;
            recalculateAndRender();
        });

        CheckBox chkLockZero = new CheckBox(I18n.get("isoclines.lock_zero"));
        chkLockZero.setSelected(true);
        chkLockZero.setStyle("-fx-text-fill: #fbbf24; -fx-font-size: 12px;");
        chkLockZero.selectedProperty().addListener((obs, oldV, newV) -> {
            lockZero = newV;
            recalculateAndRender();
        });

        controlCard.getChildren().addAll(sliderRow, sliderC, chkShow, chkLockZero);

        root.getChildren().addAll(title, didacticCard, controlCard);

        Scene scene = new Scene(root, 540, 480);
        stage.setScene(scene);
        stage.setMinWidth(480);
        stage.setMinHeight(440);

        recalculateAndRender();
    }

    private void updateExplanationText(double c) {
        if (Math.abs(c) < 1e-4) {
            lblExplanation.setText(I18n.get("isoclines.zero"));
        } else if (c > 0) {
            lblExplanation.setText(I18n.format("isoclines.pos", String.format(Locale.US, "%.2f", c), String.format(Locale.US, "%.2f", c)));
        } else {
            lblExplanation.setText(I18n.format("isoclines.neg", String.format(Locale.US, "%.2f", c), String.format(Locale.US, "%.2f", c)));
        }
    }

    public void updateODE(ODEExpression ode, double tMin, double tMax, double xMin, double xMax) {
        this.currentODE = ode;
        this.tMin = tMin;
        this.tMax = tMax;
        this.xMin = xMin;
        this.xMax = xMax;
        recalculateAndRender();
    }

    private void recalculateAndRender() {
        if (!isVisible || currentODE == null) {
            canvas.setIsoclineData(List.of(), List.of(), false, false);
            return;
        }

        Platform.runLater(() -> {
            List<IsoclineCalculator.Segment> dynamicSegs =
                    IsoclineCalculator.compute(currentODE, currentC, tMin, tMax, xMin, xMax, 90, 90);

            List<IsoclineCalculator.Segment> zeroSegs = lockZero ?
                    (Math.abs(currentC) < 1e-4 ? dynamicSegs :
                            IsoclineCalculator.compute(currentODE, 0.0, tMin, tMax, xMin, xMax, 90, 90))
                    : List.of();

            canvas.setIsoclineData(dynamicSegs, zeroSegs, isVisible, lockZero);
        });
    }
}
