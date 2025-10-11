package Modul3.Soal1;
import java.util.Random;
import java.util.LinkedList;

 public class Dadu {
	private int nilai;
	
	public Dadu() {
		acakNilai();
	}
	
	private void acakNilai() {
		this.nilai = new Random().nextInt(6) + 1;
	}

	public int getNilai() {
		return this.nilai;
	}
	public void setNilai(int nilai) {
		this.nilai = nilai;
	}
	
	public static void mulaiPermainan(int jumlahDadu) {
		LinkedList<Dadu> semuaDadu = new LinkedList<>();
		
		for(int i = 0; i < jumlahDadu; i++) {
			semuaDadu.add(new Dadu());
		}
		
		int totalNilai = 0;
		System.out.println("Hasil Lemparan Dadu ");
		
		for (int i = 0; i < semuaDadu.size(); i++) {
			Dadu dadu = semuaDadu.get(i);
			int nilaiDadu = dadu.getNilai();
			System.out.println("Dadu ke- " + (i + 1) + " bernilai " + nilaiDadu);
			totalNilai += nilaiDadu;
		}
		System.out.println("Total dadu keselurahan " + totalNilai);
	}
}
