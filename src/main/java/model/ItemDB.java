package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class ItemDB extends Item{

    /*public static List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String url = "jdbc:sqlite:mydatabase.db"; // Ditt databas-URL

        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT id, name, price FROM items");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");


                items.add(new Item(name, id, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }*/

    public static List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        Connection conn = null; // Initiera anslutningen

        try {
            conn = DBManager.getConnection(); // Försök att hämta en anslutning
            Statement stmt = conn.createStatement(); // Om anslutningen är null här, kommer detta att ge ett fel
            ResultSet rs = stmt.executeQuery("SELECT id, name, price FROM items");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                items.add(new Item(name, id, price));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Skriver ut eventuella SQL-fel
        } finally {
            DBManager.closeConnection(conn); // Stänger anslutningen
        }

        return items;
    }


    //Metod från Reine
    public static Collection searchItems(String group) throws SQLException {
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
    }

    private ItemDB(int id, String name) {
        super(id, name);
    }

    public static Item getItemById(int id, Controller controller) {
        Connection con = null; // Få en anslutning till databasen
        String dbPath = "C:/Theodors_grejor/Distribuerade system/Web_Shopping/mydatabase.db";
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        return false;
    }



}