package Modul4.Soal2;

public class Anjing extends HewanPeliharaan {
	private String warnaBulu;
	private String[] kemampuan;
	
	public Anjing(String n, String r,String w, String[] k ) {
		super(n, r);
		this.warnaBulu = w;
		this.kemampuan = k;
	}
	
	public void displayDetailAnjing() {
		super.display();
		System.out.println("Memiliki warna bulu: " + warnaBulu);
		System.out.println("Memiliki Kemampuan: " + kemampuan);
	}
}
