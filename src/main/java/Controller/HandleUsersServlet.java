package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Item;
import model.User;

import java.io.IOException;
import java.util.List;

import static model.ItemDB.getAllItems;
import static model.UserDB.getAllUsers;
/**
 * The HandleUsersServlet class is a servlet responsible for handling user management actions
 * within the application. It retrieves the list of all users for admin users or redirects
 * non-admin users to the shopping page.
 */
@WebServlet("/handleUsers")
public class HandleUsersServlet extends HttpServlet {
    /**
     * Handles GET requests by verifying if the user is logged in and has admin privileges.
     * If the user is an admin, the method retrieves all users and forwards the request to
     * the "handleUsers.jsp" page. If the user is not an admin, it redirects to "shopSite.jsp".
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;  // Avsluta metoden här om ingen användare finns i sessionen.
        }

        if(!user.getUserType().equals(User.UserType.admin)){
            List<Item> items = getAllItems();
            request.setAttribute("items", items);
            request.getRequestDispatcher("shopSite.jsp").forward(request, response);
        }

        List<User> users = getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("handleUsers.jsp").forward(request, response);
    }

    // Om du planerar att hantera POST-förfrågningar också kan du implementera doPost här
}
