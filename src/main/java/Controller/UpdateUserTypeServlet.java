package Controller;

import jakarta.servlet.http.HttpSession;
import model.Item;
import model.User;
import model.UserDB;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static model.ItemDB.getAllItems;
import static model.UserDB.getAllUsers;
/**
 * The UpdateUserTypeServlet class handles requests to update the user type of a specified user.
 * It ensures that only administrators can perform this action.
 */
@WebServlet("/updateUserType")
public class UpdateUserTypeServlet extends HttpServlet {
    /**
     * Handles POST requests to update the user type of a specified user.
     * Validates the user session and checks permissions before processing the request.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta användarnamnet och den nya användartypen från förfrågan
        String userName = request.getParameter("userName");
        String userTypeStr = request.getParameter("userType");

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

        // Konvertera sträng till enum
        User.UserType userType;
        try {
            userType = User.UserType.valueOf(userTypeStr); // Omvandla sträng till enum
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Invalid user type.");
            List<User> users = getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("handleUsers.jsp").forward(request, response);
            return;
        }

        // Anropa metoden i UserDB för att uppdatera användartypen
        boolean updated = UserDB.updateUserType(userName, userType);

        if (updated) {
            // Om uppdateringen lyckades, omdirigera till hanteringssidan
        } else {
            // Om uppdateringen misslyckades, skicka ett felmeddelande
            request.setAttribute("errorMessage", "Could not update user type.");
        }

        List<User> users = getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("handleUsers.jsp").forward(request, response);
    }
}
