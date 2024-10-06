package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Item;
import model.OrderDB;
import model.User;

import java.io.IOException;
import java.util.List;

import static model.ItemDB.getAllItems;

@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("newStatus");

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
            boolean updated = OrderDB.updateOrderStatus(orderId, newStatus);
            if (updated) {
                response.sendRedirect("viewOrders");  // Om uppdaterad, omdirigera till viewOrders
            } else {
                response.getWriter().write("Failed to update order status.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
