package dao;

import model.Pelanggan;
import java.util.List;

public interface PelangganDao {
	void save(Pelanggan pelanggan) throws Exception;
    void update(Pelanggan pelanggan) throws Exception;
    void delete(int id) throws Exception;
    List<Pelanggan> findAll() throws Exception;
}
