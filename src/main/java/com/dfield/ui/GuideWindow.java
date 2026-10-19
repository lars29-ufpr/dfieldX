package com.dfield.ui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class GuideWindow {

    public static void show(Stage ownerStage) {
        Stage stage = new Stage();
        stage.initOwner(ownerStage);
        stage.initModality(Modality.NONE);
        stage.setTitle("dfieldX — Guia Didático de Modelos Diferenciais");

        try {
            var iconStream = GuideWindow.class.getResourceAsStream("/icons/dfieldX_icon.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }
        } catch (Exception ignored) {}

        WebView webView = new WebView();
        URL guideUrl = GuideWindow.class.getResource("/guide/tutorial.html");
        if (guideUrl != null) {
            webView.getEngine().load(guideUrl.toExternalForm());
        }

        BorderPane root = new BorderPane(webView);
        root.setStyle("-fx-background-color: #0f131a;");

        Scene scene = new Scene(root, 960, 750);
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
    }
}
