<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="sv">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Logga in</title>
</head>
<body>
<h1>Logga in</h1>
<form action="LoginServlet" method="post">
  <label for="username">Användarnamn:</label>
  <input type="text" id="username" name="username" required><br>
  <label for="password">Lösenord:</label>
  <input type="password" id="password" name="password" required><br>
  <button type="submit">Logga in</button>
</form>
</body>
</html>