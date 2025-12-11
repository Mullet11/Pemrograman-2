package service;

import dao.PelangganDao;
import dao.impl.PelangganDaoImpl;
import model.Pelanggan;

import java.util.List;

public class PelangganService {

    private PelangganDao dao = new PelangganDaoImpl();

    public Pelanggan findById(int id) throws Exception {
        return dao.findById(id);
    }

    public List<Pelanggan> getAll() throws Exception {
        return dao.getAll();
    }

    public void save(Pelanggan p) throws Exception {

        if (p.getNama().trim().isEmpty())
            throw new Exception("Nama pelanggan wajib diisi.");

        if (p.getTelepon().trim().isEmpty())
            throw new Exception("Telepon wajib diisi.");

        dao.save(p);
    }

    public void update(Pelanggan p) throws Exception {

        if (p.getNama().trim().isEmpty())
            throw new Exception("Nama pelanggan wajib diisi.");

        if (p.getTelepon().trim().isEmpty())
            throw new Exception("Telepon wajib diisi.");

        dao.update(p);
    }

    public void delete(int id) throws Exception {
        dao.delete(id);
    }

    public int count() throws Exception {
        return getAll().size();
    }
    
    public String findNameById(int id) throws Exception {
        Pelanggan p = dao.findById(id);
        return (p != null) ? p.getNama() : "-";
    }

}
