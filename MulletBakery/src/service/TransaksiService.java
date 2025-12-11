package service;

import dao.TransaksiDao;
import dao.impl.TransaksiDaoImpl;
import model.Transaksi;

import java.util.List;

public class TransaksiService {

    private TransaksiDao dao = new TransaksiDaoImpl();

    public int save(Transaksi t) throws Exception {

        if (t.getPelangganId() <= 0)
            throw new Exception("Pelanggan tidak valid!");

        if (t.getId() <= 0)
            throw new Exception("Akun pembuat transaksi tidak valid!");

        if (t.getTotal() <= 0)
            throw new Exception("Total transaksi tidak boleh 0.");

        return dao.save(t);
    }

    public List<Transaksi> getAll() throws Exception {
        return dao.getAll();
    }

    public Transaksi findById(int id) throws Exception {
        return dao.findById(id);
    }

    public int count() throws Exception {
        return dao.count();
    }
    
    public double getTotalPendapatan() throws Exception {
    	return dao.getTotalPendapatan();
    }
}
