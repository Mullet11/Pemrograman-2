package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Pelanggan;
import service.PelangganService;

import java.util.Optional;

public class PelangganController {

    @FXML private TextField txtNama;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelepon;
    @FXML private TextField txtSearch;

    @FXML private TableView<Pelanggan> tblPelanggan;
    @FXML private TableColumn<Pelanggan, String> colNama;
    @FXML private TableColumn<Pelanggan, String> colEmail;
    @FXML private TableColumn<Pelanggan, String> colTelepon;

    @FXML private Label lblTotal;
    @FXML private Button btnClear;

    private final PelangganService service = new PelangganService();
    private int selectedId = 0;
    private ObservableList<Pelanggan> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Binding kolom tabel
        colNama.setCellValueFactory(cell -> cell.getValue().namaProperty());
        colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());
        colTelepon.setCellValueFactory(cell -> cell.getValue().teleponProperty());

        // Listener klik tabel
        tblPelanggan.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedId = newSel.getId();
                txtNama.setText(newSel.getNama());
                txtEmail.setText(newSel.getEmail());
                txtTelepon.setText(newSel.getTelepon());
                resetFieldHighlight();
            }
        });

        setupSearchFilter();
        setupRealtimeValidation();
        loadData();
    }

    // Realtime Validation
    private void setupRealtimeValidation() {
        // Nama: hanya huruf, spasi, dan apostrophe
        txtNama.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[\\p{L}\\s']*")) {
                highlightError(txtNama);
            } else {
                txtNama.setStyle("");
            }
        });

        // Email: tidak boleh mengandung spasi
        txtEmail.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.contains(" ")) {
                highlightError(txtEmail);
            } else {
                txtEmail.setStyle("");
            }
        });

        // Telepon: hanya angka dan + (boleh kosong saat user baru mulai mengetik)
        txtTelepon.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[0-9+]*")) {
                highlightError(txtTelepon);
            } else {
                txtTelepon.setStyle("");
            }
        });
    }

    // Search / Filter tabel
    private void setupSearchFilter() {
        if (txtSearch != null) {
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData(newValue);
            });
        }
    }

    private void filterData(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            tblPelanggan.setItems(masterData);
            updateTotalLabel();
            return;
        }

        FilteredList<Pelanggan> filtered = new FilteredList<>(masterData, p -> true);

        filtered.setPredicate(p -> {
            String f = searchText.toLowerCase();
            return p.getNama().toLowerCase().contains(f)
                || p.getEmail().toLowerCase().contains(f)
                || p.getTelepon().toLowerCase().contains(f);
        });

        SortedList<Pelanggan> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(tblPelanggan.comparatorProperty());
        tblPelanggan.setItems(sorted);
        updateTotalLabel();
    }

    // Event Button
    @FXML
    private void onAdd() {
        resetFieldHighlight();

        Pelanggan p = new Pelanggan(
            txtNama.getText(),
            txtEmail.getText(),
            txtTelepon.getText()
        );

        try {
            service.addData(p); // validasi ada di service
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil disimpan!");
            clearForm();
            loadData();
        } catch (Exception e) {
            // Tampilkan pesan validasi & highlight field yang salah
            detectInputError(e.getMessage());
            showAlert(Alert.AlertType.WARNING, "Validasi Gagal", e.getMessage());
        }
    }

    @FXML
    private void onEdit() {
        if (selectedId == 0) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih data dari tabel terlebih dahulu!");
            return;
        }

        resetFieldHighlight();

        Pelanggan p = new Pelanggan(
            selectedId,
            txtNama.getText(),
            txtEmail.getText(),
            txtTelepon.getText()
        );

        try {
            service.updateData(p);
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil diupdate!");
            clearForm();
            loadData();
        } catch (Exception e) {
            detectInputError(e.getMessage());
            showAlert(Alert.AlertType.WARNING, "Validasi Gagal", e.getMessage());
        }
    }

    @FXML
    private void onDelete() {
        if (selectedId == 0) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih data dulu!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Yakin hapus data ini?");
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText("Data yang dipilih akan dihapus permanen");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                service.deleteData(selectedId);
                clearForm();
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data terhapus!");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    @FXML
    private void onClear() {
        clearForm();
    }

    @FXML
    private void onRefresh() {
        loadData();
        if (txtSearch != null) {
            txtSearch.clear();
        }
        showAlert(Alert.AlertType.INFORMATION, "Refresh", "Data berhasil dimuat ulang!");
    }

    @FXML
    private void onExportCSV() {
        try {
            service.exportToCSV(masterData);
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil di-export ke CSV!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error Export", e.getMessage());
        }
    }

    // Helper: Highlight & Deteksi error field
    private void detectInputError(String msg) {
        if (msg == null) return;
        String lower = msg.toLowerCase();
        if (lower.contains("nama")) highlightError(txtNama);
        if (lower.contains("email")) highlightError(txtEmail);
        if (lower.contains("telepon")) highlightError(txtTelepon);
    }

    private void highlightError(TextField field) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 2;");
    }

    private void resetFieldHighlight() {
        txtNama.setStyle("");
        txtEmail.setStyle("");
        txtTelepon.setStyle("");
    }

    // Helper: Load data, label, alert, clear
    private void loadData() {
        try {
            masterData = FXCollections.observableArrayList(service.getAll());
            tblPelanggan.setItems(masterData);
            updateTotalLabel();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Gagal memuat data: " + e.getMessage());
        }
    }

    private void updateTotalLabel() {
        if (lblTotal != null) {
            lblTotal.setText("Total Data: " + tblPelanggan.getItems().size());
        }
    }

    private void clearForm() {
        resetFieldHighlight();
        txtNama.clear();
        txtEmail.clear();
        txtTelepon.clear();
        selectedId = 0;
        tblPelanggan.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

