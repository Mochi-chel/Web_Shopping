package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class ItemDB/* extends Item*/{

    //private static String DB_URL = "jdbc:sqlite:C:/Theodors_grejor/Distribuerade system/Web_Shopping/mydatabase.db";

    //Metod från Reine
    /*public static Collection searchItems(String group) throws SQLException {
        Vector v = new Vector();
        Connection con = DBManager.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select item_id, name from Item where item_group = "+group);
        while (rs.next()) {
            int i = rs.getInt("item_id");
            String name = rs.getString("name");
            v.addElement(new ItemDB(i, name));
        }
        return v;
    }*/

    /*private ItemDB(int id, String name) {
        super(id, name);
    }*/

    /* Gets all items from data
     * */
    public static List<Item> getAllItems() {
        Connection con = null;  // Skapa en Connection
        List<Item> items = new ArrayList<>();  // Lista för att lagra alla hämtade items
        String query = "SELECT * FROM items";  // SQL-fråga

        try {
            con = DBManager.getConnection();  // Hämta anslutning
            try (PreparedStatement pstmt = con.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                // Iterera över resultatet och skapa Item-objekt för varje rad
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");
                    String itemGroup = rs.getString("itemGroup");
                    items.add(new Item(name, id, price, stock, itemGroup));  // Lägg till varje objekt i listan
                }
            }
        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av alla items: " + e.getMessage());
            throw new RuntimeException("Fel vid hämtning av alla items", e);
        } finally {
            DBManager.closeConnection(con);  // Stäng anslutningen efter användning
        }

        return items;
    }


    public static Item getItemById(int id) {
        Connection con = null; // Få en anslutning till databasen
        con = DBManager.getConnection();

        String query = "SELECT * FROM items WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id); // Ställ in id som parameter

            //System.out.println("HelloKITTY");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Om vi får ett resultat, skapa ett Item-objekt
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");
                    String itemGroup = rs.getString("itemGroup");
                    return new Item(name, id, price, stock, itemGroup); // Returnera det skapade Item-objektet
                } else {
                    // Om inget resultat hittas
                    System.out.println("Item med ID " + id + " hittades inte.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }finally {
            DBManager.closeConnection(con);  // Stäng anslutningen efter användning
        }
    }

    public static boolean addItem(String name, double price, int stock, String group) {
        Connection con = DBManager.getConnection();
        String insertSQL = "INSERT INTO items (name, price, stock, itemGroup) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, stock);
            pstmt.setString(4, group);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                //System.out.println("Item successfully added!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Could not add user to DB!");
        }
        finally {
            DBManager.closeConnection(con);  // Stäng anslutningen efter användning
        }
        return false;
    }


    public static boolean updateStock(int itemId, int quantityJustering) {
        Connection con = null;
        String updateSQL = "UPDATE items SET stock = stock + ? WHERE id = ? AND stock + ? >= 0"; // Se till att stock aldrig blir negativt

        try {
            con = DBManager.getConnection(); // Hämta anslutning till databasen
            try (PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
                pstmt.setInt(1, quantityJustering); // Lägga till (eller ta bort) lagret
                pstmt.setInt(2, itemId);            // Ställ in itemId
                pstmt.setInt(3, quantityJustering); // För att försäkra att stock + justering >= 0

                int rowsUpdated = pstmt.executeUpdate(); // Kör uppdateringen
                return rowsUpdated > 0; // Om minst en rad uppdaterades, returnera true
            }
        } catch (SQLException e) {
            System.err.println("Fel vid uppdatering av lager: " + e.getMessage());
            return false;
        } finally {
            DBManager.closeConnection(con); // Stäng anslutningen efter användning
        }
    }

    public static boolean updateItemGroup(int itemId, String newGroup) {
        Connection con = null;
        String updateSQL = "UPDATE items SET itemGroup = ? WHERE id = ?";

        try {
            con = DBManager.getConnection(); // Hämta anslutning till databasen
            try (PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
                pstmt.setString(1, newGroup); // Sätt den nya gruppen från formuläret
                pstmt.setInt(2, itemId);      // Sätt itemId

                int rowsUpdated = pstmt.executeUpdate(); // Kör uppdateringen
                return rowsUpdated > 0; // Om minst en rad uppdaterades, returnera true
            }
        } catch (SQLException e) {
            System.err.println("Fel vid uppdatering av item group: " + e.getMessage());
            return false;
        } finally {
            DBManager.closeConnection(con); // Stäng anslutningen efter användning
        }
    }
}