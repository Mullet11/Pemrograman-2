package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Akun;
import service.AkunService;
import util.AlertHelper;
import util.Session;
import application.Main;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private AkunService akunService = new AkunService();
    private int failedAttempts = 0;
    private static final int MAX_FAILED = 5;

    @FXML
    private void login() {
        try {
            if (failedAttempts >= MAX_FAILED) {
                AlertHelper.warn("Akses Ditutup", "Terlalu banyak percobaan gagal.");
                return;
            }

            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                AlertHelper.warn("Validasi", "Username & Password wajib diisi.");
                return;
            }

            Akun akun = akunService.findByUsername(username);
            if (akun == null) {
                failedAttempts++;
                AlertHelper.warn("Login Gagal", "Username tidak ditemukan.");
                return;
            }

            if (!akun.getPassword().equals(password)) {
                failedAttempts++;
                AlertHelper.warn("Login Gagal", "Password salah.");
                return;
            }

            Session.setCurrentAkun(akun);
            Main.changeScene("DashboardView.fxml");

        } catch (Exception e) {
            AlertHelper.error("Login Error", e.getMessage());
        }
    }
}
