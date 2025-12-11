package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.DetailTransaksi;
import model.Menu;
import model.Pelanggan;
import model.Transaksi;
import service.DetailTransaksiService;
import service.MenuService;
import service.PelangganService;
import service.TransaksiService;
import util.AlertHelper;
import util.Session;
import application.Main;

import java.time.LocalDateTime;
import java.util.*;

public class TransaksiController {

    @FXML private ComboBox<Pelanggan> cbPelanggan;
    @FXML private ComboBox<Menu> cbMenu;
    @FXML private ComboBox<Integer> cbQty;

    @FXML private TableView<CartRow> table;
    @FXML private TableColumn<CartRow, String> colMenu;
    @FXML private TableColumn<CartRow, Integer> colQty;
    @FXML private TableColumn<CartRow, Double> colSubtotal;

    @FXML private Label lblGrandTotal;
    @FXML private Button btnSimpan;

    private MenuService menuService = new MenuService();
    private PelangganService pelangganService = new PelangganService();
    private TransaksiService transaksiService = new TransaksiService();
    private DetailTransaksiService detailService = new DetailTransaksiService();

    private ObservableList<CartRow> cart = FXCollections.observableArrayList();
    private Map<Integer, DetailTransaksi> cartDetails = new LinkedHashMap<>();

    @FXML
    private void initialize() {
        try {
            // SET MENU LIST
            List<Menu> menus = menuService.getAll();
            cbMenu.setItems(FXCollections.observableArrayList(menus));
            cbMenu.setCellFactory(lv -> new ListCell<Menu>() {
                @Override
                protected void updateItem(Menu item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null 
                        : item.getNama() + " (Rp " + item.getHarga() + ")");
                }
            });

            // SET PELANGGAN LIST
            List<Pelanggan> pls = pelangganService.getAll();
            cbPelanggan.setItems(FXCollections.observableArrayList(pls));

            // SET QTY
            cbQty.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10));
            cbQty.getSelectionModel().select(0);

            // TABLE BINDING
            colMenu.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMenu()));
            colQty.setCellValueFactory(
                data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getQty()));
            colSubtotal.setCellValueFactory(
                data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getSubtotal()));

            table.setItems(cart);

            updateTotal();

        } catch (Exception e) {
            AlertHelper.error("Init Error", e.getMessage());
        }
    }

    @FXML
    private void tambahItem() {
        try {
            Menu m = cbMenu.getSelectionModel().getSelectedItem();
            Integer qty = cbQty.getSelectionModel().getSelectedItem();

            if (m == null) {
                AlertHelper.warn("Validasi", "Pilih menu terlebih dahulu.");
                return;
            }
            if (qty == null || qty <= 0) {
                AlertHelper.warn("Validasi", "Pilih quantity yang valid.");
                return;
            }
            if (m.getStok() < qty) {
                AlertHelper.warn("Stok Kurang", "Stok tersisa: " + m.getStok());
                return;
            }

            // Jika sudah ada item yang sama maka akan tambah qty, update subtotal
            if (cartDetails.containsKey(m.getId())) {
                DetailTransaksi d = cartDetails.get(m.getId());
                int newQty = d.getQty() + qty;

                if (newQty > m.getStok()) {
                    AlertHelper.warn("Stok", "Stok tidak cukup untuk menambah jumlah.");
                    return;
                }

                d.setQty(newQty);
                d.setSubTotal(newQty * m.getHarga());

                // update row di table
                cart.removeIf(row -> row.getMenuId() == m.getId());
                cart.add(new CartRow(m.getId(), m.getNama(), d.getQty(), d.getSubTotal()));

            } else {
                DetailTransaksi d = new DetailTransaksi();
                d.setMenuId(m.getId());
                d.setQty(qty);
                d.setSubTotal(qty * m.getHarga());

                cartDetails.put(m.getId(), d);

                cart.add(new CartRow(m.getId(), m.getNama(), qty, d.getSubTotal()));
            }

            updateTotal();

        } catch (Exception e) {
            AlertHelper.error("Tambah Item Error", e.getMessage());
        }
    }

    @FXML
    private void simpanTransaksi() {
        try {
            Pelanggan p = cbPelanggan.getSelectionModel().getSelectedItem();
            if (p == null) {
                AlertHelper.warn("Validasi", "Pilih pelanggan.");
                return;
            }

            if (cartDetails.isEmpty()) {
                AlertHelper.warn("Validasi", "Cart masih kosong.");
                return;
            }

            double grandTotal = cart.stream().mapToDouble(CartRow::getSubtotal).sum();

            Transaksi trx = new Transaksi();
            trx.setPelangganId(p.getId());
            trx.setId(Session.getCurrentAkun().getIdAkun());
            trx.setTanggal(java.sql.Timestamp.valueOf(LocalDateTime.now()));
            trx.setTotal(grandTotal);

            // SIMPAN TRANSAKSI → dapatkan ID
            int trxId = transaksiService.save(trx);

            // SIMPAN DETAIL + UPDATE STOK DARI DAO
            for (DetailTransaksi d : cartDetails.values()) {
                d.setTransaksiId(trxId);
                detailService.save(d);
                menuService.kurangiStok(d.getMenuId(), d.getQty());
            }

            AlertHelper.info("Sukses", "Transaksi berhasil disimpan.");

            // RESET UI
            cart.clear();
            cartDetails.clear();
            updateTotal();

        } catch (Exception e) {
            AlertHelper.error("Simpan Error", e.getMessage());
        }
    }

    private void updateTotal() {
        double total = cart.stream().mapToDouble(CartRow::getSubtotal).sum();
        lblGrandTotal.setText("Rp " + total);
        btnSimpan.setDisable(cart.isEmpty());
    }

    @FXML
    private void backToDashboard() throws Exception {
        Main.changeScene("DashboardView.fxml");
    }

    // ROW CLASS UNTUK TABLEVIEW
    public static class CartRow {
        private final int menuId;
        private final String menu;
               private final int qty;
        private final double subtotal;

        public CartRow(int menuId, String menu, int qty, double subtotal) {
            this.menuId = menuId;
            this.menu = menu;
            this.qty = qty;
            this.subtotal = subtotal;
        }

        public int getMenuId() { return menuId; }
        public String getMenu() { return menu; }
        public int getQty() { return qty; }
        public double getSubtotal() { return subtotal; }
    }
    
    private boolean validasiItem() {
        if (cbPelanggan.getValue() == null) {
            AlertHelper.warn("Validasi", "Pilih pelanggan terlebih dahulu.");
            return false;
        }

        if (cbMenu.getValue() == null) {
            AlertHelper.warn("Validasi", "Pilih menu terlebih dahulu.");
            return false;
        }

        if (cbQty.getValue() == null) {
            AlertHelper.warn("Validasi", "Pilih jumlah (qty).");
            return false;
        }

        Menu m = cbMenu.getValue();
        int qty = cbQty.getValue();

        if (qty > m.getStok()) {
            AlertHelper.warn("Stok Tidak Cukup",
                    "Stok menu tidak mencukupi. Stok tersedia: " + m.getStok());
            return false;
        }

        return true;
    }
}
