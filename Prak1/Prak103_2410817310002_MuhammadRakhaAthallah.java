package Prak1;
import java.util.Scanner;

public class Prak103_2410817310002_MuhammadRakhaAthallah {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Masukkan dua angka: ");
		int n = scan.nextInt();
		int bilanganAwal = scan.nextInt();
		
		int count = 0;
		int angkaSaatIni = bilanganAwal;
		
		System.out.println("HASIL: ");
		do {
			if (angkaSaatIni % 2 != 0) {
				System.out.print(angkaSaatIni);
				count++;
				if (count < n) {
					System.out.print(", ");
				}
			}
			angkaSaatIni++;
		}while (count < n);

		System.out.println();
		scan.close();
	}
}
