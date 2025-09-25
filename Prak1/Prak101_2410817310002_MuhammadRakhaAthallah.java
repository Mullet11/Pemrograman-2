package Prak1;
import java.util.Scanner;

public class Prak101_2410817310002_MuhammadRakhaAthallah {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Masukkan Nama Lengkap: ");
		String namaLengkap = scan.nextLine();
		
		System.out.print("Masukkan Tempat Lahir: ");
		String tempatLahir = scan.nextLine();
		
		System.out.print("Masukkan Tanggal Lahir: ");
		int tanggalLahir = scan.nextInt();

		System.out.print("Masukkan Bulan Lahir: ");
		int bulanLahir = scan.nextInt();
		
		System.out.print("Masukkan Tahun Lahir: ");
		int tahunLahir = scan.nextInt();
		
		int tinggiBadan;
		double beratBadan;
		
		do {
			System.out.print("Masukkan Tinggi Badan: ");
			tinggiBadan = scan.nextInt();
			if (tinggiBadan <= 0) {
				System.out.println("Coba Lagi");
			}
		}while (tinggiBadan <=0);
		
		do {
			System.out.print("Masukkan Berat Badan: ");
			beratBadan = scan.nextDouble();
			if (beratBadan <= 0) {
				System.out.println("Coba Lagi");
			}
		}while (beratBadan <= 0);
		
		String[] namaBulanArray = {
				"januari", "Februari", "Maret", "April", "Mei", "Juni",
				"Juli", "Agustus", "September", "Oktober", "November", "Desember"
		};
		
		String namaBulan = namaBulanArray[bulanLahir - 1];
		
		System.out.println("Nama Lengkap " + namaLengkap + ", Lahir di " + tempatLahir + " Pada Tanggal " + tanggalLahir + " " + namaBulan + " " + tahunLahir);
		System.out.println("Tinggi Badan " + tinggiBadan + " cm dan Berat Badan " + beratBadan + " kilogram");
		
		scan.close();
	}

}
