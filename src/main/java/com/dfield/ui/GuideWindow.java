package com.dfield.ui;

import com.dfield.util.I18n;

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
        stage.setTitle(I18n.get("guide.title"));

        try {
            var iconStream = GuideWindow.class.getResourceAsStream("/icons/dfieldX_icon.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }
        } catch (Exception ignored) {}

        WebView webView = new WebView();
        String lang = com.dfield.util.I18n.getCurrentLocale().getLanguage();
        
        String guidePath;
        if (lang.equalsIgnoreCase("pt")) {
            guidePath = "/guide/tutorial.html";
        } else if (lang.equalsIgnoreCase("es")) {
            guidePath = "/guide/tutorial_es.html";
        } else if (lang.equalsIgnoreCase("fr")) {
            guidePath = "/guide/tutorial_fr.html"; // Rota para o pplaneX
        } else if (lang.equalsIgnoreCase("ru")) {
            guidePath = "/guide/tutorial_ru.html";
        } else {
            guidePath = "/guide/tutorial_en.html"; // Fallback padrão
        }

        URL guideUrl = GuideWindow.class.getResource(guidePath);
        if (guideUrl == null) {
            guideUrl = GuideWindow.class.getResource("/guide/tutorial.html");
        }
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
