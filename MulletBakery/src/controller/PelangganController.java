package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Pelanggan;
import service.PelangganService;
import util.AlertHelper;
import application.Main;

public class PelangganController {

    @FXML private TextField txtNama;
    @FXML private TextField txtTelepon;
    @FXML private TextField txtEmail;

    @FXML private TableView<Pelanggan> table;
    @FXML private TableColumn<Pelanggan, String> colNama;
    @FXML private TableColumn<Pelanggan, String> colTelepon;
    @FXML private TableColumn<Pelanggan, String> colEmail;

    private PelangganService pelangganService = new PelangganService();
    private ObservableList<Pelanggan> list = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        try {
            colNama.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().getNama()));
            colTelepon.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().getTelepon()));
            colEmail.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().getEmail()));

            list.setAll(pelangganService.getAll());
            table.setItems(list);

            // AUTO-FILL
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, p) -> {
                if (p != null) isiForm(p);
            });

        } catch (Exception e) {
            AlertHelper.error("Init Error", e.getMessage());
        }
    }

    private void isiForm(Pelanggan p) {
        txtNama.setText(p.getNama());
        txtTelepon.setText(p.getTelepon());
        txtEmail.setText(p.getEmail());
    }

    private boolean validasi() {
        if (txtNama.getText().isEmpty()) {
            AlertHelper.warn("Validasi", "Nama wajib diisi.");
            return false;
        }
        if (!txtTelepon.getText().matches("\\d+")) {
            AlertHelper.warn("Validasi", "Nomor telepon harus angka.");
            return false;
        }
        if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            AlertHelper.warn("Validasi", "Format email tidak valid.");
            return false;
        }
        return true;
    }

    @FXML
    private void simpan() {
        try {
            if (!validasi()) return;

            Pelanggan p = new Pelanggan();
            p.setNama(txtNama.getText());
            p.setTelepon(txtTelepon.getText());
            p.setEmail(txtEmail.getText());

            pelangganService.save(p);
            list.setAll(pelangganService.getAll());
            AlertHelper.info("Sukses", "Data pelanggan ditambahkan.");

            clear();

        } catch (Exception e) {
            AlertHelper.error("Simpan Error", e.getMessage());
        }
    }

    @FXML
    private void edit() {
        try {
            Pelanggan p = table.getSelectionModel().getSelectedItem();
            if (p == null) {
                AlertHelper.warn("Pilih Data", "Pilih pelanggan dahulu.");
                return;
            }

            if (!validasi()) return;

            p.setNama(txtNama.getText());
            p.setTelepon(txtTelepon.getText());
            p.setEmail(txtEmail.getText());

            pelangganService.update(p);
            list.setAll(pelangganService.getAll());

            AlertHelper.info("Sukses", "Pelanggan diperbarui.");
            clear();

        } catch (Exception e) {
            AlertHelper.error("Edit Error", e.getMessage());
        }
    }

    @FXML
    private void hapus() {
        try {
            Pelanggan p = table.getSelectionModel().getSelectedItem();
            if (p == null) {
                AlertHelper.warn("Pilih Data", "Tidak ada pelanggan yang dipilih.");
                return;
            }

            if (!AlertHelper.confirm("Konfirmasi", "Hapus pelanggan ini?")) return;

            pelangganService.delete(p.getId());
            list.setAll(pelangganService.getAll());

        } catch (Exception e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key") || msg.contains("constraint")) {
                AlertHelper.error("Hapus Error",
                    "Pelanggan tidak dapat dihapus karena sudah pernah melakukan transaksi.");
                return;
            }
            AlertHelper.error("Hapus Error", e.getMessage());
        }
    }

    private void clear() {
        txtNama.clear();
        txtTelepon.clear();
        txtEmail.clear();
        table.getSelectionModel().clearSelection();
    }

    @FXML private void back() throws Exception { Main.changeScene("DashboardView.fxml"); }
}
