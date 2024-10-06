<%@ page import="model.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Handle Users</title>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");
%>
<h4>Logged in as <%= user.getUserName() %>, <%= user.getUserType() %></h4>
<h1>Manage Users</h1>

<table border="1">
    <thead>
    <tr>
        <th>User Name</th>
        <th>User Type</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        for (User usr : users) {
    %>
    <tr>
        <td> <%= usr.getUserName() %> </td>
        <td> <%= usr.getUserType() %> </td>
        <td>
            <form action="updateUserType" method="post" style="display:inline;">
                <select name="userType">
                    <option value="customer" <%= usr.getUserType() == User.UserType.customer ? "selected" : "" %>>Customer</option>
                    <option value="staff" <%= usr.getUserType() == User.UserType.staff ? "selected" : "" %>>Staff</option>
                    <option value="admin" <%= usr.getUserType() == User.UserType.admin ? "selected" : "" %>>Admin</option>
                </select>
                <input type="hidden" name="userName" value="<%= usr.getUserName() %>">
                <input type="submit" value="Update">
            </form>
        </td>
        <td>
            <form action="deleteUser" method="post" style="display:inline;">
                <input type="hidden" name="userName" value="<%= usr.getUserName() %>">
                <input type="submit" value="Delete">
            </form>
        </td>
    </tr>
    <%
         }
    %>
    </tbody>
</table>

<% if (request.getAttribute("errorMessage") != null) { %>
<div style="color: red;">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>

<form action="goToShopSite" method="get">
    <input type="submit" value="Go back to shop site">
</form>

</body>
</html>
