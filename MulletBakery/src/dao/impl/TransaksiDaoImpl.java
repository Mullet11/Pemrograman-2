package dao.impl;

import dao.TransaksiDao;
import model.Transaksi;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDaoImpl implements TransaksiDao {

    private Transaksi map(ResultSet rs) throws Exception {
        Transaksi t = new Transaksi();
        t.setId(rs.getInt("id_transaksi"));
        t.setId(rs.getInt("id_akun"));
        t.setPelangganId(rs.getInt("id_pelanggan"));
        t.setTanggal(rs.getTimestamp("tanggal"));
        t.setTotal(rs.getDouble("total"));
        return t;
    }

    @Override
    public int save(Transaksi t) throws Exception {
        String sql = "INSERT INTO transaksi (id_akun, id_pelanggan, tanggal, total) VALUES (?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, t.getId());
            ps.setInt(2, t.getPelangganId());
            ps.setTimestamp(3, t.getTanggal());
            ps.setDouble(4, t.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

            throw new Exception("Gagal mengambil ID transaksi.");
        }
    }

    @Override
    public List<Transaksi> getAll() throws Exception {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi ORDER BY id_transaksi DESC";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    @Override
    public Transaksi findById(int id) throws Exception {
        String sql = "SELECT * FROM transaksi WHERE id_transaksi=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
            return null;
        }
    }

    @Override
    public int count() throws Exception {
        String sql = "SELECT COUNT(*) FROM transaksi";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public double getTotalPendapatan() throws Exception {
        String sql = "SELECT SUM(total) FROM transaksi";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
            return 0;
        }
    }
}
