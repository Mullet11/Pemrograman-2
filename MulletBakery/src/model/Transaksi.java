package model;

import java.sql.Timestamp;

public class Transaksi {

    private int id;
    private int pelangganId;
    private Timestamp tanggal;
    private double total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPelangganId() {
        return pelangganId;
    }

    public void setPelangganId(int pelangganId) {
        this.pelangganId = pelangganId;
    }

    public Timestamp getTanggal() {
        return tanggal;
    }

    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
