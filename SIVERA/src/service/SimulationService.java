package service;

import dao.SensorLogDAO;
import model.Batch;
import model.SensorLog;

import java.util.Random;

public class SimulationService {

    private final SensorLogDAO sensorLogDAO = new SensorLogDAO();
    private final BatchService batchService = new BatchService();
    private final Random random = new Random();

     //Simulasi pembacaan sensor untuk satu batch
    public SensorLog simulateSensor(Batch batch) {

        if (batch == null) {
            throw new IllegalArgumentException("Batch tidak boleh null");
        }

        double temperature = 20 + random.nextDouble() * 10; 
        double ph = 5.5 + random.nextDouble() * 2;          

        SensorLog log = new SensorLog(temperature, ph);

        // Simpan ke database 
        sensorLogDAO.insert(batch.getBatchId(), log);

        // Set sensor terakhir ke batch (in-memory)
        batch.setSensorLog(log);

        return log;
    }
    
    public SensorLog simulateSensor(int batchId) {
        Batch batch = batchService.getBatchById(batchId);
        return simulateSensor(batch);
    }


     //Cek apakah batch siap panen 

    public boolean checkHarvestReady(Batch batch) {
        return batch != null && batch.isReadyToHarvest();
    }
    
    public boolean checkHarvestReady(int batchId) {
        Batch batch = batchService.getBatchById(batchId);
        return checkHarvestReady(batch);
    }


     //Proses panen batch
    public void harvest(Batch batch) {
        batchService.harvestBatch(batch);
    }
    
    public void harvest(int batchId) {
        Batch batch = batchService.getBatchById(batchId);
        harvest(batch);
    }

}
