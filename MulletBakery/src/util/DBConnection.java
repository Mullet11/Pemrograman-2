package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/mullet_bakery?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String USER = "root";
    private static final String PASS = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("MySQL Driver gagal dimuat: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Opsional untuk transaksi manual (future use)
    public static Connection getTransactionConnection() throws Exception {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
        return conn;
    }
}
