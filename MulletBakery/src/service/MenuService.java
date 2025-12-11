package service;

import dao.MenuDao;
import dao.impl.MenuDaoImpl;
import model.Menu;

import java.util.List;

public class MenuService {

    private MenuDao dao = new MenuDaoImpl();

    public Menu findById(int id) throws Exception {
        return dao.findById(id);
    }

    public List<Menu> getAll() throws Exception {
        return dao.getAll();
    }

    public void save(Menu m) throws Exception {

        if (m.getNama().trim().isEmpty())
            throw new Exception("Nama menu wajib diisi!");

        if (m.getHarga() <= 0)
            throw new Exception("Harga harus lebih dari 0!");

        if (m.getStok() < 0)
            throw new Exception("Stok tidak boleh negatif!");

        dao.save(m);
    }

    public void update(Menu m) throws Exception {

        if (m.getNama().trim().isEmpty())
            throw new Exception("Nama menu wajib diisi!");

        if (m.getHarga() <= 0)
            throw new Exception("Harga harus lebih dari 0!");

        if (m.getStok() < 0)
            throw new Exception("Stok tidak boleh negatif!");

        dao.update(m);
    }

    public void delete(int id) throws Exception {
        dao.delete(id);
    }

    public void kurangiStok(int idMenu, int qty) throws Exception {
        dao.kurangiStok(idMenu, qty);
    }

    public int count() throws Exception {
        return getAll().size();
    }
}
