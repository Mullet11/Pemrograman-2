package dao;

import java.util.List;
import model.Transaksi;

public interface TransaksiDao {
    int save(Transaksi t) throws Exception;
    List<Transaksi> getAll() throws Exception;
    Transaksi findById(int id) throws Exception;
    int count() throws Exception;
    double getTotalPendapatan() throws Exception;
}
