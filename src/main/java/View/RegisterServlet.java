package View;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Registrera dig</title></head>");
        out.println("<body>");
        out.println("<h1>Registrera dig</h1>");
        out.println("<form action='RegisterServlet' method='post'>");
        out.println("Användarnamn: <input type='text' name='username'><br>");
        out.println("Lösenord: <input type='password' name='password'><br>");
        out.println("<button type='submit'>Registrera</button>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Här kan du lägga till kod för att spara användaren i databasen
        response.getWriter().println("Användare " + username + " registrerad!");
    }
}

