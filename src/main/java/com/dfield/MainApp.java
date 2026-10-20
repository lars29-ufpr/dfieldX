package com.dfield;

import com.dfield.ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow();
        Scene scene = new Scene(mainWindow, 1100, 750);

        if (getClass().getResource("/css/style.css") != null) {
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        }

        primaryStage.setTitle("dfieldX - Campo de Direções e Análise de EDOs 1D");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        var iconStream = getClass().getResourceAsStream("/icons/dfieldX_icon.png");
        if (iconStream != null) {
            primaryStage.getIcons().add(new Image(iconStream));
        }

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
