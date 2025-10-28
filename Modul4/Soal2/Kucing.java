package Modul4.Soal2;

public class Kucing extends HewanPeliharaan{
	private String warnaBulu;
	
	public Kucing(String n, String r, String w) {
		super(n,r);
		this.warnaBulu = w;
	}
	
	public void displayDetailKucing() {
		super.display();
		System.out.println("Memiliki warna bulu: " + warnaBulu);
	}
}
