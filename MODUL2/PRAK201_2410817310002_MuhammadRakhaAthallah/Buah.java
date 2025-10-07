package PRAK201_2410817310002_MuhammadRakhaAthallah;

public class Buah {
	String namaBuah;
	double Berat;
	double harga;
	double jumlahBeli;
	
	public Buah(String namaBuah, double berat, double harga, double jumlahBeli) {
		this.namaBuah = namaBuah;
		this.Berat = berat;
		this.harga = harga;
		this.jumlahBeli = jumlahBeli;
	}
	
	public void info() {
		double hargaPerKg = this.harga / this.Berat;
		double hargaSebelumDiskon = this.jumlahBeli * hargaPerKg;
		int kelipatanDiskon = (int) (this.jumlahBeli / 4);
		double totalDiskon = kelipatanDiskon * (this.harga * 0.08);
		double hargaSetelahDiskon = hargaSebelumDiskon - totalDiskon;
		
		System.out.println("Nama Buah: " + this.namaBuah);
        System.out.println("Berat: " + this.Berat);
        System.out.println("Harga: " + this.harga);
        System.out.println("Jumlah Beli: " + this.jumlahBeli + "kg");
        System.out.printf(Locale.US,"Harga Sebelum Diskon: Rp%.2f\n", hargaSebelumDiskon);
        System.out.printf(Locale.US,"Total Diskon: Rp%.2f\n", totalDiskon);
        System.out.printf(Locale.US,"Harga Setelah Diskon: Rp%.2f\n", hargaSetelahDiskon);
        System.out.println();
	}
}

