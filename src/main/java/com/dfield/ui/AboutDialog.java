package com.dfield.ui;

import com.dfield.util.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutDialog {

    public static void show(Stage owner) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle(I18n.get("about.title"));
        stage.setResizable(false);

        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(24, 30, 24, 30));
        root.setStyle("-fx-background-color: #12161f;");

        // Título Principal
        Label lblTitle = new Label("dfieldX");
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 22));
        lblTitle.setTextFill(Color.WHITE);

        // Versão
        Label lblVersion = new Label(I18n.get("about.version"));
        lblVersion.setFont(Font.font("System", FontWeight.NORMAL, 13));
        lblVersion.setTextFill(Color.web("#8395a7"));

        // Autor e Afiliação
        VBox authorBox = new VBox(4);
        authorBox.setAlignment(Pos.CENTER);

        Label lblDevBy = new Label(I18n.get("about.developed_by"));
        lblDevBy.setFont(Font.font("System", FontWeight.BOLD, 13));
        lblDevBy.setTextFill(Color.web("#54a0ff"));

        Label lblAuthor = new Label("Prof. Dr. Luiz Antonio Ribeiro de Santana");
        lblAuthor.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblAuthor.setTextFill(Color.web("#54a0ff"));

        Label lblDept = new Label("Departamento de Matemática (DMAT) — UFPR");
        lblDept.setFont(Font.font("System", FontWeight.BOLD, 13));
        lblDept.setTextFill(Color.web("#54a0ff"));

        authorBox.getChildren().addAll(lblDevBy, lblAuthor, lblDept);

        // Separador superior
        Separator sep1 = new Separator();
        sep1.setStyle("-fx-background-color: #2c3e50;");
        sep1.setPrefWidth(420);

        // Subtítulo
        Label lblSubtitle = new Label(I18n.get("about.subtitle"));
        lblSubtitle.setFont(Font.font("System", FontWeight.NORMAL, 13.5));
        lblSubtitle.setTextFill(Color.WHITE);
        lblSubtitle.setTextAlignment(TextAlignment.CENTER);

        // Lista de Características
        VBox featuresBox = new VBox(6);
        featuresBox.setAlignment(Pos.CENTER_LEFT);
        featuresBox.setPadding(new Insets(6, 10, 6, 10));

        String bullet = "• ";
        String[] features = {
            I18n.get("about.feat1"),
            I18n.get("about.feat2"),
            I18n.get("about.feat3"),
            I18n.get("about.feat4"),
            I18n.get("about.feat5")
        };

        for (String feat : features) {
            Label lblFeat = new Label(bullet + feat);
            lblFeat.setFont(Font.font("System", FontWeight.NORMAL, 12.5));
            lblFeat.setTextFill(Color.web("#c8d6e5"));
            lblFeat.setWrapText(true);
            featuresBox.getChildren().add(lblFeat);
        }

        // Separador inferior
        Separator sep2 = new Separator();
        sep2.setStyle("-fx-background-color: #2c3e50;");
        sep2.setPrefWidth(420);

        // Botão Fechar
        Button btnClose = new Button(I18n.get("btn.close"));
        btnClose.setFont(Font.font("System", FontWeight.BOLD, 13));
        btnClose.setTextFill(Color.WHITE);
        btnClose.setPrefWidth(120);
        btnClose.setPrefHeight(34);
        btnClose.setStyle(
            "-fx-background-color: #2ecc71; " +
            "-fx-background-radius: 6; " +
            "-fx-cursor: hand;"
        );
        btnClose.setOnMouseEntered(e -> btnClose.setStyle("-fx-background-color: #27ae60; -fx-background-radius: 6; -fx-cursor: hand;"));
        btnClose.setOnMouseExited(e -> btnClose.setStyle("-fx-background-color: #2ecc71; -fx-background-radius: 6; -fx-cursor: hand;"));
        btnClose.setOnAction(e -> stage.close());

        root.getChildren().addAll(lblTitle, lblVersion, authorBox, sep1, lblSubtitle, featuresBox, sep2, btnClose);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
