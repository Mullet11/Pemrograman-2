package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.AlertHelper;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Load LoginView
        Parent root = FXMLLoader.load(Main.class.getResource("/views/LoginView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Mullet Bakery");
        stage.show();
    }

    // METHOD NAVIGASI
    public static void changeScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/views/" + fxml));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            AlertHelper.error("Error Load View", "Gagal memuat tampilan: " + fxml);
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
