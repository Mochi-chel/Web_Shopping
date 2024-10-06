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

@WebServlet("/handleUsers")
public class HandleUsersServlet extends HttpServlet {

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
