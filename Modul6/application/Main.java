package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Mengambil file FXML dari package View
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ViewTable.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Praktikum 6 - Modul6");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}