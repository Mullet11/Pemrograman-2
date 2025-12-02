package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Penjualan;
import model.Pelanggan;
import model.Buku;
import service.PenjualanService;
import service.PelangganService;
import service.BukuService;

import java.time.LocalDate;
import java.util.Optional;

public class PenjualanController {

    @FXML private ComboBox<Pelanggan> cbPelanggan;
    @FXML private ComboBox<Buku> cbBuku;
    @FXML private TextField txtJumlah;
    @FXML private TextField txtTotalHarga;
    @FXML private DatePicker dpTanggal;
    @FXML private TextField txtSearch;
    
    @FXML private TableView<Penjualan> tblPenjualan;
    @FXML private TableColumn<Penjualan, String> colPelanggan;
    @FXML private TableColumn<Penjualan, String> colBuku;
    @FXML private TableColumn<Penjualan, Number> colJumlah;
    @FXML private TableColumn<Penjualan, Number> colTotalHarga;
    @FXML private TableColumn<Penjualan, LocalDate> colTanggal;
    
    @FXML private Label lblTotal;
    @FXML private Label lblPendapatan;

    private final PenjualanService service = new PenjualanService();
    private final PelangganService pelangganService = new PelangganService();
    private final BukuService bukuService = new BukuService();
    
    private int selectedId = 0;
    private ObservableList<Penjualan> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup Kolom Tabel
        colPelanggan.setCellValueFactory(cell -> cell.getValue().namaPelangganProperty());
        colBuku.setCellValueFactory(cell -> cell.getValue().judulBukuProperty());
        colJumlah.setCellValueFactory(cell -> cell.getValue().jumlahProperty());
        colTotalHarga.setCellValueFactory(cell -> cell.getValue().totalHargaProperty());
        colTanggal.setCellValueFactory(cell -> cell.getValue().tanggalProperty());

