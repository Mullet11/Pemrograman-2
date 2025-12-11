package dao;

import java.util.List;
import model.Pelanggan;

public interface PelangganDao {
    Pelanggan findById(int id) throws Exception;
    List<Pelanggan> getAll() throws Exception;
    void save(Pelanggan p) throws Exception;
    void update(Pelanggan p) throws Exception;
    void delete(int id) throws Exception;
}
