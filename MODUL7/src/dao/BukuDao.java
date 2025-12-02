package dao;

import model.Buku;
import java.util.List;

public interface BukuDao {
    void save(Buku buku) throws Exception;
    void update(Buku buku) throws Exception;
    void delete(int bukuId) throws Exception;
    List<Buku> findAll() throws Exception;
    Buku findById(int bukuId) throws Exception;
}