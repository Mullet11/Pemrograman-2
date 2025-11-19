package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import Model.Mahasiswa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller implements Initializable {

    @FXML
    private TableView<Mahasiswa> tableMahasiswa;

    @FXML
    private TableColumn<Mahasiswa, String> colNIM;

    @FXML
    private TableColumn<Mahasiswa, String> colNama;

    // ObservableList untuk menyimpan data mahasiswa
    private ObservableList<Mahasiswa> dataMahasiswa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Menghubungkan kolom tabel dengan atribut di class Mahasiswa
        // "nim" harus sama dengan variabel di Mahasiswa.java
        colNIM.setCellValueFactory(new PropertyValueFactory<>("nim"));
        
        // "nama" harus sama dengan variabel di Mahasiswa.java
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        
        // Memuat data ke tabel
        loadData();
    }
    
    private void loadData() {
        // Membuat ObservableList untuk menyimpan data
        dataMahasiswa = FXCollections.observableArrayList();
        
        // Menambahkan data
        dataMahasiswa.add(new Mahasiswa(1, "Muhamammad Rakha' Athallah", "2410817310002"));
        dataMahasiswa.add(new Mahasiswa(2, "Muhammad Azriel Akbar", "2410817110011"));
        dataMahasiswa.add(new Mahasiswa(3, "Muhammad dzul Fathi Ahyan", "2410817210011"));
        dataMahasiswa.add(new Mahasiswa(4, "Andre Cristian Nathanael", "2410817210006"));
        dataMahasiswa.add(new Mahasiswa(5, "Muhammad Ghazi Rakhmadi", "2410817310009"));
        dataMahasiswa.add(new Mahasiswa(6, "Achmad Reihan Alfaiz", "2410817210019"));
        dataMahasiswa.add(new Mahasiswa(7, "Putri Fatima Az'hara", "2410817120001"));
        dataMahasiswa.add(new Mahasiswa(8, "Muhammad Guntur Ricky Adhitya", "2410817310003"));
        dataMahasiswa.add(new Mahasiswa(9, "Anggraeni Dwi Zahra", "2410817220018"));
        dataMahasiswa.add(new Mahasiswa(10, "Rachel Wina yuda", "2410817220030"));
        
        // Mengisi tabel dengan data
        tableMahasiswa.setItems(dataMahasiswa);
    }
}