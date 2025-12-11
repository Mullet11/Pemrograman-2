package model;

public class Pelanggan {

    private int id;
    private String nama;
    private String telepon;
    private String email;

    public Pelanggan() {}

    public Pelanggan(int id, String nama, String telepon, String email) {
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.email = email;
    }
    
    @Override
    public String toString() {
        return nama + " (" + telepon + ")";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
