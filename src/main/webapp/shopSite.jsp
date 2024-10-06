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
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");
    User.UserType userType = user.getUserType();
    if(user.getUserType() == null){
        response.sendRedirect("index.jsp");
        return;
    }
%>
<h4>Logged in as <%= user.getUserName() %>, <%= user.getUserType() %></h4>
<h1>Items List</h1>

<%


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
        <th>Stock</th>
        <th>Item Group</th>
        <th>Button</th>
    </tr>
    </thead>
    <tbody>
    <%

        List<Item> items = (List<Item>) request.getAttribute("items");

        for (Item item : items) {
    %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getStock() %></td>
        <td><%= item.getGroup() %></td>
        <td>
            <form action="addToCart" method="post">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="submit" value="Add to Cart">
            </form>

            <% if(userType.equals(UserType.admin)){
                %>
            <% String message = (String) request.getAttribute("message"); %>

            <form action="updateStock" method="post" style="display:inline;">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="hidden" name="operation" value="add">
                <input type="submit" value="+">
            </form>

            <form action="updateStock" method="post" style="display:inline;">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="hidden" name="operation" value="remove">
                <input type="submit" value="-">
            </form>


            <form action="updateItemGroup" method="post" style="display:inline;">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="text" name="newGroup" placeholder="Enter new group" value="<%= item.getGroup() %>">
                <input type="submit" value="Update Group">
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
        if (userType != null) {
            if (userType == UserType.staff || userType == UserType.admin) {
    %>
    <form action="viewOrders" method="get">
        <input type="submit" value="View Orders">
    </form>
    <%
            }
        }
    %>

    <%
        if (userType.equals(UserType.admin)) {
    %>
    <form action="addItem" method="get">
        <input type="submit" value="Add New Item">
    </form>
    <form action="handleUsers" method="get">
        <input type="submit" value="Handle users">
    </form>
    <%
        }
    %>

    </tbody>
</table>

<form action="cart" method="get">
    <input type="submit" value="Go to Cart">
</form>

<form action="logout" method="get">
    <input type="submit" value="Logout">
</form>

</body>
</html>
