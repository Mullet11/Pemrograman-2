package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import service.PelangganService;
import service.BukuService;
import service.PenjualanService;

public class MainController {

    @FXML private TabPane mainTabPane;
    @FXML private Tab tabDashboard;
    @FXML private Tab tabPelanggan;
    @FXML private Tab tabBuku;
    @FXML private Tab tabPenjualan;
    
    // Dashboard Labels
    @FXML private Label lblTotalPelanggan;
    @FXML private Label lblTotalBuku;
    @FXML private Label lblTotalTransaksi;
    @FXML private Label lblTotalPendapatan;

    private final PelangganService pelangganService = new PelangganService();
    private final BukuService bukuService = new BukuService();
    private final PenjualanService penjualanService = new PenjualanService();

    @FXML
    public void initialize() {
        loadStatistics();
        loadTabContents();
        
        // Listener untuk refresh statistik saat kembali ke Dashboard
        mainTabPane.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldTab, newTab) -> {
                if (newTab == tabDashboard) {
                    loadStatistics();
                }
            }
        );
    }

    private void loadTabContents() {
        try {
            // Load Pelanggan Tab Content
            FXMLLoader pelangganLoader = new FXMLLoader(getClass().getResource("/views/PelangganView.fxml"));
            AnchorPane pelangganContent = pelangganLoader.load();
            tabPelanggan.setContent(pelangganContent);

            // Load Buku Tab Content
            FXMLLoader bukuLoader = new FXMLLoader(getClass().getResource("/views/BukuView.fxml"));
            AnchorPane bukuContent = bukuLoader.load();
            tabBuku.setContent(bukuContent);

            // Load Penjualan Tab Content
            FXMLLoader penjualanLoader = new FXMLLoader(getClass().getResource("/views/PenjualanView.fxml"));
            AnchorPane penjualanContent = penjualanLoader.load();
            tabPenjualan.setContent(penjualanContent);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat tab: " + e.getMessage());
        }
    }

    private void loadStatistics() {
        try {
            // Load Total Pelanggan
            int totalPelanggan = pelangganService.getTotalPelanggan();
            lblTotalPelanggan.setText(String.valueOf(totalPelanggan));

            // Load Total Buku
            int totalBuku = bukuService.getTotalBuku();
            lblTotalBuku.setText(String.valueOf(totalBuku));

            // Load Total Transaksi
            int totalTransaksi = penjualanService.getTotalTransaksi();
            lblTotalTransaksi.setText(String.valueOf(totalTransaksi));

            // Load Total Pendapatan
            double totalPendapatan = penjualanService.getTotalPendapatan();
            lblTotalPendapatan.setText(String.format("Rp %,.0f", totalPendapatan));

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat statistik: " + e.getMessage());
        }
    }

    @FXML
    private void onRefreshStatistics() {
        loadStatistics();
        showAlert(Alert.AlertType.INFORMATION, "Refresh", "Statistik berhasil diperbarui!");
    }

    @FXML
    private void onGoToPelanggan() {
        mainTabPane.getSelectionModel().select(tabPelanggan);
    }

    @FXML
    private void onGoToBuku() {
        mainTabPane.getSelectionModel().select(tabBuku);
    }

    @FXML
    private void onGoToPenjualan() {
        mainTabPane.getSelectionModel().select(tabPenjualan);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}