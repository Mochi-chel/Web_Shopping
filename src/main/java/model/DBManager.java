package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private static final String DB_URL = "jdbc:sqlite:C:/Theodors_grejor/Distribuerade system/Web_Shopping/mydatabase.db";
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            //System.out.println("Anslutning till SQLite-databasen lyckades!");
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA busy_timeout = 5000;"); // Timeout i millisekunder (5 sek)
            }
        } catch (SQLException e) {
            System.out.println("Kunde inte ansluta till databasen: " + e.getMessage());
        }
        return conn;
    }
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                //System.out.println("Anslutningen stängdes.");
            }
        } catch (SQLException e) {
            System.out.println("Fel vid stängning av anslutningen: " + e.getMessage());
        }
    }


}
