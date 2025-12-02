package dao;

import model.Penjualan;
import java.util.List;

public interface PenjualanDao {
    void save(Penjualan penjualan) throws Exception;
    void update(Penjualan penjualan) throws Exception;
    void delete(int penjualanId) throws Exception;
    List<Penjualan> findAll() throws Exception;
    List<Penjualan> findByPelanggan(int pelangganId) throws Exception;
}