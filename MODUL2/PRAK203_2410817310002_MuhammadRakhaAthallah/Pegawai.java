package PRAK203_2410817310002_MuhammadRakhaAthallah;

//public class Employee{
//pada baris diatas ini terdapat kesalahan karena nama class adalah 'Employee', sedangkan pada file Soal3Main.java, objek dibuat dari class 'Pegawai'. Nama class harus sama saat pembuatan objek.
public class Pegawai {  
	public String nama;
	//public char asal;
	//pada baris diatas ini terjadi error karena tipe data untuk asal adalah char, yang hanya bisa menampung satu karakter.
	//sedangkan di Soal3Main, variabel ini diisi dengan String "Kingdom of Orvel".
	public String asal;
	public String jabatan;
	public int umur;
	
	public String getNama() {
		return nama;
	}
	
	public String getAsal() {
		return asal;
	}
	
	//public void setJabatan() {
	//pada baris ini terjadi error karena metode setJabatan tidak memiliki parameter untuk menerima input.
	//sehingga variabel j tidak terdefinisi.
	public void setJabatan(String j) {
		this.jabatan = j;		
	}
}
