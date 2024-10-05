<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Items List</title>
    <link rel="stylesheet" href="styles.css"> <!-- Lägg till eventuell CSS här -->
</head>
<body>
<h1>Items List</h1>

<table border="1">
    <thead>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Item Group</th>
        <th>Button</th>
    </tr>
    </thead>
    <tbody>
    <%
        // Hämta listan med items från förfrågan
        List<Item> items = (List<Item>) request.getAttribute("items");

        // Iterera över items och visa namn och pris
        for (Item item : items) {
    %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getStock() %></td>
        <td><%= item.getGroup() %></td>
        <td>
            <form action="addToCart" method="post">
                <input type="hidden" name="itemId" value="<%= item.getId() %>"> <!-- Skickar item-id -->
                <input type="submit" value="Add to Cart"> <!-- Knapp för att lägga till i kundvagn -->
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<form action="cart" method="get">
    <input type="submit" value="Go to Cart">
</form>

</body>
</html>
