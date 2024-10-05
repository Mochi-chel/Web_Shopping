package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDB {

    public static boolean addOrder(Order order) {
        boolean isAdded = false;


        //System.out.println("Im in addOrder method!");
        //System.out.println(order.toString());
        try (Connection con = DBManager.getConnection()) {
            con.setAutoCommit(false); // Ställ in auto-commit till false

            // SQL-fråga för att lägga till en order
            String insertSQL = "INSERT INTO orders (username, totalPrice, status) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, order.getUser().getUserName());
                pstmt.setDouble(2, order.getTotalPrice());
                pstmt.setString(3, order.getStatus());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int orderId = generatedKeys.getInt(1);
                            // Anropa addOrderItems med den befintliga anslutningen
                            addOrderItems(con, orderId, order.getUser().getCart().getList());
                            updateStock(con, order.getUser().getCart().getList());
                            isAdded = true;
                        }
                    }
                }
            } catch (SQLException e) {
                con.rollback(); // Återställ om något går fel
                System.out.println("Could not add order to DB: " + e.getMessage());
            }

            if (isAdded) {
                con.commit(); // Bekräfta transaktionen om allt gick bra
            }
        } catch (SQLException e) {
            System.out.println("Could not connect to DB: " + e.getMessage());
        }

        return isAdded;
    }

    private static void addOrderItems(Connection connection, int orderId, List<Item> cartItems) throws SQLException {
        String insertOrderItemSQL = "INSERT INTO orderItems (orderId, itemId, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertOrderItemSQL)) {
            for (Item item : cartItems) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, item.getId());
                pstmt.setInt(3, item.getStock());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    private static void updateStock(Connection connection, List<Item> cartItems) throws SQLException {
        String updateStockSQL = "UPDATE items SET stock = stock - ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateStockSQL)) {
            for (Item item : cartItems) {
                pstmt.setInt(1, item.getStock()); // Antal som ska dras bort
                pstmt.setInt(2, item.getId()); // Item ID
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }


    public static List<Order> getOrdersByUsername(String username) throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DBManager.getConnection()) {
            String selectOrdersSQL = "SELECT * FROM orders WHERE username = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(selectOrdersSQL)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int orderId = rs.getInt("id");
                        double totalPrice = rs.getDouble("totalPrice");
                        String status = rs.getString("status");

                        User user = new User(username, User.UserType.customer);

                        List<Item> orderItems = getOrderItems(orderId);

                        orders.add(new Order(orderId, totalPrice, status, user));
                    }
                }
            }
        }
        return orders;
    }

    private static List<Item> getOrderItems(int orderId) throws SQLException {
        List<Item> orderItems = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String selectOrderItemsSQL = "SELECT oi.itemId, i.name, oi.quantity, i.price " +
                    "FROM orderItems oi JOIN items i ON oi.itemId = i.id " +
                    "WHERE oi.orderId = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(selectOrderItemsSQL)) {
                pstmt.setInt(1, orderId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int itemId = rs.getInt("itemId");
                        String itemName = rs.getString("name");
                        int quantity = rs.getInt("quantity");
                        double price = rs.getDouble("price");

                        Item item = new Item(itemName, itemId, price, quantity, ""); // Anta att vi har en tom sträng för grupp

                        orderItems.add(item);
                    }
                }
            }
        }
        return orderItems;
    }
}
