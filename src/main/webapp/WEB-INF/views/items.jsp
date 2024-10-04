<%@ page import="model.Item" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Item List</title>
</head>
<body>
<h1>Items</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <%
        // Hämta itemList från request
        List<Item> items = (List<Item>) request.getAttribute("itemList");

        if (items != null && !items.isEmpty()) {
            // Iterera genom listan och skriv ut varje item
            for (Item item : items) {
    %>
    <tr>
        <td><%= item.getId() %></td>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3">Empty</td> <!-- Visa 'Empty' om listan är tom -->
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
