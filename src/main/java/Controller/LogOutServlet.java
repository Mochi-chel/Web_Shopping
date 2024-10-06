package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")  // URL-mappning till logout-servlet
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Hämta den nuvarande sessionen, men skapa inte en ny om ingen finns
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Rensa ett specifikt attribut från sessionen (t.ex. "user")
            session.removeAttribute("user");
        }

        // Omdirigera till login.jsp
        response.sendRedirect("login.jsp");
    }
}
