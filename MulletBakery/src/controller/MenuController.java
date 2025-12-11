package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Menu;
import service.MenuService;
import util.AlertHelper;
import application.Main;

public class MenuController {

    @FXML private TextField txtNamaMenu;
    @FXML private TextField txtHarga;
    @FXML private TextField txtStok;
    @FXML private TextArea txtDeskripsi;

    @FXML private TableView<Menu> tableMenu;
    @FXML private TableColumn<Menu, String> colNama;
    @FXML private TableColumn<Menu, Double> colHarga;
    @FXML private TableColumn<Menu, Integer> colStok;

    private MenuService menuService = new MenuService();
    private ObservableList<Menu> list = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        try {
            colNama.setCellValueFactory(v -> new javafx.beans.property.SimpleStringProperty(v.getValue().getNama()));
            colHarga.setCellValueFactory(v -> new javafx.beans.property.SimpleDoubleProperty(v.getValue().getHarga()).asObject());
            colStok.setCellValueFactory(v -> new javafx.beans.property.SimpleIntegerProperty(v.getValue().getStok()).asObject());

            list.setAll(menuService.getAll());
            tableMenu.setItems(list);

            // AUTO-FILL ketika baris dipilih
            tableMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, m) -> {
                if (m != null) isiForm(m);
            });

        } catch (Exception e) {
            AlertHelper.error("Init Error", e.getMessage());
        }
    }

    private void isiForm(Menu m) {
        txtNamaMenu.setText(m.getNama());
        txtHarga.setText(String.valueOf(m.getHarga()));
        txtStok.setText(String.valueOf(m.getStok()));
        txtDeskripsi.setText(m.getDeskripsi());
    }

    private boolean validasi() {
        if (txtNamaMenu.getText().isEmpty()) {
            AlertHelper.warn("Validasi", "Nama menu wajib diisi.");
            return false;
        }
        if (!txtHarga.getText().matches("\\d+")) {
            AlertHelper.warn("Validasi", "Harga harus angka.");
            return false;
        }
        if (!txtStok.getText().matches("\\d+")) {
            AlertHelper.warn("Validasi", "Stok harus angka.");
            return false;
        }
        return true;
    }

    @FXML
    private void simpan() {
        try {
            if (!validasi()) return;

            Menu m = new Menu();
            m.setNama(txtNamaMenu.getText());
            m.setHarga(Double.parseDouble(txtHarga.getText()));
            m.setStok(Integer.parseInt(txtStok.getText()));
            m.setDeskripsi(txtDeskripsi.getText());

            menuService.save(m);
            list.setAll(menuService.getAll());

            AlertHelper.info("Sukses", "Menu berhasil ditambahkan.");
            clear();

        } catch (Exception e) {
            AlertHelper.error("Simpan Error", e.getMessage());
        }
    }

    @FXML
    private void edit() {
        try {
            Menu m = tableMenu.getSelectionModel().getSelectedItem();
            if (m == null) {
                AlertHelper.warn("Pilih Data", "Pilih menu dahulu.");
                return;
            }

            if (!validasi()) return;

            m.setNama(txtNamaMenu.getText());
            m.setHarga(Double.parseDouble(txtHarga.getText()));
            m.setStok(Integer.parseInt(txtStok.getText()));
            m.setDeskripsi(txtDeskripsi.getText());

            menuService.update(m);
            list.setAll(menuService.getAll());

            AlertHelper.info("Sukses", "Menu berhasil diperbarui.");
            clear();

        } catch (Exception e) {
            AlertHelper.error("Edit Error", e.getMessage());
        }
    }

    @FXML
    private void hapus() {
        try {
            Menu m = tableMenu.getSelectionModel().getSelectedItem();
            if (m == null) {
                AlertHelper.warn("Pilih Data", "Pilih menu yang ingin dihapus.");
                return;
            }

            if (!AlertHelper.confirm("Konfirmasi", "Hapus menu ini?")) return;

            menuService.delete(m.getId());
            list.setAll(menuService.getAll());

        } catch (Exception e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key") || msg.contains("constraint")) {
                AlertHelper.error("Hapus Error",
                    "Menu tidak dapat dihapus karena sudah digunakan dalam transaksi.");
                return;
            }
            AlertHelper.error("Hapus Error", e.getMessage());
        }
    }

    private void clear() {
        txtNamaMenu.clear();
        txtHarga.clear();
        txtStok.clear();
        txtDeskripsi.clear();
        tableMenu.getSelectionModel().clearSelection();
    }

    @FXML private void back() throws Exception { Main.changeScene("DashboardView.fxml"); }
}
