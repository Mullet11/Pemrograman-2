package model;

import javafx.beans.property.*;

public class Buku {
    private final IntegerProperty bukuId;
    private final StringProperty judul;
    private final StringProperty penulis;
    private final DoubleProperty harga;
    private final IntegerProperty stok;
    
    // Konstruktor untuk data baru (ID belum ada)
    public Buku(String judul, String penulis, double harga, int stok) {
        this(0, judul, penulis, harga, stok);
    }
    
    // Konstruktor lengkap untuk data dari database
    public Buku(int bukuId, String judul, String penulis, double harga, int stok) {
        this.bukuId = new SimpleIntegerProperty(bukuId);
        this.judul = new SimpleStringProperty(judul);
        this.penulis = new SimpleStringProperty(penulis);
        this.harga = new SimpleDoubleProperty(harga);
        this.stok = new SimpleIntegerProperty(stok);
    }
    
    // Getters
    public int getBukuId() {
        return bukuId.get();
    }
    
    public String getJudul() {
        return judul.get();
    }
    
    public String getPenulis() {
        return penulis.get();
    }
    
    public double getHarga() {
        return harga.get();
    }
    
    public int getStok() {
        return stok.get();
    }
    
    // Setters
    public void setBukuId(int bukuId) {
        this.bukuId.set(bukuId);
    }
    
    public void setJudul(String judul) {
        this.judul.set(judul);
    }
    
    public void setPenulis(String penulis) {
        this.penulis.set(penulis);
    }
    
    public void setHarga(double harga) {
        this.harga.set(harga);
    }
    
    public void setStok(int stok) {
        this.stok.set(stok);
    }
    
    // Properties untuk TableView
    public IntegerProperty bukuIdProperty() {
        return bukuId;
    }
    
    public StringProperty judulProperty() {
        return judul;
    }
    
    public StringProperty penulisProperty() {
        return penulis;
    }
    
    public DoubleProperty hargaProperty() {
        return harga;
    }
    
    public IntegerProperty stokProperty() {
        return stok;
    }
    
    @Override
    public String toString() {
        return judul.get() + " - " + penulis.get();
    }
}