package Prak1;
import java.util.Scanner;

public class Prak104_2410817310002_MuhammadRakhaAthallah {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Pilihan Abu: ");
		String pilihanAbuStr = scan.nextLine().replace(" ", "");
		
		System.out.print("Pilihan Bagas: ");
		String pilihanBagasStr = scan.nextLine().replace(" ", "");
		
		int poinAbu = 0;
		int poinBagas = 0;
		
		for(int i = 0; i < 3; i++) {
			char abu = pilihanAbuStr.charAt(i);
			char bagas = pilihanBagasStr.charAt(i);
			
			if (abu == bagas) {
				
			}else if ((abu == 'B' && bagas == 'G') || 
					(abu == 'G' && bagas == 'K') || 
					(abu == 'K' && bagas == 'B')) {
				poinAbu++;
			}else {
				poinBagas++;
			}
		}
		System.out.println("Pemenang: ");
		if(poinAbu > poinBagas) {
			System.out.println("Abu");
		}else if(poinBagas > poinAbu) {
			System.out.println("Bagas");
		}else {
			System.out.println("Seri");
		}
		scan.close();
	}

}
