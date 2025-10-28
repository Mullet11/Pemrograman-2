package Modul4.Soal2;

public class HewanPeliharaan {
	private String nama;
	private String ras;
	
	public HewanPeliharaan(String n, String r) {
		this.nama = n;
		this.ras = r;
	}
	
	public void display() {
		System.out.println("\nDetail Hewan Peliharaan:");
		System.out.println("Nama Hewan Peliharaan: " + nama + "\nDengan Ras: " + ras);
	}
}
