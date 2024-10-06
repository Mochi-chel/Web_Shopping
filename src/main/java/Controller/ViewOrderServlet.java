package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.util.List;

import static model.ItemDB.getAllItems;

@WebServlet("/viewOrders")
public class ViewOrderServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;  // Avsluta metoden här om ingen användare finns i sessionen.
        }

        if(user.getUserType().equals(User.UserType.customer)){
            List<Item> items = getAllItems();
            request.setAttribute("items", items);
            request.getRequestDispatcher("shopSite.jsp").forward(request, response);
        }

        try {
            List<Order> orders = OrderDB.getAllOrders();
            request.setAttribute("orders", orders);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving orders. Please try again later.");
        }



        // Navigera till viewOrders.jsp
        request.getRequestDispatcher("viewOrders.jsp").forward(request, response);
    }
}
