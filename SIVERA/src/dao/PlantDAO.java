package dao;

import config.DatabaseConfig;
import model.Plant;
import model.LeafPlant;
import model.FruitPlant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlantDAO {
	private static final String SELECT_ALL = "SELECT * FROM plants";
	private static final String SELECT_BY_ID = "SELECT * FROM plants WHERE plant_id = ?";
	private static final String INSERT = "INSERT INTO plants (plant_name, plant_type, min_harvest_age, min_temperature, max_temperature, min_ph, max_ph) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE plants SET plant_name=?, min_harvest_age=?, min_temperature=?, max_temperature=?, min_ph=?, max_ph=? " + "WHERE plant_id=?";
	private static final String DELETE = "DELETE FROM plants WHERE plant_id=?";

	
	public List<Plant> findAll(){
		List<Plant> plants = new ArrayList<>();
		
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
			ResultSet rs = stmt.executeQuery()){
			
			while(rs.next()) {
				Plant plant = mapResultSetToPlant(rs);
				plants.add(plant);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return plants;
	}
	
	public Plant findById(int plantId) {
		Plant plant = null;
		
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)){
			
			stmt.setInt(1, plantId);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				plant = mapResultSetToPlant(rs);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return plant;
	}
	
	public void insert(Plant plant) {
	    try (Connection conn = DatabaseConfig.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(INSERT)) {

	        stmt.setString(1, plant.getPlantName());
	        stmt.setString(2, plant.getPlantType());
	        stmt.setInt(3, plant.getMinHarvestAge());
	        stmt.setDouble(4, plant.getMinTemperature());
	        stmt.setDouble(5, plant.getMaxTemperature());
	        stmt.setDouble(6, plant.getMinPH());
	        stmt.setDouble(7, plant.getMaxPH());

	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void update(Plant plant) {
	    try (Connection conn = DatabaseConfig.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

	        stmt.setString(1, plant.getPlantName());
	        stmt.setInt(2, plant.getMinHarvestAge());
	        stmt.setDouble(3, plant.getMinTemperature());
	        stmt.setDouble(4, plant.getMaxTemperature());
	        stmt.setDouble(5, plant.getMinPH());
	        stmt.setDouble(6, plant.getMaxPH());
	        stmt.setInt(7, plant.getPlantId());

	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void delete(int plantId) {
	    try (Connection conn = DatabaseConfig.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(DELETE)) {

	        stmt.setInt(1, plantId);
	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	//MAPPING ResultSet ke objek Plant
	private Plant mapResultSetToPlant(ResultSet rs) throws SQLException{
		
		String plantType = rs.getString("plant_type");
		Plant plant;
		
		if ("LEAF".equalsIgnoreCase(plantType)) {
            plant = new LeafPlant();
        } else if ("FRUIT".equalsIgnoreCase(plantType)) {
            plant = new FruitPlant();
        } else {
            throw new IllegalArgumentException("Unknown plant type: " + plantType);
        }
		
		plant.setPlantId(rs.getInt("plant_id"));
        plant.setPlantName(rs.getString("plant_name"));
        plant.setMinHarvestAge(rs.getInt("min_harvest_age"));
        plant.setMinTemperature(rs.getDouble("min_temperature"));
        plant.setMaxTemperature(rs.getDouble("max_temperature"));
        plant.setMinPH(rs.getDouble("min_ph"));
        plant.setMaxPH(rs.getDouble("max_ph"));

        return plant;
	}
}
