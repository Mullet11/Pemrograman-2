package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Akun;
import service.AkunService;
import util.AlertHelper;
import application.Main;

public class AkunController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtNama;
    @FXML private ComboBox<String> cbRole;

    @FXML private TableView<Akun> table;
    @FXML private TableColumn<Akun, String> colUsername;
    @FXML private TableColumn<Akun, String> colNama;
    @FXML private TableColumn<Akun, String> colRole;

    private AkunService akunService = new AkunService();
    private ObservableList<Akun> list = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        try {
            cbRole.setItems(FXCollections.observableArrayList("admin", "kasir", "owner"));
            cbRole.getSelectionModel().select("admin");

            colUsername.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().getUsername()));
            colNama.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().getNama()));
            colRole.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().getRole()));

            list.setAll(akunService.getAll());
            table.setItems(list);

            // AUTO-FILL FORM
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, a) -> {
                if (a != null) isiForm(a);
            });

        } catch (Exception e) {
            AlertHelper.error("Init Error", e.getMessage());
        }
    }

    private void isiForm(Akun a) {
        txtUsername.setText(a.getUsername());
        txtPassword.setText(a.getPassword());
        txtNama.setText(a.getNama());
        cbRole.setValue(a.getRole());
    }

    private boolean validasi() {
        if (txtUsername.getText().isEmpty()) {
            AlertHelper.warn("Validasi", "Username wajib diisi.");
            return false;
        }
        if (txtPassword.getText().isEmpty()) {
            AlertHelper.warn("Validasi", "Password wajib diisi.");
            return false;
        }
        if (txtNama.getText().isEmpty()) {
            AlertHelper.warn("Validasi", "Nama wajib diisi.");
            return false;
        }
        return true;
    }

    @FXML
    private void simpan() {
        try {
            if (!validasi()) return;

            Akun a = new Akun();
            a.setUsername(txtUsername.getText());
            a.setPassword(txtPassword.getText());
            a.setNama(txtNama.getText());
            a.setRole(cbRole.getValue());

            akunService.save(a);
            list.setAll(akunService.getAll());
            AlertHelper.info("Sukses", "Akun dibuat.");

            clear();

        } catch (Exception e) {
            AlertHelper.error("Simpan Error", e.getMessage());
        }
    }

    @FXML
    private void edit() {
        try {
            Akun a = table.getSelectionModel().getSelectedItem();
            if (a == null) {
                AlertHelper.warn("Pilih Data", "Pilih akun dahulu.");
                return;
            }

            if (!validasi()) return;

            a.setUsername(txtUsername.getText());
            a.setPassword(txtPassword.getText());
            a.setNama(txtNama.getText());
            a.setRole(cbRole.getValue());

            akunService.update(a);
            list.setAll(akunService.getAll());

            AlertHelper.info("Sukses", "Akun diperbarui.");
            clear();

        } catch (Exception e) {
            AlertHelper.error("Edit Error", e.getMessage());
        }
    }

    @FXML
    private void hapus() {
        try {
            Akun a = table.getSelectionModel().getSelectedItem();

            if (a == null) {
                AlertHelper.warn("Pilih Data", "Tidak ada akun dipilih.");
                return;
            }

            if (!AlertHelper.confirm("Konfirmasi", "Hapus akun ini?")) return;

            akunService.delete(a.getIdAkun());
            list.setAll(akunService.getAll());

            AlertHelper.info("Sukses", "Akun dihapus.");

        } catch (Exception e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key") || msg.contains("constraint")) {
                AlertHelper.error("Hapus Error", 
                    "Akun ini tidak dapat dihapus karena sudah digunakan dalam transaksi.");
                return;
            }
            AlertHelper.error("Hapus Error", e.getMessage());
        }
    }

    private void clear() {
        txtUsername.clear();
        txtPassword.clear();
        txtNama.clear();
        cbRole.getSelectionModel().select("admin");
        table.getSelectionModel().clearSelection();
    }

    @FXML private void back() throws Exception { Main.changeScene("DashboardView.fxml"); }
    @FXML private void goMenu() throws Exception { Main.changeScene("MenuView.fxml"); }
    @FXML private void goTransaksi() throws Exception { Main.changeScene("TransaksiView.fxml"); }
    @FXML private void goPelanggan() throws Exception { Main.changeScene("PelangganView.fxml"); }
    @FXML private void logout() throws Exception { Main.changeScene("LoginView.fxml"); }
}
