<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.*" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Shopping Cart</title>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");
%>
<h4>Logged in as <%= user.getUserName() %>, <%= user.getUserType() %></h4>
<h2>Your Shopping Cart</h2>

<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<p style="color: green;"><%= message %></p>
<%
    }

    String warning = (String) request.getAttribute("warning");
    if (warning != null) {
%>
<p style="color: red;"><%= warning %></p>
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

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Cart cart = user.getCart();
        List<Item> cartItems = cart != null ? cart.getList() : null;
        double totalPrice = 0.0;

        if (cartItems != null && !cartItems.isEmpty()) {
            for (Item item : cartItems) {
                totalPrice += item.getPrice() * item.getStock();
    %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %></td>
        <td>
            <form action="updateCart" method="post" style="display:inline;">
            <input type="hidden" name="itemId" value="<%= item.getId() %>">
            <button type="submit" name="operation" value="remove">-</button>
            <input type="text" name="quantity" value="<%= item.getStock() %>" readonly style="width: 30px; text-align: center;">
            <button type="submit" name="operation" value="add">+</button>
        </form>
        </td>
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
<form action="clearCart" method="post" style="margin-top: 20px;">
    <input type="submit" value="Clear Cart">
</form>
<form action="goToShopSite" method="get">
    <input type="submit" value="Go back to shop site">
</form>
</body>
</html>
