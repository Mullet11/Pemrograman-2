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
    @FXML private TextField txtTelepon; // ✅ CHANGED: txtUmur -> txtTelepon
    @FXML private TextField txtSearch;
    
    @FXML private TableView<Pelanggan> tblPelanggan;
    @FXML private TableColumn<Pelanggan, String> colNama;
    @FXML private TableColumn<Pelanggan, String> colEmail;
    @FXML private TableColumn<Pelanggan, String> colTelepon; // ✅ CHANGED: Integer -> String
    
    @FXML private Label lblTotal;
    @FXML private Button btnClear;

    private final PelangganService service = new PelangganService();
    private int selectedId = 0;
    private ObservableList<Pelanggan> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup Kolom Tabel
        colNama.setCellValueFactory(cell -> cell.getValue().namaProperty());
        colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());
        colTelepon.setCellValueFactory(cell -> cell.getValue().teleponProperty()); // ✅ CHANGED

        // Listener saat baris tabel diklik
        tblPelanggan.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedId = newSelection.getId();
                    txtNama.setText(newSelection.getNama());
                    txtEmail.setText(newSelection.getEmail());
                    txtTelepon.setText(newSelection.getTelepon()); // ✅ CHANGED
                }
            }
        );

        // Setup Search Filter
        setupSearchFilter();
        
        loadData();
    }

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

        FilteredList<Pelanggan> filteredData = new FilteredList<>(masterData, p -> true);
        
        filteredData.setPredicate(pelanggan -> {
            String lowerCaseFilter = searchText.toLowerCase();
            
            if (pelanggan.getNama().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (pelanggan.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (pelanggan.getTelepon().toLowerCase().contains(lowerCaseFilter)) { // ✅ CHANGED
                return true;
            }
            return false;
        });

        SortedList<Pelanggan> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblPelanggan.comparatorProperty());
        tblPelanggan.setItems(sortedData);
        updateTotalLabel();
    }

    @FXML
    private void onAdd() {
        processInput(() -> {
            // ✅ CHANGED: Tidak perlu parse integer lagi
            Pelanggan p = new Pelanggan(
                txtNama.getText(), 
                txtEmail.getText(), 
                txtTelepon.getText()
            );
            try {
                service.addData(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil disimpan!");
        });
    }

    @FXML
    private void onEdit() {
        if (selectedId == 0) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih data dari tabel terlebih dahulu!");
            return;
        }
        
        processInput(() -> {
            // ✅ CHANGED: Tidak perlu parse integer lagi
            Pelanggan p = new Pelanggan(
                selectedId, 
                txtNama.getText(), 
                txtEmail.getText(), 
                txtTelepon.getText()
            );
            try {
                service.updateData(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil diupdate!");
        });
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

    private void processInput(Runnable action) {
        try {
            action.run();
            clearForm();
            loadData();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Gagal", e.getMessage());
        }
    }

    private void loadData() {
        try {
            masterData = FXCollections.observableArrayList(service.getAll());
            tblPelanggan.setItems(masterData);
            updateTotalLabel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Gagal memuat data: " + e.getMessage());
        }
    }

    private void updateTotalLabel() {
        if (lblTotal != null) {
            int total = tblPelanggan.getItems().size();
            lblTotal.setText("Total Data: " + total);
        }
    }

    private void clearForm() {
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