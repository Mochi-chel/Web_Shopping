package View;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Logga in</title></head>");
        out.println("<body>");
        out.println("<h1>Logga in</h1>");
        out.println("<form action='LoginServlet' method='post'>");
        out.println("Användarnamn: <input type='text' name='username'><br>");
        out.println("Lösenord: <input type='password' name='password'><br>");
        out.println("<button type='submit'>Logga in</button>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Enkel kontroll för demonstration
        if ("admin".equals(username) && "1234".equals(password)) {
            response.getWriter().println("Välkommen " + username + "!");
        } else {
            response.getWriter().println("Fel användarnamn eller lösenord!");
        }
    }
}

