package com.dfield.ui;

import com.dfield.util.I18n;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class ParameterSlidersWindow {

    private static Stage openStage = null;

    public static void show(Stage ownerStage, String currentParamsStr, Consumer<String> onParametersChanged) {
        if (openStage != null && openStage.isShowing()) {
            openStage.close();
        }

        Stage stage = new Stage();
        openStage = stage;
        stage.initOwner(ownerStage);
        stage.initModality(Modality.NONE);
        stage.setTitle(I18n.get("sliders.title"));

        try {
            var iconStream = ParameterSlidersWindow.class.getResourceAsStream("/icons/dfieldX_icon.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }
        } catch (Exception ignored) {}

        Map<String, Double> paramMap = parseParameters(currentParamsStr);

        VBox contentBox = new VBox(14);
        contentBox.setPadding(new Insets(20));
        contentBox.setStyle("-fx-background-color: #12161f;");

        Label header = new Label(I18n.get("sliders.header"));
        header.setStyle("-fx-text-fill: #38bdf8; -fx-font-weight: bold; -fx-font-size: 13px;");
        contentBox.getChildren().add(header);

        if (paramMap.isEmpty()) {
            Label lblEmpty = new Label(I18n.get("sliders.empty"));
            lblEmpty.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
            contentBox.getChildren().add(lblEmpty);
        } else {
            for (Map.Entry<String, Double> entry : paramMap.entrySet()) {
                String name = entry.getKey();
                double initialVal = entry.getValue();

                double minVal = initialVal > 0 ? 0.0 : (initialVal == 0 ? -10.0 : initialVal * 3.0);
                double maxVal = initialVal > 0 ? Math.max(initialVal * 3.0, 10.0) : (initialVal == 0 ? 10.0 : 0.0);
                if (minVal == maxVal) {
                    minVal -= 5.0;
                    maxVal += 5.0;
                }

                VBox card = new VBox(6);
                card.setStyle("-fx-background-color: #181d28; -fx-padding: 10px; -fx-background-radius: 6px; -fx-border-color: #1e293b; -fx-border-radius: 6px;");

                HBox titleRow = new HBox(8);
                titleRow.setAlignment(Pos.CENTER_LEFT);

                Label lblName = new Label(name);
                lblName.setStyle("-fx-text-fill: #a78bfa; -fx-font-weight: bold; -fx-font-size: 13px;");

                Label lblVal = new Label(String.format(Locale.US, "%.3f", initialVal));
                lblVal.setStyle("-fx-text-fill: #f1f5f9; -fx-font-family: monospace; -fx-font-weight: bold;");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                titleRow.getChildren().addAll(lblName, spacer, lblVal);

                Slider slider = new Slider(minVal, maxVal, initialVal);
                slider.setBlockIncrement(0.01);
                slider.setStyle("-fx-control-inner-background: #1e293b;");

                slider.valueProperty().addListener((obs, oldV, newV) -> {
                    double val = newV.doubleValue();
                    lblVal.setText(String.format(Locale.US, "%.3f", val));
                    paramMap.put(name, val);
                    String updatedStr = serializeParameters(paramMap);
                    Platform.runLater(() -> onParametersChanged.accept(updatedStr));
                });

                HBox rangeBox = new HBox(8);
                rangeBox.setAlignment(Pos.CENTER_LEFT);
                Label lblMin = new Label(String.format(Locale.US, "%.1f", minVal));
                lblMin.setStyle("-fx-text-fill: #475569; -fx-font-size: 11px;");
                Region rSpacer = new Region();
                HBox.setHgrow(rSpacer, Priority.ALWAYS);
                Label lblMax = new Label(String.format(Locale.US, "%.1f", maxVal));
                lblMax.setStyle("-fx-text-fill: #475569; -fx-font-size: 11px;");
                rangeBox.getChildren().addAll(lblMin, rSpacer, lblMax);

                card.getChildren().addAll(titleRow, slider, rangeBox);
                contentBox.getChildren().add(card);
            }
        }

        ScrollPane scroll = new ScrollPane(contentBox);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #12161f; -fx-background-color: transparent;");

        Scene scene = new Scene(scroll, 380, 480);
        stage.setScene(scene);
        stage.setMinWidth(320);
        stage.setMinHeight(300);
        stage.setOnHidden(e -> openStage = null);
        stage.show();
    }

    private static Map<String, Double> parseParameters(String raw) {
        Map<String, Double> map = new LinkedHashMap<>();
        if (raw == null || raw.isBlank()) return map;
        String[] tokens = raw.split("[,;\\s]+");
        for (String t : tokens) {
            if (t.contains("=")) {
                String[] parts = t.split("=");
                if (parts.length == 2) {
                    try {
                        String pName = parts[0].trim();
                        double pVal = Double.parseDouble(parts[1].trim());
                        if (!pName.isEmpty()) {
                            map.put(pName, pVal);
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return map;
    }

    private static String serializeParameters(Map<String, Double> map) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, Double> e : map.entrySet()) {
            if (i > 0) sb.append(", ");
            sb.append(e.getKey()).append("=").append(String.format(Locale.US, "%.3f", e.getValue()));
            i++;
        }
        return sb.toString();
    }
}
