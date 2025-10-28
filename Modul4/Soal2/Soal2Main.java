package Modul4.Soal2;

import java.util.Scanner;

public class Soal2Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Pilih jenis hewan yang ingin diinputkan: ");
		System.out.println("1 = Kucing");
		System.out.println("2 = Anjing");
		System.out.println("Masukkan Pilihan: ");
		int pilihan = scan.nextInt();
		scan.nextLine();
		
		if (pilihan == 1) {
			System.out.print("Nama hewan peliharaan: ");
			String n = scan.nextLine();
			System.out.print("Ras: ");
			String r = scan.nextLine();
			System.out.print("Warna Bulu: ");
			String w = scan.nextLine();
			
			Kucing Kucing = new Kucing (n, r, w);
			Kucing.displayDetailKucing();
		} else if (pilihan == 2) {
			System.out.print("Nama hewan peliharaan: ");
			String n = scan.nextLine();
			System.out.print("Ras: ");
			String r = scan.nextLine();
			System.out.print("Warna Bulu: ");
			String w = scan.nextLine();
			System.out.print("Kemampuan: ");
			String kStr = scan.nextLine();
			String[] k = kStr.split(",\\s*");
			
			Anjing Anjing = new Anjing (n, r, w, k);
			Anjing.displayDetailAnjing();
		} else {
			System.out.println("Pilihan tidak valid");
		}
		scan.close();
	}

}
