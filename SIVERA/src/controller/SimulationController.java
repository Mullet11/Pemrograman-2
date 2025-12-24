package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Batch;
import model.SensorLog;
import service.BatchService;
import service.SimulationService;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {

    @FXML private ComboBox<Batch> batchBox;
    @FXML private TextArea outputArea;

    private final SimulationService simulationService = new SimulationService();
    private final BatchService batchService = new BatchService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        refreshBatchBox();

        batchBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            outputArea.clear();
        });

        batchBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Batch batch, boolean empty) {
                super.updateItem(batch, empty);
                if (empty || batch == null) {
                    setText(null);
                } else {
                    setText(
                            "Batch #" + batch.getBatchId()
                            + " | " + batch.getPlant().getPlantName()
                            + " | Rak: " + batch.getRack().getRackName()
                            + " | Status: " + batch.getStatus()
                    );
                }
            }
        });
        batchBox.setButtonCell(batchBox.getCellFactory().call(null));
    }

    @FXML
    private void simulate() {

        outputArea.clear();

        Batch batch = batchBox.getValue();
        if (batch == null) {
            showAlert("Pilih batch terlebih dahulu");
            return;
        }

        if ("DIPANEN".equals(batch.getStatus())) {
            showAlert("Batch sudah dipanen dan tidak bisa disimulasikan");
            return;
        }

        if (!"AKTIF".equals(batch.getStatus())) {
            showAlert("Simulasi hanya dapat dilakukan pada batch AKTIF");
            return;
        }

        SensorLog log = simulationService.simulateSensor(batch);

        outputArea.appendText( "📡 Simulasi Sensor\n" + "Suhu : " + String.format("%.2f", log.getTemperature()) + " °C\n" +"pH   : " + String.format("%.2f", log.getPh()) + "\n");

        if (simulationService.checkHarvestReady(batch)) {
            batch.setStatus("SIAP_PANEN");
            batchService.harvestBatch(batch);

            outputArea.appendText("✅ Batch SIAP PANEN\n");
            refreshBatchBox();
        } else {
            outputArea.appendText("⏳ Batch belum siap panen\n");
        }
    }

    @FXML
    private void checkHarvest() {

        outputArea.clear();

        Batch batch = batchBox.getValue();
        if (batch == null) {
            showAlert("Pilih batch terlebih dahulu");
            return;
        }

        if ("DIPANEN".equals(batch.getStatus())) {
            outputArea.appendText("🌾 Batch sudah dipanen\n");
            return;
        }

        if ("SIAP_PANEN".equals(batch.getStatus())) {
            outputArea.appendText("✅ Batch siap dipanen\n");
        } else {
            outputArea.appendText("⏳ Batch belum siap dipanen\n");
        }
    }

    @FXML
    private void harvest() {

        outputArea.clear();

        Batch batch = batchBox.getValue();
        if (batch == null) {
            showAlert("Pilih batch terlebih dahulu");
            return;
        }

        if (!"SIAP_PANEN".equals(batch.getStatus())) {
            showAlert("Batch hanya dapat dipanen jika berstatus SIAP PANEN");
            return;
        }

        simulationService.harvest(batch);
        outputArea.appendText("🌾 Batch berhasil DIPANEN\n");

        refreshBatchBox();
    }

    @FXML
    private void refresh() {
        refreshBatchBox();
        outputArea.clear();
    }

    private void refreshBatchBox() {
        batchBox.getItems().setAll(batchService.getAllBatch());
        batchBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
