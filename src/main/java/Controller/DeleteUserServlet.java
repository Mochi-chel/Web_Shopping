package Controller;

import jakarta.servlet.http.HttpSession;
import model.Item;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static model.ItemDB.getAllItems;
import static model.UserDB.deleteUser;
import static model.UserDB.getAllUsers;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName"); // Hämta användarnamnet från förfrågan

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



        if (!userName.isEmpty()) {
            boolean success = deleteUser(userName); // Kalla metoden för att ta bort användaren

            // Om användaren raderades framgångsrikt
            if (success) {
                request.setAttribute("message", "User successfully deleted.");
            } else {
                request.setAttribute("message", "Failed to delete user.");
            }
        } else {
            request.setAttribute("message", "Invalid user.");
        }

        List<User> users = getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("handleUsers.jsp").forward(request, response);
    }
}
