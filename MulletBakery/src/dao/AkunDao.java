package dao;

import java.util.List;
import model.Akun;

public interface AkunDao {
    Akun findByUsername(String username) throws Exception;
    Akun findById(int id) throws Exception;
    List<Akun> getAll() throws Exception;
    void save(Akun akun) throws Exception;
    void update(Akun akun) throws Exception;
    void delete(int id) throws Exception;
}
