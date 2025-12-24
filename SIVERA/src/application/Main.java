package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            Font.loadFont(
                getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf"),
                12
            );

            Font.loadFont(
                getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf"),
                12
            );
            
            Font.loadFont(
                    getClass().getResourceAsStream("/fonts/Poppins-SemiBold.ttf"),
                    12
                );

            Parent root = FXMLLoader.load(
                getClass().getResource("/views/login.fxml")
            );

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                getClass().getResource("application.css").toExternalForm()
            );

            primaryStage.setTitle("SIVERA - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
