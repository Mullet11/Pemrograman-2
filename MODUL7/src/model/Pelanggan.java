package model;

import javafx.beans.property.*;

public class Pelanggan {
	private final IntegerProperty id;
	private final StringProperty nama;
	private final StringProperty email;
	private final StringProperty telepon; 
	
	// Konstruktor untuk data baru (ID yang belum ada)
	public Pelanggan(String nama, String email, String telepon) {
		this(0, nama, email, telepon);
	}
	
	// Konstruktor lengkap untuk data dari database 
	public Pelanggan(int id, String nama, String email, String telepon) {
		this.id = new SimpleIntegerProperty(id);
		this.nama = new SimpleStringProperty(nama);
		this.email = new SimpleStringProperty(email);
		this.telepon = new SimpleStringProperty(telepon); 
	}
	
	// Getters
	public int getId() {
		return id.get();
	}
	
	public String getNama() {
		return nama.get();
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public String getTelepon() {
		return telepon.get();
	}
	
	// Setters
	public void setId(int id) {
		this.id.set(id);
	}
	
	public void setNama(String nama) {
		this.nama.set(nama);
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}
	
	public void setTelepon(String telepon) { 
		this.telepon.set(telepon);
	}
	
	// Properties untuk TableView
	public IntegerProperty idProperty() {
		return id;
	}
	
	public StringProperty namaProperty() {
		return nama;
	}
	
	public StringProperty emailProperty() {
		return email;
	}
	
	public StringProperty teleponProperty() { 
		return telepon;
	}
	
	@Override
	public String toString() {
		// Format: Nama - Telepon (untuk ComboBox display)
		return nama.get() + " - " + telepon.get();
	}
}