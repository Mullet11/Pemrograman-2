package model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Penjualan {
    private final IntegerProperty penjualanId;
    private final IntegerProperty jumlah;
    private final DoubleProperty totalHarga;
    private final ObjectProperty<LocalDate> tanggal;
    
    // RELASI dengan tabel lain
    private final IntegerProperty pelangganId;
    private final IntegerProperty bukuId;
    
    // Untuk menampilkan nama di tabel (tidak disimpan di database)
    private final StringProperty namaPelanggan;
    private final StringProperty judulBuku;
    
    // Konstruktor untuk data baru
    public Penjualan(int jumlah, double totalHarga, LocalDate tanggal, 
                     int pelangganId, int bukuId) {
        this(0, jumlah, totalHarga, tanggal, pelangganId, bukuId, "", "");
    }
    
    // Konstruktor lengkap dari database (dengan join)
    public Penjualan(int penjualanId, int jumlah, double totalHarga, 
                     LocalDate tanggal, int pelangganId, int bukuId,
                     String namaPelanggan, String judulBuku) {
        this.penjualanId = new SimpleIntegerProperty(penjualanId);
        this.jumlah = new SimpleIntegerProperty(jumlah);
        this.totalHarga = new SimpleDoubleProperty(totalHarga);
        this.tanggal = new SimpleObjectProperty<>(tanggal);
        this.pelangganId = new SimpleIntegerProperty(pelangganId);
        this.bukuId = new SimpleIntegerProperty(bukuId);
        this.namaPelanggan = new SimpleStringProperty(namaPelanggan);
        this.judulBuku = new SimpleStringProperty(judulBuku);
    }
    
    // Getters
    public int getPenjualanId() {
        return penjualanId.get();
    }
    
    public int getJumlah() {
        return jumlah.get();
    }
    
    public double getTotalHarga() {
        return totalHarga.get();
    }
    
    public LocalDate getTanggal() {
        return tanggal.get();
    }
    
    public int getPelangganId() {
        return pelangganId.get();
    }
    
    public int getBukuId() {
        return bukuId.get();
    }
    
    public String getNamaPelanggan() {
        return namaPelanggan.get();
    }
    
    public String getJudulBuku() {
        return judulBuku.get();
    }
    
    // Setters
    public void setPenjualanId(int penjualanId) {
        this.penjualanId.set(penjualanId);
    }
    
    public void setJumlah(int jumlah) {
        this.jumlah.set(jumlah);
    }
    
    public void setTotalHarga(double totalHarga) {
        this.totalHarga.set(totalHarga);
    }
    
    public void setTanggal(LocalDate tanggal) {
        this.tanggal.set(tanggal);
    }
    
    public void setPelangganId(int pelangganId) {
        this.pelangganId.set(pelangganId);
    }
    
    public void setBukuId(int bukuId) {
        this.bukuId.set(bukuId);
    }
    
    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan.set(namaPelanggan);
    }
    
    public void setJudulBuku(String judulBuku) {
        this.judulBuku.set(judulBuku);
    }
    
    // Properties untuk TableView
    public IntegerProperty penjualanIdProperty() {
        return penjualanId;
    }
    
    public IntegerProperty jumlahProperty() {
        return jumlah;
    }
    
    public DoubleProperty totalHargaProperty() {
        return totalHarga;
    }
    
    public ObjectProperty<LocalDate> tanggalProperty() {
        return tanggal;
    }
    
    public IntegerProperty pelangganIdProperty() {
        return pelangganId;
    }
    
    public IntegerProperty bukuIdProperty() {
        return bukuId;
    }
    
    public StringProperty namaPelangganProperty() {
        return namaPelanggan;
    }
    
    public StringProperty judulBukuProperty() {
        return judulBuku;
    }
}