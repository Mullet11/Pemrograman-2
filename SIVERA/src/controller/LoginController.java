package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Account;
import service.AuthService;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private VBox leftPane;

    @FXML
    private VBox rightPane;

    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        playIntroAnimation();
    }

    private void playIntroAnimation() {

        leftPane.setTranslateX(-450);
        rightPane.setTranslateX(450);

        TranslateTransition leftAnim =
                new TranslateTransition(Duration.millis(700), leftPane);
        leftAnim.setToX(0);
        leftAnim.setInterpolator(javafx.animation.Interpolator.EASE_OUT);

        TranslateTransition rightAnim =
                new TranslateTransition(Duration.millis(700), rightPane);
        rightAnim.setToX(0);
        rightAnim.setInterpolator(javafx.animation.Interpolator.EASE_OUT);

        leftAnim.play();
        rightAnim.play();
    }

    @FXML
    private void handleLogin(ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // VALIDASI
        if (username.isEmpty() || password.isEmpty()) {
            showWarning("Login Gagal", "Username dan password wajib diisi.");
            return;
        }

        if (username.length() < 4 || username.length() > 20) {
            showWarning("Login Gagal", "Username harus 4–20 karakter.");
            return;
        }

        if (!username.matches("[a-zA-Z0-9]+")) {
            showWarning("Login Gagal", "Username hanya boleh huruf dan angka.");
            return;
        }

        if (password.length() < 6) {
            showWarning("Login Gagal", "Password minimal 6 karakter.");
            return;
        }

        Account account = authService.login(username, password);

        if (authService.isAuthenticated(account)) {
            loadDashboard(event);
        } else {
            showWarning("Login Gagal", "Username atau password salah.");
            clearForm();
        }
    }

    private void loadDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/views/dashboard.fxml")
            );

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("SIVERA - Dashboard");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error Sistem", "Gagal membuka dashboard.");
        }
    }

    private void clearForm() {
        passwordField.clear();
        usernameField.requestFocus();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
