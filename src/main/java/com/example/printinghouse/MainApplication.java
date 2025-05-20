package com.example.printinghouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/printinghouse/Main.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 800);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setTitle("Printing House");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}