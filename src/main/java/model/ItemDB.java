package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
/**
 * The ItemDB class provides methods for interacting with the database related to Item objects.
 * It includes functionalities to retrieve, add, and update items in the database.
 */
public class ItemDB{
    /**
     * Retrieves all items from the database.
     *
     * @return a List of Item objects representing all items in the database
     * @throws RuntimeException if there is an error during database access
     */
    public static List<Item> getAllItems() {
        Connection con = null;  // Skapa en Connection
        List<Item> items = new ArrayList<>();  // Lista för att lagra alla hämtade items
        String query = "SELECT * FROM items";  // SQL-fråga

        try {
            con = DBManager.getConnection();
            try (PreparedStatement pstmt = con.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                // Iterera över resultatet och skapa Item-objekt för varje rad
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");
                    String itemGroup = rs.getString("itemGroup");
                    items.add(new Item(name, id, price, stock, itemGroup));
                }
            }
        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av alla items: " + e.getMessage());
            throw new RuntimeException("Fel vid hämtning av alla items", e);
        } finally {
            DBManager.closeConnection(con);
        }

        return items;
    }

    /**
     * Retrieves a specific item from the database based on its ID.
     *
     * @param id the ID of the item to retrieve
     * @return the Item object with the specified ID, or null if not found
     * @throws RuntimeException if there is an error during database access
     */
    public static Item getItemById(int id) {
        Connection con = null;
        con = DBManager.getConnection();

        String query = "SELECT * FROM items WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id); // Ställ in id som parameter

            //System.out.println("HelloKITTY");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");
                    String itemGroup = rs.getString("itemGroup");
                    return new Item(name, id, price, stock, itemGroup);
                } else {
                    System.out.println("Item med ID " + id + " hittades inte.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }finally {
            DBManager.closeConnection(con);
        }
    }
    /**
     * Adds a new item to the database.
     *
     * @param name   the name of the item
     * @param price  the price of the item
     * @param stock  the stock quantity of the item
     * @param group  the group/category of the item
     * @return true if the item was added successfully, false otherwise
     */
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
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Could not add user to DB!");
        }
        finally {
            DBManager.closeConnection(con);
        }
        return false;
    }

    /**
     * Updates the stock quantity of a specific item in the database.
     *
     * @param itemId          the ID of the item to update
     * @param quantityJustering the adjustment to be made to the stock (can be negative to remove stock)
     * @return true if the stock was updated successfully, false otherwise
     */
    public static boolean updateStock(int itemId, int quantityJustering) {
        Connection con = null;
        String updateSQL = "UPDATE items SET stock = stock + ? WHERE id = ? AND stock + ? >= 0";

        try {
            con = DBManager.getConnection();
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
            DBManager.closeConnection(con);
        }
    }
    /**
     * Updates the group/category of a specific item in the database.
     *
     * @param itemId  the ID of the item to update
     * @param newGroup the new group/category to assign to the item
     * @return true if the item group was updated successfully, false otherwise
     */
    public static boolean updateItemGroup(int itemId, String newGroup) {
        Connection con = null;
        String updateSQL = "UPDATE items SET itemGroup = ? WHERE id = ?";

        try {
            con = DBManager.getConnection();
            try (PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
                pstmt.setString(1, newGroup);
                pstmt.setInt(2, itemId);

                int rowsUpdated = pstmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            System.err.println("Fel vid uppdatering av item group: " + e.getMessage());
            return false;
        } finally {
            DBManager.closeConnection(con);
        }
    }
}