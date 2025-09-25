package Prak1;
import java.util.Scanner;

public class Prak102_2410817310002_MuhammadRakhaAthallah {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Masukkan Angka: ");
		int angkaAwal = scan.nextInt();
		
		int i = 0;
		int angkaSaatIni = angkaAwal;
		
		System.out.println("Output");
		while (i < 10) {
			if (angkaSaatIni % 5 == 0) {
				int hasil = (angkaSaatIni / 5) - 1;
				System.out.print(hasil);
			}else {
				System.out.print(angkaSaatIni);
			}
			if (i < 9) {
				System.out.print(", ");
			}
			angkaSaatIni++;
			i++;
		}
		System.out.println();
		scan.close();	
	}
}
