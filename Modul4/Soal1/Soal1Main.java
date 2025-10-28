package Modul4.Soal1;
import java.util.Scanner;

public class Soal1Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Nama Hewan Peliharaan: ");
		String n = scan.nextLine();
		
		System.out.print("ras: ");
		String r = scan.nextLine();
		
		HewanPeliharaan a = new HewanPeliharaan(n, r);
		a.display();
		
		scan.close();
	}
}
