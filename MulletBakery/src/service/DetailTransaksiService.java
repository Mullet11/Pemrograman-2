package service;

import dao.DetailTransaksiDao;
import dao.impl.DetailTransaksiDaoImpl;
import model.DetailTransaksi;

import java.util.List;

public class DetailTransaksiService {

    private DetailTransaksiDao dao = new DetailTransaksiDaoImpl();

    public void save(DetailTransaksi d) throws Exception {

        if (d.getQty() <= 0)
            throw new Exception("Qty tidak valid!");

        if (d.getSubTotal() <= 0)
            throw new Exception("Subtotal tidak valid!");

        dao.save(d);
    }

    public List<DetailTransaksi> getByTransaksi(int trxId) throws Exception {
        return dao.getByTransaksiId(trxId);
    }
}
