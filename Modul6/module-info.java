module Modul6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    
    // Membuka akses package application ke JavaFX
    opens application to javafx.graphics, javafx.fxml;
    
    // Membuka akses package Controller ke JavaFX FXML
    opens Controller to javafx.fxml;
    
    // PENTING: Membuka Model agar FXML bisa membaca/menulis data ke class Mahasiswa
    opens Model to javafx.base, javafx.fxml;
}