package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class ItemDB extends Item{

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

    private ItemDB(int id, String name) {
        super(id, name);
    }

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
                    items.add(new Item(name, id, price));  // Lägg till varje objekt i listan
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

            System.out.println("HelloKITTY");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Om vi får ett resultat, skapa ett Item-objekt
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    return new Item(name, id, price); // Returnera det skapade Item-objektet
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

    public static boolean addItem(String name, double price) {
        Connection con = DBManager.getConnection();
        String insertSQL = "INSERT INTO items (name, price) VALUES (?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Item successfully added!");
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
}