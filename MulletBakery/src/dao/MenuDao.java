package dao;

import java.util.List;
import model.Menu;

public interface MenuDao {
    Menu findById(int id) throws Exception;
    List<Menu> getAll() throws Exception;
    void save(Menu menu) throws Exception;
    void update(Menu menu) throws Exception;
    void delete(int id) throws Exception;
    void kurangiStok(int idMenu, int qty) throws Exception;
}
