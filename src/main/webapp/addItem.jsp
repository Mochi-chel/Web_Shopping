<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Item</title>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");
    User.UserType userType = user.getUserType();
%>
<h4>Logged in as <%= user.getUserName() %>, <%= user.getUserType() %></h4>
<h1>Add New Item</h1>

<% if (request.getAttribute("errorMessage") != null) { %>
<div style="color: #ff0000;">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>

<form action="addItem" method="post">
    <label for="name">Item Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" step="0.01" min="0" required><br>

    <label for="stock">Stock:</label>
    <input type="number" id="stock" name="stock" required><br>

    <label for="group">Item Group:</label>
    <input type="text" id="group" name="group" required><br>

    <input type="submit" value="Add Item">
</form>

<form action="goToShopSite" method="get">
    <input type="submit" value="Go back to shop site">
</form>

</body>
</html>
