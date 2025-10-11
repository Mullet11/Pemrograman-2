package Modul3.Soal2;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.InputMismatchException;

public class Soal2main {
	/*
	 ini gunanya buat validasi tanggal, bulan lwn tahun yang dibeikan logis kah kada
	 return true tuh jika tanggal valid, klo false kebalikannya
	 */
	public boolean isValidDate(int tanggal, int bulan, int tahun) {
		if (bulan < 1 || bulan > 12 || tanggal < 1 || tahun <= 0 || tahun > 2025) { // Ditambahkan validasi tahun > 2025
			return false;
		}

		int maxDays;
		if (bulan == 2) {
			// nah klo ini meriksa tahun kabisat jadinya menentukan jumlah hari di bulan februari
			boolean isLeap = (tahun % 4 == 0 && tahun % 100 != 0) || (tahun % 400 == 0);
			maxDays = isLeap ? 29 : 28;
		} else if (bulan == 4 || bulan == 6 || bulan == 9 || bulan == 11) {
			maxDays = 30;
		} else {
			maxDays = 31;
		}

		return tanggal <= maxDays;
	}

	public static void main(String[] args) {
		Soal2main main = new Soal2main();
		Scanner input = new Scanner(System.in);
		LinkedList<Negara> daftarNegara = new LinkedList<>();

		int jumlahNegara = 0;
		// ini buat validasi input jumlah negara
		while (true) {
			try {
				System.out.print("Masukkan jumlah negara: ");
				jumlahNegara = input.nextInt();
				if (jumlahNegara > 0) {
					input.nextLine(); // ini buat membersihkan karakter newline
					break; // ini buat keluar dari loop jika input user valid
				} else {
					System.out.println("Jumlah negara harus lebih dari 0. Silahkan coba lagi.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Input tidak valid. Harap masukkan sebuah angka.");
				input.next(); // nah ini buat membersihkan input yang salah dari scanner
			}
		}

		//ini loop buat mengumpulkan info negara
		for (int i = 0; i < jumlahNegara; i++) {
			System.out.println("\nMasukkan data untuk Negara ke-" + (i + 1));
			System.out.print("Nama Negara: ");
			String nama = input.nextLine();
			System.out.print("Jenis Kepemimpinan: ");
			String jenisKepemimpinan = input.nextLine();
			System.out.print("Nama Pemimpin: ");
			String namaPemimpin = input.nextLine();

			if (jenisKepemimpinan.equalsIgnoreCase("monarki")) {
				daftarNegara.add(new Negara(nama, jenisKepemimpinan, namaPemimpin));
			} else {
				int tanggal = 0, bulan = 0, tahun = 0;

				//nah dibawah ini validasi input tanggal, bulan lawan tahun
				while (true) {
					try {
						System.out.print("Tanggal Kemerdekaan: ");
						tanggal = input.nextInt();
						System.out.print("Bulan Kemerdekaan: ");
						bulan = input.nextInt();
						System.out.print("Tahun Kemerdekaan: ");
						tahun = input.nextInt();
						input.nextLine();

						if (main.isValidDate(tanggal, bulan, tahun)) {
							break;
						} else {
							System.out.println("Tanggal/Bulan/Tahun tidak valid (Tahun tidak boleh > 2025). Silakan masukkan kembali.");
						}
					} catch (InputMismatchException e) {
						System.out.println("Input tanggal, bulan, dan tahun harus berupa angka. Silakan masukkan kembali.");
						input.nextLine();
					}
				}
				daftarNegara.add(new Negara(nama, jenisKepemimpinan, namaPemimpin, tanggal, bulan, tahun));
			}
		}
		for (Negara negara : daftarNegara) {
			negara.info();
		}
		input.close();
	}
}