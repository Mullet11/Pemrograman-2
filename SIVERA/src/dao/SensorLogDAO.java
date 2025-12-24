package dao;

import config.DatabaseConfig;
import model.SensorLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SensorLogDAO {
	public static final String INSERT = "INSERT INTO sensor_logs (batch_id, temperature, ph, recorded_at) VALUES (?, ?, ?, ?)";
	public static final String SELECT_BY_ID = "SELECT * FROM sensor_logs WHERE log_id = ?";
	public static final String SELECT_BY_BATCH = "SELECT * FROM sensor_logs WHERE batch_id = ? ORDER BY recorded_at DESC";
	
	public void insert(int batchId, SensorLog log) {
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(INSERT)){
			
			stmt.setInt(1, batchId);
			stmt.setDouble(2, log.getTemperature());
			stmt.setDouble(3, log.getPh());
			stmt.setTimestamp(4, new Timestamp(log.getRecordedAt().getTime()));
			
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public SensorLog findById(int logId) {
		SensorLog log = null;
		
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)){
			
			stmt.setInt(1, logId);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				log = mapResultSetToSensorLog(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return log;
	}
	
	public List<SensorLog> findByBatchId(int batchId){
		List<SensorLog> logs = new ArrayList<>();
		
		try(Connection conn = DatabaseConfig.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_BATCH)){
			
			stmt.setInt(1, batchId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				SensorLog log = mapResultSetToSensorLog(rs);
				logs.add(log);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return logs;
	}
	
	private SensorLog mapResultSetToSensorLog(ResultSet rs) throws SQLException{
		SensorLog log = new SensorLog();
		log.setLogId(rs.getInt("log_id"));
		log.setTemperature(rs.getDouble("temperature"));
		log.setPh(rs.getDouble("ph"));
		log.setRecordedAt(rs.getTimestamp("recorded_at"));
		
		return log;
	}
}
