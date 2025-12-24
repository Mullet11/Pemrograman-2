	package controller;
	
	import javafx.beans.property.ReadOnlyStringWrapper;
	import javafx.fxml.FXML;
	import javafx.fxml.Initializable;
	import javafx.scene.control.*;
	import model.Batch;
	import model.Plant;
	import model.Rack;
	import service.BatchService;
	import service.PlantService;
	import service.RackService;
	
	import java.net.URL;
	import java.util.ResourceBundle;
	
	public class BatchController implements Initializable {
	
	    @FXML private TableView<Batch> batchTable;
	
	    @FXML private TableColumn<Batch, String> plantCol;
	    @FXML private TableColumn<Batch, String> rackCol;
	    @FXML private TableColumn<Batch, String> statusCol;
	
	    @FXML private ComboBox<Plant> plantBox;
	    @FXML private ComboBox<Rack> rackBox;
	
	    private final BatchService batchService = new BatchService();
	    private final PlantService plantService = new PlantService();
	    private final RackService rackService = new RackService();
	
	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	
	        plantCol.setCellValueFactory(
	                cell -> new ReadOnlyStringWrapper(
	                        cell.getValue().getPlant().getPlantName()
	                )
	        );
	
	        rackCol.setCellValueFactory(
	                cell -> new ReadOnlyStringWrapper(
	                        cell.getValue().getRack().getRackName()
	                )
	        );
	
	        statusCol.setCellValueFactory(
	                cell -> new ReadOnlyStringWrapper(
	                        cell.getValue().getStatus()
	                )
	        );
	
	        plantBox.getItems().setAll(plantService.getAllPlants());
	        rackBox.getItems().setAll(rackService.getAllRacks());
	
	        plantBox.setCellFactory(cb -> new ListCell<>() {
	            @Override
	            protected void updateItem(Plant item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty || item == null ? null : item.getPlantName());
	            }
	        });
	        plantBox.setButtonCell(plantBox.getCellFactory().call(null));
	
	        rackBox.setCellFactory(cb -> new ListCell<>() {
	            @Override
	            protected void updateItem(Rack item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty || item == null ? null : item.getRackName());
	            }
	        });
	        rackBox.setButtonCell(rackBox.getCellFactory().call(null));
	
	        refreshTable();
	    }
	
	    /* ===================== ACTION ===================== */
	
	    @FXML
	    private void handleAdd() {
	
	        if (!validateInput()) return;
	
	        Plant plant = plantBox.getValue();
	        Rack rack = rackBox.getValue();
	
	        batchService.createBatch(plant, rack);
	        refreshTable();
	        clearForm();
	    }
	
	    @FXML
	    private void handleRefresh() {
	        refreshTable();
	        clearForm();
	    }
	    
	    @FXML
	    private void handleDelete() {

	        Batch selected = batchTable.getSelectionModel().getSelectedItem();

	        if (selected == null) {
	            showAlert("Pilih batch yang ingin dihapus");
	            return;
	        }

	        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	        confirm.setTitle("Konfirmasi Hapus");
	        confirm.setHeaderText(null);
	        confirm.setContentText("Yakin ingin menghapus batch ini?");

	        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
	            return;
	        }

	        batchService.deleteBatch(selected.getBatchId());
	        refreshTable();
	        clearForm();
	    }
	
	    private void refreshTable() {
	        batchTable.getItems().setAll(
	                batchService.getAllBatch()
	        );
	    }
	
	    private void clearForm() {
	        plantBox.getSelectionModel().clearSelection();
	        rackBox.getSelectionModel().clearSelection();
	    }
	
	    private boolean validateInput() {
	
	        Plant plant = plantBox.getValue();
	        Rack rack = rackBox.getValue();
	
	        if (plant == null || rack == null) {
	            showAlert("Plant dan rack wajib dipilih");
	            return false;
	        }
	
	        // === VALIDASI LOGIKA (CEK DATA YANG SUDAH ADA) ===
	        for (Batch batch : batchTable.getItems()) {
	
	            // Rack sudah digunakan batch lain
	            if (batch.getRack().getRackId() == rack.getRackId()) {
	                showAlert("Rack sudah digunakan oleh batch lain");
	                return false;
	            }
	
	            // Kombinasi plant & rack sama
	            if (batch.getPlant().getPlantId() == plant.getPlantId()
	                    && batch.getRack().getRackId() == rack.getRackId()) {
	                showAlert("Batch dengan plant dan rack ini sudah ada");
	                return false;
	            }
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
