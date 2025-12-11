package controller;

import java.sql.Timestamp;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Transaksi;
import service.MenuService;
import service.PelangganService;
import service.TransaksiService;
import util.AlertHelper;

public class DashboardController {

    // Label card dashboard
    @FXML private Label lblMenu;
    @FXML private Label lblPelanggan;
    @FXML private Label lblTransaksi;
    @FXML private Label lblPendapatan;

    // Table ringkasan transaksi terakhir
    @FXML private TableView<Transaksi> tblTransaksi;
    @FXML private TableColumn<Transaksi, Integer> colId;
    @FXML private TableColumn<Transaksi, String> colPelanggan;
    @FXML private TableColumn<Transaksi, Double> colTotal;
    @FXML private TableColumn<Transaksi, Timestamp> colTanggal;

    private MenuService menuService = new MenuService();
    private PelangganService pelangganService = new PelangganService();
    private TransaksiService transaksiService = new TransaksiService();

    @FXML
    private void initialize() {
        try {
            // CARD SUMMARY
            lblMenu.setText(String.valueOf(menuService.count()));
            lblPelanggan.setText(String.valueOf(pelangganService.count()));
            lblTransaksi.setText(String.valueOf(transaksiService.count()));
            lblPendapatan.setText("Rp " + transaksiService.getTotalPendapatan());

            // TABLE SETUP
            colId.setCellValueFactory(v -> new javafx.beans.property.SimpleIntegerProperty(
                    v.getValue().getId()).asObject());

            colTotal.setCellValueFactory(v ->
                    new javafx.beans.property.SimpleDoubleProperty(
                            v.getValue().getTotal()).asObject()
            );

            colTanggal.setCellValueFactory(v ->
                    new javafx.beans.property.ObjectPropertyBase<Timestamp>() {
                        @Override
                        public Timestamp get() {
                            return v.getValue().getTanggal();
                        }

                        @Override
                        public Object getBean() { return v; }

                        @Override
                        public String getName() { return "tanggal"; }
                    }
            );

            // Pelanggan name via lookup
            colPelanggan.setCellValueFactory(v -> {
                try {
                    String nama = pelangganService.findNameById(v.getValue().getPelangganId());
                    return new SimpleStringProperty(nama);
                } catch (Exception e) {
                    return new SimpleStringProperty("-");
                }
            });

            // Load transaksi ke tabel
            tblTransaksi.setItems(FXCollections.observableArrayList(transaksiService.getAll()));

        } catch (Exception e) {
            AlertHelper.error("Dashboard Error", e.getMessage());
        }
    }

    // NAVIGASI
    @FXML private void goMenu() throws Exception {
        Main.changeScene("MenuView.fxml");
    }

    @FXML private void goTransaksi() throws Exception {
        Main.changeScene("TransaksiView.fxml");
    }

    @FXML private void goPelanggan() throws Exception {
        Main.changeScene("PelangganView.fxml");
    }

    @FXML private void goAkun() throws Exception {
        Main.changeScene("AkunView.fxml");
    }
}
