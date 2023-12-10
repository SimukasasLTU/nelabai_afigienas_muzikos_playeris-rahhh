package com.example.work;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MusicPlayerUI {

    private Stage primaryStage;

    public MusicPlayerUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initializeUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/work/hello-view.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Music Player");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
