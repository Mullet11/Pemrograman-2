package dao.impl;

import dao.PelangganDao;
import model.Pelanggan;
import util.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganDaoImpl implements PelangganDao {
    
    @Override
    public void save(Pelanggan p) throws Exception {
        String sql = "INSERT INTO pelanggan (nama, email, telepon) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getTelepon()); 
            ps.executeUpdate();
        }
    }
    
    @Override
    public void update(Pelanggan p) throws Exception {
        String sql = "UPDATE pelanggan SET nama=?, email=?, telepon=? WHERE id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getTelepon()); 
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        }
    }
    
    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM pelanggan WHERE id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    @Override
    public List<Pelanggan> findAll() throws Exception {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan ORDER BY id DESC";
        
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(new Pelanggan(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("telepon")
                ));
            }
        }
        return list;
    }
}