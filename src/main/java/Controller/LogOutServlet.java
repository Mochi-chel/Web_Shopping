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
    /**
     * Handles GET requests to log the user out of the application.
     * It invalidates the user session and redirects to the login page.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
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
