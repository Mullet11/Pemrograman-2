package dao.impl;

import dao.PelangganDao;
import model.Pelanggan;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganDaoImpl implements PelangganDao {

    private Pelanggan map(ResultSet rs) throws Exception {
        Pelanggan p = new Pelanggan();
        p.setId(rs.getInt("id_pelanggan"));
        p.setNama(rs.getString("nama"));
        p.setTelepon(rs.getString("telepon"));
        p.setEmail(rs.getString("email"));
        return p;
    }

    @Override
    public Pelanggan findById(int id) throws Exception {
        String sql = "SELECT * FROM pelanggan WHERE id_pelanggan=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    @Override
    public List<Pelanggan> getAll() throws Exception {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan ORDER BY id_pelanggan DESC";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    @Override
    public void save(Pelanggan p) throws Exception {
        String sql = "INSERT INTO pelanggan (nama, telepon, email) VALUES (?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getNama());
            ps.setString(2, p.getTelepon());
            ps.setString(3, p.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Pelanggan p) throws Exception {
        String sql = "UPDATE pelanggan SET nama=?, telepon=?, email=? WHERE id_pelanggan=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getNama());
            ps.setString(2, p.getTelepon());
            ps.setString(3, p.getEmail());
            ps.setInt(4, p.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