        // Format kolom total harga
        colTotalHarga.setCellFactory(column -> new TableCell<Penjualan, Number>() {
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

        // Format kolom tanggal
        colTanggal.setCellFactory(column -> new TableCell<Penjualan, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                }
            }
        });

        // Set default date to today
        dpTanggal.setValue(LocalDate.now());

        // Listener saat baris tabel diklik
        tblPenjualan.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedId = newSelection.getPenjualanId();
                    txtJumlah.setText(String.valueOf(newSelection.getJumlah()));
                    txtTotalHarga.setText(String.valueOf(newSelection.getTotalHarga()));
                    dpTanggal.setValue(newSelection.getTanggal());
                    
                    // Select combobox items - FIXED: Ganti nama method
                    selectPelangganInComboBox(newSelection.getPelangganId());
                    selectBukuInComboBox(newSelection.getBukuId());
                }
            }
        );

        // Auto calculate total when jumlah or buku changes
        txtJumlah.textProperty().addListener((obs, old, newVal) -> calculateTotal());
        cbBuku.valueProperty().addListener((obs, old, newVal) -> calculateTotal());

        setupSearchFilter();
        loadComboBoxData();
        loadData();
    }

    // FIXED: Pisahkan method dengan nama yang berbeda
    private void selectPelangganInComboBox(int pelangganId) {
        for (Pelanggan p : cbPelanggan.getItems()) {
            if (p.getId() == pelangganId) {
                cbPelanggan.setValue(p);
                break;
            }
        }
    }

    private void selectBukuInComboBox(int bukuId) {
        for (Buku b : cbBuku.getItems()) {
            if (b.getBukuId() == bukuId) {
                cbBuku.setValue(b);
                break;
            }
        }
    }

    private void calculateTotal() {
        try {
            if (cbBuku.getValue() != null && !txtJumlah.getText().isEmpty()) {
                double harga = cbBuku.getValue().getHarga();
                int jumlah = Integer.parseInt(txtJumlah.getText());
                double total = harga * jumlah;
                txtTotalHarga.setText(String.format("%.0f", total));
            }
        } catch (NumberFormatException e) {
            // Ignore
        }
    }

    private void loadComboBoxData() {
        try {
            // Load Pelanggan
            ObservableList<Pelanggan> pelangganList = 
                FXCollections.observableArrayList(pelangganService.getAll());
            cbPelanggan.setItems(pelangganList);
            
            // Load Buku
            ObservableList<Buku> bukuList = 
                FXCollections.observableArrayList(bukuService.getAll());
            cbBuku.setItems(bukuList);
            
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat data: " + e.getMessage());
        }
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
            tblPenjualan.setItems(masterData);
            updateLabels();
            return;
        }

        FilteredList<Penjualan> filteredData = new FilteredList<>(masterData, p -> true);
        
        filteredData.setPredicate(penjualan -> {
            String lowerCaseFilter = searchText.toLowerCase();
            
            if (penjualan.getNamaPelanggan().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (penjualan.getJudulBuku().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(penjualan.getTotalHarga()).contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });

        SortedList<Penjualan> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblPenjualan.comparatorProperty());
        tblPenjualan.setItems(sortedData);
        updateLabels();
    }

    @FXML
    private void onAdd() {
        processInput(() -> {
            Penjualan p = new Penjualan(
                Integer.parseInt(txtJumlah.getText()),
                Double.parseDouble(txtTotalHarga.getText()),
                dpTanggal.getValue(),
                cbPelanggan.getValue().getId(),
                cbBuku.getValue().getBukuId()
            );
            try {
                service.addData(p);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Transaksi berhasil disimpan!\nStok buku telah diupdate.");
        });
    }

    @FXML
    private void onEdit() {
        if (selectedId == 0) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih data dari tabel terlebih dahulu!");
            return;
        }
        
        processInput(() -> {
            Penjualan p = new Penjualan(
                selectedId,
                Integer.parseInt(txtJumlah.getText()),
                Double.parseDouble(txtTotalHarga.getText()),
                dpTanggal.getValue(),
                cbPelanggan.getValue().getId(),
                cbBuku.getValue().getBukuId(),
                "", ""
            );
            try {
                service.updateData(p);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data transaksi berhasil diupdate!");
        });
    }

    @FXML
    private void onDelete() {
        if (selectedId == 0) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih data dulu!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Yakin hapus transaksi ini?");
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText("Data transaksi akan dihapus permanen");
        Optional<ButtonType> result = confirm.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                service.deleteData(selectedId);
                clearForm();
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data transaksi terhapus!");
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
        loadComboBoxData();
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
            if (cbPelanggan.getValue() == null) {
                throw new IllegalArgumentException("Pilih pelanggan terlebih dahulu!");
            }
            if (cbBuku.getValue() == null) {
                throw new IllegalArgumentException("Pilih buku terlebih dahulu!");
            }
            
            action.run();
            clearForm();
            loadData();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Jumlah dan Total Harga harus berupa angka!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Gagal", e.getMessage());
        }
    }

    private void loadData() {
        try {
            masterData = FXCollections.observableArrayList(service.getAll());
            tblPenjualan.setItems(masterData);
            updateLabels();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Gagal memuat data: " + e.getMessage());
        }
    }

    private void updateLabels() {
        if (lblTotal != null) {
            int total = tblPenjualan.getItems().size();
            lblTotal.setText("Total Transaksi: " + total);
        }
        
        if (lblPendapatan != null) {
            try {
                double pendapatan = service.getTotalPendapatan();
                lblPendapatan.setText(String.format("Total Pendapatan: Rp %,.0f", pendapatan));
            } catch (Exception e) {
                lblPendapatan.setText("Total Pendapatan: -");
            }
        }
    }

    private void clearForm() {
        cbPelanggan.setValue(null);
        cbBuku.setValue(null);
        txtJumlah.clear();
        txtTotalHarga.clear();
        dpTanggal.setValue(LocalDate.now());
        selectedId = 0;
        tblPenjualan.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}