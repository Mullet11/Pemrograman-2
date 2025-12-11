package dao.impl;

import dao.DetailTransaksiDao;
import model.DetailTransaksi;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailTransaksiDaoImpl implements DetailTransaksiDao {

    @Override
    public void save(DetailTransaksi d) throws Exception {
        String sql = "INSERT INTO detail_transaksi (id_transaksi, id_menu, qty, subtotal) VALUES (?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, d.getTransaksiId());
            ps.setInt(2, d.getMenuId());
            ps.setInt(3, d.getQty());
            ps.setDouble(4, d.getSubTotal());

            ps.executeUpdate();
        }
    }

    @Override
    public List<DetailTransaksi> getByTransaksiId(int transaksiId) throws Exception {
        List<DetailTransaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM detail_transaksi WHERE id_transaksi=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, transaksiId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetailTransaksi d = new DetailTransaksi();
                d.setId(rs.getInt("id_detail"));
                d.setTransaksiId(rs.getInt("id_transaksi"));
                d.setMenuId(rs.getInt("id_menu"));
                d.setQty(rs.getInt("qty"));
                d.setSubTotal(rs.getDouble("subtotal"));
                list.add(d);
            }
        }
        return list;
    }
}
