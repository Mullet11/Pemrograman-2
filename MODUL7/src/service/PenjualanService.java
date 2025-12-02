package service;

import dao.PenjualanDao;
import dao.BukuDao;
import dao.impl.PenjualanDaoImpl;
import dao.impl.BukuDaoImpl;
import model.Penjualan;
import model.Buku;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PenjualanService {
    private final PenjualanDao dao = new PenjualanDaoImpl();
    private final BukuDao bukuDao = new BukuDaoImpl();

    public List<Penjualan> getAll() throws Exception {
        return dao.findAll();
    }

    public void addData(Penjualan p) throws Exception {
        validate(p);
        
        // Cek stok buku sebelum menyimpan
        Buku buku = bukuDao.findById(p.getBukuId());
        if (buku == null) {
            throw new Exception("Buku tidak ditemukan!");
        }
        if (buku.getStok() < p.getJumlah()) {
            throw new Exception("Stok buku tidak mencukupi! Stok tersedia: " + buku.getStok());
        }
        
        // Simpan penjualan
        dao.save(p);
        
        // Update stok buku
        buku.setStok(buku.getStok() - p.getJumlah());
        bukuDao.update(buku);
    }

    public void updateData(Penjualan p) throws Exception {
        if (p.getPenjualanId() <= 0) throw new Exception("Pilih data tabel dulu untuk diedit!");
        validate(p);
        dao.update(p);
    }

    public void deleteData(int penjualanId) throws Exception {
        if (penjualanId <= 0) throw new Exception("ID tidak valid!");
        dao.delete(penjualanId);
    }

    private void validate(Penjualan p) throws Exception {
        // Validasi Jumlah
        if (p.getJumlah() <= 0) {
            throw new Exception("Jumlah harus lebih dari 0!");
        }
        if (p.getJumlah() > 1000) {
            throw new Exception("Jumlah maksimal 1000 unit per transaksi!");
        }
        
        // Validasi Total Harga
        if (p.getTotalHarga() <= 0) {
            throw new Exception("Total harga harus lebih dari 0!");
        }
        
        // Validasi Tanggal
        if (p.getTanggal() == null) {
            throw new Exception("Tanggal tidak boleh kosong!");
        }
        if (p.getTanggal().isAfter(LocalDate.now())) {
            throw new Exception("Tanggal tidak boleh di masa depan!");
        }
        
        // Validasi Foreign Key
        if (p.getPelangganId() <= 0) {
            throw new Exception("Pilih pelanggan terlebih dahulu!");
        }
        if (p.getBukuId() <= 0) {
            throw new Exception("Pilih buku terlebih dahulu!");
        }
    }

    // Export data ke CSV
    public void exportToCSV(ObservableList<Penjualan> data) throws Exception {
        if (data == null || data.isEmpty()) {
            throw new Exception("Tidak ada data untuk di-export!");
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "penjualan_export_" + timestamp + ".csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("ID,Pelanggan,Buku,Jumlah,Total Harga,Tanggal\n");
            
            for (Penjualan p : data) {
                writer.append(String.valueOf(p.getPenjualanId())).append(",");
                writer.append(escapeCsv(p.getNamaPelanggan())).append(",");
                writer.append(escapeCsv(p.getJudulBuku())).append(",");
                writer.append(String.valueOf(p.getJumlah())).append(",");
                writer.append(String.valueOf(p.getTotalHarga())).append(",");
                writer.append(p.getTanggal().toString()).append("\n");
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
    public int getTotalTransaksi() throws Exception {
        return dao.findAll().size();
    }

    public double getTotalPendapatan() throws Exception {
        List<Penjualan> list = dao.findAll();
        double total = 0;
        for (Penjualan p : list) {
            total += p.getTotalHarga();
        }
        return total;
    }

    public List<Penjualan> getPenjualanByPelanggan(int pelangganId) throws Exception {
        return dao.findByPelanggan(pelangganId);
    }
}