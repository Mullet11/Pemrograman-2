package model;

public class Menu {

    private int id;
    private String nama;
    private double harga;
    private int stok;
    private String deskripsi;

    public Menu() {}

    public Menu(int id, String nama, double harga, int stok, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.deskripsi = deskripsi;
    }
    
    @Override
    public String toString() {
        return nama + " - Rp " + harga;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
