package Modul3.Soal1;
import java.util.Scanner;

public class Soal1Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Masukkan Jumlah Dadu: ");
		int jumlahDadu = input.nextInt();
		
		if(jumlahDadu <= 0) {
			System.out.println("Error: Jumlah dadu harus lebih dari 0.");
		}else {
			Dadu.mulaiPermainan(jumlahDadu);
		}
		input.close();
	}
}
