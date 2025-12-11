package dao;

import model.DetailTransaksi;

import java.util.List;

public interface DetailTransaksiDao {
    void save(DetailTransaksi d) throws Exception;
    List<DetailTransaksi> getByTransaksiId(int transaksiId) throws Exception;
}
