package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * The OrderDB class provides methods for managing orders in the database.
 * It includes functionalities to add new orders, retrieve existing orders,
 * update order statuses, and handle related order items.
 */
public class OrderDB {

    /**
     * Adds a new order to the database.
     *
     * @param order the Order object to be added
     * @return true if the order was added successfully, false otherwise
     */
    public static boolean addOrder(Order order) {
        boolean isAdded = false;

        try (Connection con = DBManager.getConnection()) {
            con.setAutoCommit(false);

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

                            addOrderItems(con, orderId, order.getUser().getCart().getList());
                            updateStock(con, order.getUser().getCart().getList());
                            isAdded = true;
                        }
                    }
                }
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Could not add order to DB: " + e.getMessage());
            }

            if (isAdded) {
                con.commit();
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
                pstmt.setInt(1, item.getStock());
                pstmt.setInt(2, item.getId());
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

                        Item item = new Item(itemName, itemId, price, quantity, "");

                        orderItems.add(item);
                    }
                }
            }
        }
        return orderItems;
    }
    /**
     * Retrieves all orders from the database.
     *
     * @return a List of Order objects representing all orders in the database
     * @throws SQLException if a database access error occurs
     */
    public static List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DBManager.getConnection()) {
            String selectOrdersSQL = "SELECT * FROM orders";
            try (PreparedStatement pstmt = connection.prepareStatement(selectOrdersSQL)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int orderId = rs.getInt("id");
                        double totalPrice = rs.getDouble("totalPrice");
                        String status = rs.getString("status");
                        String username = rs.getString("username");

                        User user = new User(username, User.UserType.customer);

                        Cart cart = new Cart();
                        List<Item> orderItems = getOrderItems(orderId);
                        for (Item item : orderItems) {
                            cart.addItem(item);
                        }
                        user.setCart(cart);

                        Order order = new Order(orderId, totalPrice, status, user);
                        orders.add(order);
                    }
                }
            }
        }
        return orders;
    }

    public static List<Order> getAllOrdersAAAAA() throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DBManager.getConnection()) {
            String selectOrdersSQL = "SELECT * FROM orders";
            /*
            Häma alla orders
            Häma alla orderItems
            Plocka ut order (låt os säga oID)
            skapa user med: username (från order), userType (hämta från DB), cart (se nedan för hur cart sa fixas)
                Kolla på informationen vi fic från orderItems.
                SELECT OrderItems WHERE orderId WHERE (oID)
                gå igenom allt som fås härifrån, det bör vara alla items kollade till en viss order
                    få information om item genom att:
                        SELECT item WHERE id = (id som fås från orderItems tabellen)
                        all info utom quantity fås från item tabellen i DB.
                        new Item(id, name, price, "quantity" (av det vi får), group)

             */


            try (PreparedStatement pstmt = connection.prepareStatement(selectOrdersSQL)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int orderId = rs.getInt("id");
                        double totalPrice = rs.getDouble("totalPrice");
                        String status = rs.getString("status");
                        String username = rs.getString("username");

                        User user = new User(username, User.UserType.customer); // Anta att alla är kunder

                        // Hämta alla orderartiklar kopplade till den aktuella ordern
                        List<Item> orderItems = getOrderItems(orderId);
                        Cart cart = new Cart();

                        //Lägger orderItems i UserCart

                        Order order = new Order(orderId, totalPrice, status, user);
                        orders.add(order);
                    }
                }
            }
        }
        return orders;
    }

    public static boolean updateOrderStatus(int orderId, String newStatus) {
        boolean isUpdated = false;

        String updateSQL = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, orderId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            System.out.println("Could not update order status: " + e.getMessage());
        }

        return isUpdated;
    }
}
