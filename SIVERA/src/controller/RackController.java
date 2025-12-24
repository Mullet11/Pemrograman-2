package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Rack;
import service.RackService;

import java.net.URL;
import java.util.ResourceBundle;

public class RackController implements Initializable {

    @FXML private TableView<Rack> rackTable;

    @FXML private TableColumn<Rack, String> nameCol;
    @FXML private TableColumn<Rack, String> locationCol;
    @FXML private TableColumn<Rack, Integer> capacityCol;

    @FXML private TextField nameField;
    @FXML private TextField locationField;
    @FXML private TextField capacityField;

    private final RackService rackService = new RackService();

    private static final int MIN_CAPACITY = 1;
    private static final int MAX_CAPACITY = 1000;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("rackName")
        );

        locationCol.setCellValueFactory( new PropertyValueFactory<>("location"));
        capacityCol.setCellValueFactory( new PropertyValueFactory<>("capacity"));
        rackTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> fillForm(selected));

        refreshTable();
    }

    @FXML
    private void handleAdd() {
        if (!validateInput()) return;

        rackService.addRack(
                nameField.getText().trim(),
                locationField.getText().trim(),
                Integer.parseInt(capacityField.getText().trim())
        );

        refreshTable();
        clearForm();
    }

    @FXML
    private void handleDelete() {
        Rack selected = rackTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih rak yang ingin dihapus");
            return;
        }

        rackService.deleteRack(selected.getRackId());
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleRefresh() {
        refreshTable();
        clearForm();
    }

    private void refreshTable() {
        rackTable.getItems().setAll(
                rackService.getAllRacks()
        );
    }

    private void fillForm(Rack rack) {
        if (rack == null) return;

        nameField.setText(rack.getRackName());
        locationField.setText(rack.getLocation());
        capacityField.setText(
                String.valueOf(rack.getCapacity())
        );
    }

    private void clearForm() {
        nameField.clear();
        locationField.clear();
        capacityField.clear();
        rackTable.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {

        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String capacityText = capacityField.getText().trim();

        // Wajib isi
        if (name.isEmpty() || location.isEmpty() || capacityText.isEmpty()) {
            showAlert("Semua field wajib diisi");
            return false;
        }

        // Validasi nama rak
        if (name.length() < 2 || name.length() > 30) {
            showAlert("Nama rak harus 2–30 karakter");
            return false;
        }

        if (!name.matches("[a-zA-Z0-9\\s]+")) {
            showAlert("Nama rak hanya boleh huruf, angka, dan spasi");
            return false;
        }

        // Validasi lokasi
        if (location.length() < 3 || location.length() > 50) {
            showAlert("Lokasi harus 3–50 karakter");
            return false;
        }

        if (!location.matches("[a-zA-Z\\s]+")) {
            showAlert("Lokasi hanya boleh huruf dan spasi");
            return false;
        }

        // Validasi kapasitas
        int capacity;
        try {
            capacity = Integer.parseInt(capacityText);
        } catch (NumberFormatException e) {
            showAlert("Kapasitas harus berupa angka");
            return false;
        }

        if (capacity < MIN_CAPACITY || capacity > MAX_CAPACITY) {
            showAlert("Kapasitas harus antara " + MIN_CAPACITY + " – " + MAX_CAPACITY);
            return false;
        }

        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
