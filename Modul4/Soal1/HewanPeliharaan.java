package Modul4.Soal1;

public class HewanPeliharaan {
	private String n;
	private String r;
	
	public HewanPeliharaan(String n, String r) {
		this.n = n;
		this.r = r;
	}
	
	public void display () {
		System.out.println("\nDetail Hewan Peliharaan:");
		System.out.println("Nama Hewan peliharaanku adalah: " + this.n + "\nDengan ras: " + this.r);
	}
}
