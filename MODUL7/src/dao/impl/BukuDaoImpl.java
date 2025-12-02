package dao.impl;

import dao.BukuDao;
import model.Buku;
import util.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDaoImpl implements BukuDao {
    
    @Override
    public void save(Buku buku) throws Exception {
        String sql = "INSERT INTO buku (judul, penulis, harga, stok) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setDouble(3, buku.getHarga());
            ps.setInt(4, buku.getStok());
            ps.executeUpdate();
        }
    }
    
    @Override
    public void update(Buku buku) throws Exception {
        String sql = "UPDATE buku SET judul=?, penulis=?, harga=?, stok=? WHERE buku_id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setDouble(3, buku.getHarga());
            ps.setInt(4, buku.getStok());
            ps.setInt(5, buku.getBukuId());
            ps.executeUpdate();
        }
    }
    
    @Override
    public void delete(int bukuId) throws Exception {
        String sql = "DELETE FROM buku WHERE buku_id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bukuId);
            ps.executeUpdate();
        }
    }
    
    @Override
    public List<Buku> findAll() throws Exception {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku ORDER BY buku_id DESC";
        
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(new Buku(
                    rs.getInt("buku_id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                ));
            }
        }
        return list;
    }
    
    @Override
    public Buku findById(int bukuId) throws Exception {
        String sql = "SELECT * FROM buku WHERE buku_id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bukuId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Buku(
                    rs.getInt("buku_id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                );
            }
        }
        return null;
    }
}