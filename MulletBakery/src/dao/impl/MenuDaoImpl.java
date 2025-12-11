package dao.impl;

import dao.MenuDao;
import model.Menu;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoImpl implements MenuDao {

    private Menu map(ResultSet rs) throws Exception {
        Menu m = new Menu();
        m.setId(rs.getInt("id_menu"));
        m.setNama(rs.getString("nama_menu"));
        m.setHarga(rs.getDouble("harga"));
        m.setStok(rs.getInt("stok"));
        m.setDeskripsi(rs.getString("deskripsi"));
        return m;
    }

    @Override
    public Menu findById(int id) throws Exception {
        String sql = "SELECT * FROM menu WHERE id_menu=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);
            return null;
        }
    }

    @Override
    public List<Menu> getAll() throws Exception {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu ORDER BY id_menu DESC";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    @Override
    public void save(Menu m) throws Exception {
        String sql = "INSERT INTO menu (nama_menu, harga, stok, deskripsi) VALUES (?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, m.getNama());
            ps.setDouble(2, m.getHarga());
            ps.setInt(3, m.getStok());
            ps.setString(4, m.getDeskripsi());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Menu m) throws Exception {
        String sql = "UPDATE menu SET nama_menu=?, harga=?, stok=?, deskripsi=? WHERE id_menu=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, m.getNama());
            ps.setDouble(2, m.getHarga());
            ps.setInt(3, m.getStok());
            ps.setString(4, m.getDeskripsi());
            ps.setInt(5, m.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM menu WHERE id_menu=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void kurangiStok(int idMenu, int qty) throws Exception {
        String sql = "UPDATE menu SET stok = stok - ? WHERE id_menu=? AND stok >= ?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, idMenu);
            ps.setInt(3, qty);

            int updated = ps.executeUpdate();
            if (updated == 0)
                throw new Exception("Stok tidak cukup untuk menu id=" + idMenu);
        }
    }
}
