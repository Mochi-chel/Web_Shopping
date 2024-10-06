<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrera dig</title>
</head>
<body>
<h1>Registrera dig</h1>

<% String message = (String) request.getAttribute("message"); %>
<% Boolean registerSuccess = (Boolean) request.getAttribute("registerSuccess"); %>

<% if (message != null) { %>
<p style="color: <%= registerSuccess != null && registerSuccess ? "green" : "red" %>;">
    <%= message %>
</p>
<% } %>

<form action="Register" method="post">
    <label for="username">Användarnamn:</label>
    <input type="text" id="username" name="username" required><br>

    <label for="password">Lösenord:</label>
    <input type="password" id="password" name="password" required><br>

    <label for="userType">Användartyp:</label>
    <select id="userType" name="userType" required>
        <option value="customer">Kund</option>
        <option value="admin">Administratör</option>
        <option value="staff">Arbetare</option>
    </select><br>

    <button type="submit">Registrera</button>
</form>

<form action="goToStart" method="get">
    <input type="submit" value="Go back to start">
</form>
</body>
</html>
