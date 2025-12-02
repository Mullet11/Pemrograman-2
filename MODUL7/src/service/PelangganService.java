package service;

import dao.PelangganDao;
import dao.impl.PelangganDaoImpl;
import model.Pelanggan;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class PelangganService {
    private final PelangganDao dao = new PelangganDaoImpl();
    
    // Email regex pattern yang lebih strict
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+62|62|0)[0-9]{9,12}$");
    
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L}\\s']+$");


    public List<Pelanggan> getAll() throws Exception {
        return dao.findAll();
    }

    public void addData(Pelanggan p) throws Exception {
        validate(p);
        checkDuplicateEmail(p.getEmail(), 0);
        checkDuplicateTelepon(p.getTelepon(), 0);
        dao.save(p);
    }

    public void updateData(Pelanggan p) throws Exception {
        if (p.getId() <= 0) throw new Exception("Pilih data tabel dulu untuk diedit!");
        validate(p);
        checkDuplicateEmail(p.getEmail(), p.getId());
        checkDuplicateTelepon(p.getTelepon(), p.getId()); 
        dao.update(p);
    }

    public void deleteData(int id) throws Exception {
        if (id <= 0) throw new Exception("ID tidak valid!");
        dao.delete(id);
    }

    private void validate(Pelanggan p) throws Exception {
        // Validasi Nama
        if (p.getNama() == null || p.getNama().trim().isEmpty()) {
            throw new Exception("Nama tidak boleh kosong!");
        }
        if (p.getNama().trim().length() < 3) {
            throw new Exception("Nama minimal 3 karakter!");
        }
        if (p.getNama().trim().length() > 100) {
            throw new Exception("Nama maksimal 100 karakter!");
        }
        
        if (!NAME_PATTERN.matcher(p.getNama().trim()).matches()) {
            throw new Exception("Nama hanya boleh berisi huruf, spasi, dan tanda apostrophe ('). Tidak boleh mengandung angka atau simbol lain!");
        }
        
        // Validasi Email dengan regex yang lebih strict
        if (p.getEmail() == null || p.getEmail().trim().isEmpty()) {
            throw new Exception("Email tidak boleh kosong!");
        }
        if (!EMAIL_PATTERN.matcher(p.getEmail()).matches()) {
            throw new Exception("Format email tidak valid! Contoh: nama@domain.com");
        }
        
        if (p.getEmail().contains(" ")) {
            throw new Exception("Email tidak boleh mengandung spasi!");
        }
        
        if (p.getTelepon() == null || p.getTelepon().trim().isEmpty()) {
            throw new Exception("Nomor telepon tidak boleh kosong!");
        }
        
        String cleanPhone = p.getTelepon().replaceAll("[\\s-]", ""); // menghilangkan spasi and garis miring
        
        if (!PHONE_PATTERN.matcher(cleanPhone).matches()) {
            throw new Exception("Format telepon tidak valid! Contoh: 081234567890 atau +6281234567890");
        }
        
        if (cleanPhone.length() < 10) {
            throw new Exception("Nomor telepon minimal 10 digit!");
        }
        
        if (cleanPhone.length() > 15) {
            throw new Exception("Nomor telepon maksimal 15 digit!");
        }
        
        if (!p.getTelepon().matches("^[0-9+]+$")) {
            throw new Exception("Nomor telepon hanya boleh berisi angka dan tanda + di awal!");
        }
    }

    private void checkDuplicateEmail(String email, int currentId) throws Exception {
        List<Pelanggan> allData = dao.findAll();
        for (Pelanggan p : allData) {
            if (p.getEmail().equalsIgnoreCase(email) && p.getId() != currentId) {
                throw new Exception("Email sudah terdaftar! Gunakan email lain.");
            }
        }
    }
    
    private void checkDuplicateTelepon(String telepon, int currentId) throws Exception {
        String cleanPhone = telepon.replaceAll("[\\s-]", "");
        List<Pelanggan> allData = dao.findAll();
        for (Pelanggan p : allData) {
            String existingPhone = p.getTelepon().replaceAll("[\\s-]", "");
            if (existingPhone.equals(cleanPhone) && p.getId() != currentId) {
                throw new Exception("Nomor telepon sudah terdaftar! Gunakan nomor lain.");
            }
        }
    }

    // Export data ke CSV
    public void exportToCSV(ObservableList<Pelanggan> data) throws Exception {
        if (data == null || data.isEmpty()) {
            throw new Exception("Tidak ada data untuk di-export!");
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "pelanggan_export_" + timestamp + ".csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("ID,Nama,Email,Telepon\n");
            
            // Data rows
            for (Pelanggan p : data) {
                writer.append(String.valueOf(p.getId())).append(",");
                writer.append(escapeCsv(p.getNama())).append(",");
                writer.append(escapeCsv(p.getEmail())).append(",");
                writer.append(escapeCsv(p.getTelepon())).append("\n");
            }
            
            writer.flush();
        } catch (IOException e) {
            throw new Exception("Gagal export ke CSV: " + e.getMessage());
        }
    }

    // Helper method untuk escape special characters di CSV
    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // Get statistics
    public int getTotalPelanggan() throws Exception {
        return dao.findAll().size();
    }

}