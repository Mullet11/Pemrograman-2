package Modul3.Soal2;
import java.util.HashMap;

public class Negara {
	private String nama;
	private String jenisKepemimpinan;
	private String namaPemimpin;
	private int tanggalKemerdekaan;
	private int bulanKemerdekaan;
	private int tahunKemerdekaan;
	
	private final HashMap<Integer, String> namaBulan;
	
	public Negara(String nama, String jenisKepemimpinan, String namaPemimpin, int tanggalKemerdekaan, int bulanKemerdekaan, int tahunKemerdekaan) {
		this.nama = nama;
		this.jenisKepemimpinan = jenisKepemimpinan;
		this.namaPemimpin = namaPemimpin;
		this.tanggalKemerdekaan = tanggalKemerdekaan;
		this.bulanKemerdekaan = bulanKemerdekaan;
		this.tahunKemerdekaan = tahunKemerdekaan;
		
		this.namaBulan = new HashMap<>();
		this.namaBulan.put(1, "Januari");
		this.namaBulan.put(2, "Februari");
		this.namaBulan.put(3, "Maret");
		this.namaBulan.put(4, "April");
		this.namaBulan.put(5, "Mei");
		this.namaBulan.put(6, "Juni");
		this.namaBulan.put(7, "Juli");
		this.namaBulan.put(8, "agustus");
		this.namaBulan.put(9, "September");
		this.namaBulan.put(10, "Oktober");
		this.namaBulan.put(11, "November");
		this.namaBulan.put(12, "Desember");
	}
	
	public Negara(String nama, String jenisKepemimpinan, String namaPemimpin) {
		this(nama, jenisKepemimpinan, namaPemimpin, 0, 0, 0);
	}
	
	public void info() {
		String jenisPemimpinFormatted;
		
		switch(jenisKepemimpinan.toLowerCase()) {
		case "presiden":
			jenisPemimpinFormatted = "Presiden";
			break;
		case "perdana menteri":
			jenisPemimpinFormatted = "Perdana Menteri";
			break;
		case "monarki":
			jenisPemimpinFormatted = "Raja";
			break;
		default:
			jenisPemimpinFormatted = jenisKepemimpinan;
			break;
		}
		
		System.out.println("\nNegara " + this.nama + " mempunyai " + jenisPemimpinFormatted + " bernama " + this.namaPemimpin);
		if(!this.jenisKepemimpinan.equalsIgnoreCase("monarki")) {
			System.out.println("Deklarasi Kemerdekaan pada Tanggal " + this.tanggalKemerdekaan + " " + this.namaBulan.get(this.bulanKemerdekaan) + " " + this.tahunKemerdekaan);
		}
	}
}
