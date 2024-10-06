<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.Item" %>
<%@ page import="model.User" %>
<%@ page import="model.User.UserType" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Controller Orders</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

<%
    User user = (User) request.getSession().getAttribute("user");
    User.UserType userType = user != null ? user.getUserType() : null;

    if (userType == UserType.staff || userType == UserType.admin) {
        List<Order> orders = (List<Order>) request.getAttribute("orders");
%>

<h2>All Orders</h2>

<%
    if (orders != null && !orders.isEmpty()) {
%>
<table>
    <thead>
    <tr>
        <th>Order ID</th>
        <th>Username</th>
        <th>Total Price</th>
        <th>Status</th>
        <th>Items in Order</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Order order : orders) {
    %>
    <tr>
        <td><%= order.getId() %></td>
        <td><%= order.getUser().getUserName() %></td>
        <td><%= order.getTotalPrice() %></td>
        <td><%= order.getStatus() %></td>
        <td>
            <table>
                <thead>
                <tr>
                    <th>Item ID</th>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Item item : order.getUser().getCart().getList()) {
                %>
                <tr>
                    <td><%= item.getId() %></td>
                    <td><%= item.getName() %></td>
                    <td><%= item.getStock() %></td>
                    <td><%= item.getPrice() %></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </td>
        <td>
            <form action="updateOrderStatus" method="post">
                <input type="hidden" name="orderId" value="<%= order.getId() %>" />
                <input type="hidden" name="newStatus" value="Packed" />
                <button type="submit">Pack</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
} else {
%>
<p>No orders found.</p>
<%
    }
} else {
%>
<p>Access Denied: You do not have permission to view this page.</p>
<%
    response.sendRedirect("login.jsp");
%>
<%
    }
%>

<form action="goToShopSite" method="get">
    <input type="submit" value="Go back to shop site">
</form>

</body>
</html>
