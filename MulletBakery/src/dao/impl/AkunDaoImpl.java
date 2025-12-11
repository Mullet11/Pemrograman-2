package dao.impl;

import dao.AkunDao;
import model.Akun;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AkunDaoImpl implements AkunDao {

    @Override
    public Akun findByUsername(String username) throws Exception {
        String sql = "SELECT * FROM akun WHERE username = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);
            return null;
        }
    }

    @Override
    public Akun findById(int id) throws Exception {
        String sql = "SELECT * FROM akun WHERE id_akun = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);
            return null;
        }
    }

    @Override
    public List<Akun> getAll() throws Exception {
        List<Akun> list = new ArrayList<>();
        String sql = "SELECT * FROM akun ORDER BY id_akun DESC";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    @Override
    public void save(Akun akun) throws Exception {
        String sql = "INSERT INTO akun (username, password, nama, role) VALUES (?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, akun.getUsername());
            ps.setString(2, akun.getPassword());
            ps.setString(3, akun.getNama());
            ps.setString(4, akun.getRole());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Akun akun) throws Exception {
        String sql = "UPDATE akun SET username=?, password=?, nama=?, role=? WHERE id_akun=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, akun.getUsername());
            ps.setString(2, akun.getPassword());
            ps.setString(3, akun.getNama());
            ps.setString(4, akun.getRole());
            ps.setInt(5, akun.getIdAkun());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM akun WHERE id_akun=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Akun map(ResultSet rs) throws Exception {
        Akun a = new Akun();
        a.setIdAkun(rs.getInt("id_akun"));
        a.setUsername(rs.getString("username"));
        a.setPassword(rs.getString("password"));
        a.setNama(rs.getString("nama"));
        a.setRole(rs.getString("role"));
        return a;
    }
}
