import model.Controller;
import model.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        // Anslut till databasen
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Anslutning till SQLite-databasen lyckades!");

                // Skapa tabellen om den inte redan finns
                createItemsTable(conn);
                createUserTable(conn);
                createOrderTable(conn);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createItemsTable(Connection conn) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "stock INTEGER NOT NULL," +
                "itemGroup TEXT NOT NULL" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabellen 'items' skapades eller existerar redan.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createUserTable(Connection conn){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS user (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "userType TEXT NOT NULL" +
                ");";
        try (Statement stmt = conn.createStatement()){
            stmt.execute(createTableSQL);
            System.out.println("Tabellen 'user' skapades eller existerar redan");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void createOrderTable(Connection conn){
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            System.out.println("Kunde inte aktivera främmande nycklar: " + e.getMessage());
        }


        String createTableSQL = "CREATE TABLE IF NOT EXISTS orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "totalPrice REAL NOT NULL," +
                "status TEXT NOT NULL," +
                "FOREIGN KEY (username) REFERENCES user(username)" +
                ");";

        try (Statement stmt = conn.createStatement()){
            stmt.execute(createTableSQL);
            System.out.println("Tabellen 'orders' skapades eller existerar redan");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        String createOrderItemsTableSQL = "CREATE TABLE IF NOT EXISTS orderItems (" +
                "orderId INTEGER NOT NULL," +       // Referens till beställning
                "itemId INTEGER NOT NULL," +        // Referens till artikel
                "quantity INTEGER NOT NULL," +      // Antal av artikeln
                "PRIMARY KEY (orderId, itemId), " + // Unik kombination av orderId och itemId
                "FOREIGN KEY (orderId) REFERENCES orders(id)," +
                "FOREIGN KEY (itemId) REFERENCES items(id)" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createOrderItemsTableSQL);
            System.out.println("Tabellen 'orderItems' skapades eller existerar redan");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /*
    private static void insertTestData(Connection conn) {
        String insertSQL = "INSERT INTO items (name, price, item_group) VALUES " +
                "('Laptop', 999.99, 'Electronics'), " +
                "('Book', 29.99, 'Literature'), " +
                "('Phone', 499.99, 'Electronics');";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSQL);
            System.out.println("Testdata har lagts till i tabellen 'items'.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    */

    /*
    private static void fetchAndDisplayItems(Connection conn) {
        String selectSQL = "SELECT * FROM items;";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("Innehållet i tabellen 'items':");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String itemGroup = rs.getString("item_group");
                System.out.println("ID: " + id + ", Namn: " + name + ", Pris: " + price + ", Grupp: " + itemGroup);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     */
}
