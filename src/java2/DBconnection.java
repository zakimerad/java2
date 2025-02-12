package java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String NAME = "gestionetd";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + NAME + "?serverTimezone=UTC";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
            return null;
        }
    }
}
