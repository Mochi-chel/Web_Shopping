<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%@ page import="model.User" %>
<%@ page import="model.User.UserType" %>
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

        User user = (User) request.getSession().getAttribute("user");
        User.UserType userType = user.getUserType();
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

            <% if(userType.equals(UserType.admin)){
                %>
            <% String message = (String) request.getAttribute("message"); %>

            <form action="updateStock" method="post" style="display:inline;">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="hidden" name="operation" value="add"> <!-- Indikerar att det är ett "plus" -->
                <input type="submit" value="+">
            </form>

            <form action="updateStock" method="post" style="display:inline;">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="hidden" name="operation" value="remove"> <!-- Indikerar att det är ett "minus" -->
                <input type="submit" value="-">
            </form>


            <%
            }
            %>
        </td>
    </tr>
    <%
        }
    %>

    <%
        // Visa knappar baserat på användartyp
        if (userType != null) {
            if (userType == UserType.staff || userType == UserType.admin) {
    %>
    <form action="viewOrders" method="get">
        <input type="submit" value="View Order">
    </form>
    <%
            }
        }
    %>

    </tbody>
</table>

<form action="cart" method="get">
    <input type="submit" value="Go to Cart">
</form>

</body>
</html>
