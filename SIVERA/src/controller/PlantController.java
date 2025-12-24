package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import model.Plant;
import model.LeafPlant;
import model.FruitPlant;
import service.PlantService;

import java.net.URL;
import java.util.ResourceBundle;

public class PlantController implements Initializable {

    @FXML private TableView<Plant> plantTable;

    @FXML private TableColumn<Plant, String> nameCol;
    @FXML private TableColumn<Plant, String> typeCol;
    @FXML private TableColumn<Plant, Integer> ageCol;

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> typeBox;
    
    private static final int MIN_AGE = 1;
    private static final int MAX_AGE = 365;

    private final PlantService plantService = new PlantService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ComboBox
        typeBox.getItems().addAll("LEAF", "FRUIT");

        // Table binding
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("plantName")
        );

        typeCol.setCellValueFactory(
                cell -> new ReadOnlyStringWrapper(
                        cell.getValue().getPlantType()
                )
        );

        ageCol.setCellValueFactory(
                new PropertyValueFactory<>("minHarvestAge")
        );

        // Selection listener (isi form saat klik tabel)
        plantTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, selected) -> fillForm(selected)
        );

        refreshTable();
    }

    @FXML
    private void handleAdd() {
        if (!validateInput(true)) return;

        int age = Integer.parseInt(ageField.getText());
        Plant plant;

        if ("LEAF".equals(typeBox.getValue())) {
            plant = new LeafPlant();
        } else {
            plant = new FruitPlant();
        }

        plant.setPlantName(nameField.getText());
        plant.setMinHarvestAge(age);

        plantService.addPlant(plant);
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleUpdate() {
        Plant selected = plantTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih data yang ingin diubah");
            return;
        }

        if (!validateInput(false)) return;

        selected.setPlantName(nameField.getText());
        selected.setMinHarvestAge(
                Integer.parseInt(ageField.getText())
        );

        plantService.updatePlant(selected);
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleDelete() {
        Plant selected = plantTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih data yang ingin dihapus");
            return;
        }

        plantService.deletePlant(selected.getPlantId());
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleRefresh() {
        refreshTable();
        clearForm();
    }

    private void refreshTable() {
        plantTable.getItems().setAll(
                plantService.getAllPlants()
        );
    }

    private void fillForm(Plant plant) {
        if (plant == null) return;

        nameField.setText(plant.getPlantName());
        ageField.setText(
                String.valueOf(plant.getMinHarvestAge())
        );
        typeBox.setValue(plant.getPlantType());
        typeBox.setDisable(true);
    }

    private void clearForm() {
        nameField.clear();
        ageField.clear();
        typeBox.getSelectionModel().clearSelection();
        typeBox.setDisable(false);
        plantTable.getSelectionModel().clearSelection();
    }

    private boolean validateInput(boolean isAdd) {

        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();

        //VALIDASI KOSONG
        if (name.isEmpty() || ageText.isEmpty() || typeBox.getValue() == null) {
            showAlert("Semua field wajib diisi");
            return false;
        }

     //VALIDASI NAMA TANAMAN
        if (name.length() < 3 || name.length() > 50) {
            showAlert("Nama tanaman harus 3–50 karakter");
            return false;
        }

        if (!name.matches("[a-zA-Z\\s]+")) {
            showAlert("Nama tanaman hanya boleh huruf");
            return false;
        }

        //VALIDASI UMUR PANEN
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            showAlert("Umur panen harus berupa angka");
            return false;
        }

        if (age < MIN_AGE || age > MAX_AGE) {
            showAlert("Umur panen harus antara " + MIN_AGE + " sampai " + MAX_AGE + " hari");
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
