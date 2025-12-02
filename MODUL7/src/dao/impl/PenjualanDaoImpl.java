package dao.impl;

import dao.PenjualanDao;
import model.Penjualan;
import util.DatabaseHelper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PenjualanDaoImpl implements PenjualanDao {
    
    @Override
    public void save(Penjualan penjualan) throws Exception {
        String sql = "INSERT INTO penjualan (jumlah, total_harga, tanggal, pelanggan_id, buku_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, penjualan.getJumlah());
            ps.setDouble(2, penjualan.getTotalHarga());
            ps.setDate(3, Date.valueOf(penjualan.getTanggal()));
            ps.setInt(4, penjualan.getPelangganId());
            ps.setInt(5, penjualan.getBukuId());
            ps.executeUpdate();
        }
    }
    
    @Override
    public void update(Penjualan penjualan) throws Exception {
        String sql = "UPDATE penjualan SET jumlah=?, total_harga=?, tanggal=?, " +
                     "pelanggan_id=?, buku_id=? WHERE penjualan_id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, penjualan.getJumlah());
            ps.setDouble(2, penjualan.getTotalHarga());
            ps.setDate(3, Date.valueOf(penjualan.getTanggal()));
            ps.setInt(4, penjualan.getPelangganId());
            ps.setInt(5, penjualan.getBukuId());
            ps.setInt(6, penjualan.getPenjualanId());
            ps.executeUpdate();
        }
    }
    
    @Override
    public void delete(int penjualanId) throws Exception {
        String sql = "DELETE FROM penjualan WHERE penjualan_id=?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, penjualanId);
            ps.executeUpdate();
        }
    }
    
    @Override
    public List<Penjualan> findAll() throws Exception {
        List<Penjualan> list = new ArrayList<>();
        // JOIN dengan tabel pelanggan dan buku untuk mendapatkan nama
        String sql = "SELECT p.*, pel.nama as nama_pelanggan, b.judul as judul_buku " +
                     "FROM penjualan p " +
                     "LEFT JOIN pelanggan pel ON p.pelanggan_id = pel.id " +
                     "LEFT JOIN buku b ON p.buku_id = b.buku_id " +
                     "ORDER BY p.penjualan_id DESC";
        
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                LocalDate tanggal = rs.getDate("tanggal").toLocalDate();
                list.add(new Penjualan(
                    rs.getInt("penjualan_id"),
                    rs.getInt("jumlah"),
                    rs.getDouble("total_harga"),
                    tanggal,
                    rs.getInt("pelanggan_id"),
                    rs.getInt("buku_id"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("judul_buku")
                ));
            }
        }
        return list;
    }
    
    @Override
    public List<Penjualan> findByPelanggan(int pelangganId) throws Exception {
        List<Penjualan> list = new ArrayList<>();
        String sql = "SELECT p.*, pel.nama as nama_pelanggan, b.judul as judul_buku " +
                     "FROM penjualan p " +
                     "LEFT JOIN pelanggan pel ON p.pelanggan_id = pel.id " +
                     "LEFT JOIN buku b ON p.buku_id = b.buku_id " +
                     "WHERE p.pelanggan_id = ? " +
                     "ORDER BY p.tanggal DESC";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pelangganId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                LocalDate tanggal = rs.getDate("tanggal").toLocalDate();
                list.add(new Penjualan(
                    rs.getInt("penjualan_id"),
                    rs.getInt("jumlah"),
                    rs.getDouble("total_harga"),
                    tanggal,
                    rs.getInt("pelanggan_id"),
                    rs.getInt("buku_id"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("judul_buku")
                ));
            }
        }
        return list;
    }
}