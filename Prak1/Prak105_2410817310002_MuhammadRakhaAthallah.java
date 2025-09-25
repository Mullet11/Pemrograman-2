package Prak1;

import java.util.Scanner;
import java.util.Locale;

public class Prak105_2410817310002_MuhammadRakhaAthallah {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);
		
		final double PI = 3.14;
		
		System.out.print("Masukkan jari-jari: ");
		double jariJari = scan.nextDouble();
		
		System.out.print("Masukkan Tinggi: ");
		double tinggi = scan.nextDouble();
		
		double volume = PI * jariJari * jariJari * tinggi;
		
		System.out.println("Hasil Hitung: ");
		System.out.printf("Volume tabung dengan jari-jari %.1f cm dan tinggi %.1f  adalah %.3f m3\n", jariJari, tinggi, volume);
		
		scan.close();
	}

}

