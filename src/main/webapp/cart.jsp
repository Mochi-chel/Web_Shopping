<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Shopping Cart</title>
    <link rel="stylesheet" href="styles.css"> <!-- Lägg till eventuell CSS här -->
</head>
<body>
<h2>Your Shopping Cart</h2>

<%
    // Visa meddelande om det finns något (t.ex. efter att ordern lagts in)
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<p style="color: green;"><%= message %></p>
<%
    }
%>


<table border="1">
    <thead>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Amount of Items</th>
    </tr>
    </thead>
    <tbody>
    <%
        // Hämta kundvagnen från förfrågan
        List<Item> cartItems = (List<Item>) request.getAttribute("cartItems");
        Object totalPriceObj = request.getAttribute("totalPrice");
        double totalPrice = 0.0;

        if (totalPriceObj != null && totalPriceObj instanceof Double) {
            totalPrice = (Double) totalPriceObj;
        }

        if (cartItems != null && !cartItems.isEmpty()) {
            // Visa alla produkter i kundvagnen
            for (Item item : cartItems) {
    %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getStock() %></td> <!-- Antal eller lagerstatus -->
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3">Your cart is empty.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<h3>Total Price: <%= totalPrice %></h3>

<form action="sendOrder" method="post">
    <input type="submit" value="Send Order">
</form>
</body>
</html>
