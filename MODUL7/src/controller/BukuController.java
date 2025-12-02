package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Buku;
import service.BukuService;

import java.util.Optional;

public class BukuController {

    @FXML private TextField txtJudul;
    @FXML private TextField txtPenulis;
    @FXML private TextField txtHarga;
    @FXML private TextField txtStok;
    @FXML private TextField txtSearch;
    
    @FXML private TableView<Buku> tblBuku;
    @FXML private TableColumn<Buku, String> colJudul;
    @FXML private TableColumn<Buku, String> colPenulis;
    @FXML private TableColumn<Buku, Number> colHarga;
    @FXML private TableColumn<Buku, Integer> colStok;
    
    @FXML private Label lblTotal;
    @FXML private Label lblNilaiStok;

    private final BukuService service = new BukuService();
    private int selectedId = 0;
    private ObservableList<Buku> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup Kolom Tabel
        colJudul.setCellValueFactory(cell -> cell.getValue().judulProperty());
        colPenulis.setCellValueFactory(cell -> cell.getValue().penulisProperty());
        colHarga.setCellValueFactory(cell -> cell.getValue().hargaProperty());
        colStok.setCellValueFactory(cell -> cell.getValue().stokProperty().asObject());

        // Format kolom harga dengan Rupiah
        colHarga.setCellFactory(column -> new TableCell<Buku, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Rp %,.0f", item.doubleValue()));
                }
            }
        });

        // Listener saat baris tabel diklik
        tblBuku.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedId = newSelection.getBukuId();
                    txtJudul.setText(newSelection.getJudul());
                    txtPenulis.setText(newSelection.getPenulis());
                    txtHarga.setText(String.valueOf(newSelection.getHarga()));
                    txtStok.setText(String.valueOf(newSelection.getStok()));
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
            tblBuku.setItems(masterData);
            updateLabels();
            return;
        }

        FilteredList<Buku> filteredData = new FilteredList<>(masterData, p -> true);
        
        filteredData.setPredicate(buku -> {
            String lowerCaseFilter = searchText.toLowerCase();
            
            if (buku.getJudul().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (buku.getPenulis().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(buku.getHarga()).contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(buku.getStok()).contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });

        SortedList<Buku> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblBuku.comparatorProperty());
        tblBuku.setItems(sortedData);
        updateLabels();
    }

    @FXML
    private void onAdd() {
        processInput(() -> {
            Buku b = new Buku(
                txtJudul.getText(), 
                txtPenulis.getText(), 
                Double.parseDouble(txtHarga.getText()), 
                Integer.parseInt(txtStok.getText())
            );
            try {
                service.addData(b);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data buku berhasil disimpan!");
        });
    }

    @FXML
    private void onEdit() {
        if (selectedId == 0) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih data dari tabel terlebih dahulu!");
            return;
        }
        
        processInput(() -> {
            Buku b = new Buku(
                selectedId,
                txtJudul.getText(), 
                txtPenulis.getText(), 
                Double.parseDouble(txtHarga.getText()), 
                Integer.parseInt(txtStok.getText())
            );
            try {
                service.updateData(b);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data buku berhasil diupdate!");
        });
    }

    @FXML
    private void onDelete() {
        if (selectedId == 0) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih data dulu!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Yakin hapus data buku ini?");
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText("Data buku akan dihapus permanen");
        Optional<ButtonType> result = confirm.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                service.deleteData(selectedId);
                clearForm();
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data buku terhapus!");
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

    // Helper Method untuk mengurangi duplikasi kode try-catch
    private void processInput(Runnable action) {
        try {
            action.run();
            clearForm();
            loadData();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Harga dan Stok harus berupa angka!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Gagal", e.getMessage());
        }
    }

    private void loadData() {
        try {
            masterData = FXCollections.observableArrayList(service.getAll());
            tblBuku.setItems(masterData);
            updateLabels();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Gagal memuat data: " + e.getMessage());
        }
    }

    private void updateLabels() {
        if (lblTotal != null) {
            int total = tblBuku.getItems().size();
            lblTotal.setText("Total Buku: " + total);
        }
        
        if (lblNilaiStok != null) {
            try {
                double nilaiStok = service.getTotalNilaiStok();
                lblNilaiStok.setText(String.format("Nilai Stok: Rp %,.0f", nilaiStok));
            } catch (Exception e) {
                lblNilaiStok.setText("Nilai Stok: -");
            }
        }
    }

    private void clearForm() {
        txtJudul.clear();
        txtPenulis.clear();
        txtHarga.clear();
        txtStok.clear();
        selectedId = 0;
        tblBuku.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}