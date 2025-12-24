package dao;

import config.DatabaseConfig;
import model.Rack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RackDAO {
	private static final String INSERT = "INSERT INTO racks (rack_name, location, capacity) VALUES (?, ?, ?)";
	private static final String SELECT_ALL = "SELECT * FROM racks";
	private static final String SELECT_BY_ID = "SELECT * FROM racks WHERE rack_id = ?";
	private static final String UPDATE = "UPDATE racks SET rack_name = ?, location = ?, capacity = ? WHERE rack_id = ?";
	private static final String DELETE = "DELETE FROM racks WHERE rack_id = ?";
	
	public void insert(Rack rack) {
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(INSERT)){
			
			stmt.setString(1, rack.getRackName());
			stmt.setString(2, rack.getLocation());
			stmt.setInt(3, rack.getCapacity());
			
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Rack> findAll(){
		List<Rack> racks = new ArrayList<>();
		
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
			ResultSet rs = stmt.executeQuery()){
			
			while(rs.next()) {
				Rack rack = mapResultSetToRack(rs);
				racks.add(rack);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return racks;
	}
	
	public Rack findById(int rackId) {
        Rack rack = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setInt(1, rackId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rack = mapResultSetToRack(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rack;
    }
	
	public void update(Rack rack) {
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(UPDATE)){
			
			stmt.setString(1, rack.getRackName());
			stmt.setString(2, rack.getLocation());
			stmt.setInt(3, rack.getCapacity());
			stmt.setInt(4, rack.getRackId());
			
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int rackId) {
		try(Connection conn = DatabaseConfig.getConnection();
				PreparedStatement stmt = conn.prepareStatement(DELETE)){
			
			stmt.setInt(1, rackId);
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Rack mapResultSetToRack(ResultSet rs) throws SQLException{
		
		Rack rack = new Rack();
		rack.setRackId(rs.getInt("rack_id"));
		rack.setRackName(rs.getString("rack_name"));
		rack.setLocation(rs.getString("location"));
		rack.setCapacity(rs.getInt("capacity"));
		
		return rack;
	}
}
