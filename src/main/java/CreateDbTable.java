import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDbTable {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {

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
                "orderId INTEGER NOT NULL," +
                "itemId INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "PRIMARY KEY (orderId, itemId), " +
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

}
