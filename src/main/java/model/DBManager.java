package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    // Databasens URL (för SQLite)
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    // Metod för att skapa och returnera en anslutning
    public static Connection getConnection() {
        System.out.println("Hi!");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Anslutning till SQLite-databasen lyckades!");
        } catch (SQLException e) {
            System.out.println("Kunde inte ansluta till databasen: " + e.getMessage());
        }
        return conn;
    }

    // Metod för att stänga anslutningen
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Anslutningen stängdes.");
            }
        } catch (SQLException e) {
            System.out.println("Fel vid stängning av anslutningen: " + e.getMessage());
        }
    }
}
