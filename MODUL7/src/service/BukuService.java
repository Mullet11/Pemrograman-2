package service;

import dao.BukuDao;
import dao.impl.BukuDaoImpl;
import model.Buku;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BukuService {
    private final BukuDao dao = new BukuDaoImpl();

    public List<Buku> getAll() throws Exception {
        return dao.findAll();
    }

    public void addData(Buku b) throws Exception {
        validate(b);
        checkDuplicateJudul(b.getJudul(), 0);
        dao.save(b);
    }

    public void updateData(Buku b) throws Exception {
        if (b.getBukuId() <= 0) throw new Exception("Pilih data tabel dulu untuk diedit!");
        validate(b);
        checkDuplicateJudul(b.getJudul(), b.getBukuId());
        dao.update(b);
    }

    public void deleteData(int bukuId) throws Exception {
        if (bukuId <= 0) throw new Exception("ID tidak valid!");
        dao.delete(bukuId);
    }

    private void validate(Buku b) throws Exception {
        // Validasi Judul
        if (b.getJudul() == null || b.getJudul().trim().isEmpty()) {
            throw new Exception("Judul tidak boleh kosong!");
        }
        if (b.getJudul().trim().length() < 3) {
            throw new Exception("Judul minimal 3 karakter!");
        }
        if (b.getJudul().trim().length() > 250) {
            throw new Exception("Judul maksimal 250 karakter!");
        }
        
        // Validasi Penulis
        if (b.getPenulis() == null || b.getPenulis().trim().isEmpty()) {
            throw new Exception("Penulis tidak boleh kosong!");
        }
        if (b.getPenulis().trim().length() < 3) {
            throw new Exception("Nama penulis minimal 3 karakter!");
        }
        
        // Validasi Harga
        if (b.getHarga() <= 0) {
            throw new Exception("Harga harus lebih dari 0!");
        }
        if (b.getHarga() > 10000000) {
            throw new Exception("Harga tidak boleh lebih dari Rp 10.000.000!");
        }
        
        // Validasi Stok
        if (b.getStok() < 0) {
            throw new Exception("Stok tidak boleh negatif!");
        }
        if (b.getStok() > 10000) {
            throw new Exception("Stok maksimal 10.000 unit!");
        }
    }

    private void checkDuplicateJudul(String judul, int currentId) throws Exception {
        List<Buku> allData = dao.findAll();
        for (Buku b : allData) {
            if (b.getJudul().equalsIgnoreCase(judul) && b.getBukuId() != currentId) {
                throw new Exception("Judul buku sudah terdaftar! Gunakan judul lain.");
            }
        }
    }

    // Export data ke CSV
    public void exportToCSV(ObservableList<Buku> data) throws Exception {
        if (data == null || data.isEmpty()) {
            throw new Exception("Tidak ada data untuk di-export!");
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "buku_export_" + timestamp + ".csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("ID,Judul,Penulis,Harga,Stok\n");
            
            for (Buku b : data) {
                writer.append(String.valueOf(b.getBukuId())).append(",");
                writer.append(escapeCsv(b.getJudul())).append(",");
                writer.append(escapeCsv(b.getPenulis())).append(",");
                writer.append(String.valueOf(b.getHarga())).append(",");
                writer.append(String.valueOf(b.getStok())).append("\n");
            }
            
            writer.flush();
        } catch (IOException e) {
            throw new Exception("Gagal export ke CSV: " + e.getMessage());
        }
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // Get statistics
    public int getTotalBuku() throws Exception {
        return dao.findAll().size();
    }

    public double getTotalNilaiStok() throws Exception {
        List<Buku> list = dao.findAll();
        double total = 0;
        for (Buku b : list) {
            total += (b.getHarga() * b.getStok());
        }
        return total;
    }
}