package service;

import dao.AkunDao;
import dao.impl.AkunDaoImpl;
import model.Akun;

import java.util.List;

public class AkunService {

    private AkunDao dao = new AkunDaoImpl();

    public Akun findByUsername(String username) throws Exception {
        return dao.findByUsername(username);
    }

    public Akun findById(int id) throws Exception {
        return dao.findById(id);
    }

    public List<Akun> getAll() throws Exception {
        return dao.getAll();
    }

    public void save(Akun akun) throws Exception {

        if (akun.getUsername().trim().isEmpty())
            throw new Exception("Username wajib diisi.");

        if (akun.getPassword().trim().isEmpty())
            throw new Exception("Password wajib diisi.");

        if (dao.findByUsername(akun.getUsername()) != null)
            throw new Exception("Username sudah digunakan!");

        dao.save(akun);
    }

    public void update(Akun akun) throws Exception {

        if (akun.getUsername().trim().isEmpty())
            throw new Exception("Username wajib diisi.");

        if (akun.getPassword().trim().isEmpty())
            throw new Exception("Password wajib diisi.");

        dao.update(akun);
    }

    public void delete(int id) throws Exception {
        dao.delete(id);
    }
}
