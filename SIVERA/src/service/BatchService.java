package service;

import dao.BatchDAO;
import model.Batch;
import model.Plant;
import model.Rack;

import java.time.LocalDate;
import java.util.List;

public class BatchService {

    private final BatchDAO batchDAO = new BatchDAO();

    public void createBatch(Plant plant, Rack rack) {
        Batch batch = new Batch();
        batch.setPlant(plant);
        batch.setRack(rack);
        batch.setPlantingDate(LocalDate.now());
        batch.setStatus("AKTIF");

        batchDAO.insert(batch);
    }

    public List<Batch> getAllBatch() {
        return batchDAO.findAll();
    }

    public Batch getBatchById(int id) {
        return batchDAO.findById(id);
    }

    public void deleteBatch(int batchId) {
        batchDAO.delete(batchId);
    }

    public boolean isReadyToHarvest(Batch batch) {
        return batch.isReadyToHarvest();
    }

    public void harvestBatch(Batch batch) {
        if (batch.isReadyToHarvest()) {
            batch.harvest();
            batchDAO.updateStatus(batch.getBatchId(), batch.getStatus());
        }
    }
}
