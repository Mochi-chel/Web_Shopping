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
    User.UserType userType = user.getUserType();
%>
<h4>Logged in as <%= user.getUserName() %>, <%= user.getUserType() %></h4>
<h1>Manage Users</h1>

<!-- Här kan du lägga till logik för att visa och hantera användare -->

<!-- Exempel på en enkel lista över användare -->
<table border="1">
    <thead>
    <tr>
        <th>User Name</th>
        <th>User Type</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <!-- Här kan du iterera över en lista med användare och visa dem -->
    <%
        List<User> users = (List<User>) request.getAttribute("users"); // hämta användare
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
                <input type="hidden" name="userName" value="<%= usr.getUserName() %>"> <!-- Skicka användarnamnet -->
                <input type="submit" value="Update">
            </form>
        </td>
        <td>
            <form action="deleteUser" method="post" style="display:inline;">
                <input type="hidden" name="userName" value="<%= usr.getUserName() %>"> <!-- Skicka användarnamnet -->
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
    <input type="submit" value="Go back to shop site"> <!-- Knapp för att lägga till i kundvagn -->
</form>

</body>
</html>
