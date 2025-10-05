package PRAK203_2410817310002_MuhammadRakhaAthallah;

public class Soal3Main {

	public static void main(String[] args) {
		Pegawai p1 = new Pegawai();
		//p1.nama = "Roi"
		//Kesalahan pada baris diatas adalah tidak diakhiri titik koma (;).
		p1.nama = "Roi";
		p1.asal = "Kingdom of Orvel";
		//p1.setJabatan("Assasin");
		//baris diatas ini sebelumnya error karena metode setJabatan() di kelas pegawai tidak memiliki parameter.
		p1.setJabatan("Assasin");
		p1.umur = 17;
		//menambahkan inisialisasi umur agar sesuai dengan output yang diminta. Karena variabel umur belum diberi nilai.
		
		System.out.println("Nama: " + p1.getNama());
		//mengubah String dari "nama pegawai" menjadi "nama" agar sesuai output di soal.
		System.out.println("Asal: " + p1.getAsal());
		System.out.println("Jabatan: " + p1.jabatan);
		System.out.println("Umur: " + p1.umur + " tahun");
		//menambahkan " tahun" pada output umur agar sesuai output di soal.
	}

}
