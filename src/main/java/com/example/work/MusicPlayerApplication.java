package com.example.work;

import javafx.application.Application;
import javafx.stage.Stage;

public class MusicPlayerApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        MusicPlayerUI musicPlayerUI = new MusicPlayerUI(primaryStage);
        musicPlayerUI.initializeUI();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
