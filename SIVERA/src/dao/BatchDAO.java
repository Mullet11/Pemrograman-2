package dao;

import config.DatabaseConfig;
import model.Batch;
import model.Plant;
import model.Rack;
import model.LeafPlant;
import model.FruitPlant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BatchDAO {

    private static final String INSERT = "INSERT INTO batches (plant_id, rack_id, planting_date, status) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT " + "b.batch_id, b.planting_date, b.status, " + "p.plant_id, p.plant_name, p.plant_type, p.min_harvest_age, " + "r.rack_id, r.rack_name, r.location, r.capacity " + "FROM batches b " + "JOIN plants p ON b.plant_id = p.plant_id " + "JOIN racks r ON b.rack_id = r.rack_id";
    private static final String SELECT_BY_ID = "SELECT " + "b.batch_id, b.planting_date, b.status, " + "p.plant_id, p.plant_name, p.plant_type, p.min_harvest_age, " + "r.rack_id, r.rack_name, r.location, r.capacity " + "FROM batches b " + "JOIN plants p ON b.plant_id = p.plant_id " + "JOIN racks r ON b.rack_id = r.rack_id " + "WHERE b.batch_id = ?";
    private static final String UPDATE_STATUS = "UPDATE batches SET status = ? WHERE batch_id = ?";
    private static final String DELETE = "DELETE FROM batches WHERE batch_id = ?";

    public void insert(Batch batch) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {

            stmt.setInt(1, batch.getPlant().getPlantId());
            stmt.setInt(2, batch.getRack().getRackId());
            stmt.setDate(3, java.sql.Date.valueOf(batch.getPlantingDate()));
            stmt.setString(4, batch.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Batch> findAll() {
        List<Batch> batches = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                //TENTUKAN SUBCLASS PLANT
                String plantType = rs.getString("plant_type");
                Plant plant;

                if ("LEAF".equalsIgnoreCase(plantType)) {
                    plant = new LeafPlant();
                } else if ("FRUIT".equalsIgnoreCase(plantType)) {
                    plant = new FruitPlant();
                } else {
                    throw new IllegalStateException("Unknown plant type: " + plantType);
                }

                plant.setPlantId(rs.getInt("plant_id"));
                plant.setPlantName(rs.getString("plant_name"));
                plant.setMinHarvestAge(rs.getInt("min_harvest_age"));

                //RACK
                Rack rack = new Rack();
                rack.setRackId(rs.getInt("rack_id"));
                rack.setRackName(rs.getString("rack_name"));
                rack.setLocation(rs.getString("location"));
                rack.setCapacity(rs.getInt("capacity"));

                //BATCH
                Batch batch = new Batch();
                batch.setBatchId(rs.getInt("batch_id"));
                batch.setPlant(plant);
                batch.setRack(rack);
                batch.setPlantingDate(rs.getDate("planting_date").toLocalDate());
                batch.setStatus(rs.getString("status"));

                batches.add(batch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return batches;
    }

    public Batch findById(int batchId) {
        Batch batch = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setInt(1, batchId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    String plantType = rs.getString("plant_type");
                    Plant plant;

                    if ("LEAF".equalsIgnoreCase(plantType)) {
                        plant = new LeafPlant();
                    } else if ("FRUIT".equalsIgnoreCase(plantType)) {
                        plant = new FruitPlant();
                    } else {
                        throw new IllegalStateException("Unknown plant type: " + plantType);
                    }

                    plant.setPlantId(rs.getInt("plant_id"));
                    plant.setPlantName(rs.getString("plant_name"));
                    plant.setMinHarvestAge(rs.getInt("min_harvest_age"));

                    Rack rack = new Rack();
                    rack.setRackId(rs.getInt("rack_id"));
                    rack.setRackName(rs.getString("rack_name"));
                    rack.setLocation(rs.getString("location"));
                    rack.setCapacity(rs.getInt("capacity"));

                    batch = new Batch();
                    batch.setBatchId(rs.getInt("batch_id"));
                    batch.setPlant(plant);
                    batch.setRack(rack);
                    batch.setPlantingDate(rs.getDate("planting_date").toLocalDate());
                    batch.setStatus(rs.getString("status"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return batch;
    }

    public void updateStatus(int batchId, String status) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_STATUS)) {

            stmt.setString(1, status);
            stmt.setInt(2, batchId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int batchId) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setInt(1, batchId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
