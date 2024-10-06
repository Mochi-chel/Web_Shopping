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
/**
 * The UpdateOrderStatusServlet class is responsible for updating the status of an order.
 * It restricts access based on user type, allowing only users with admin privileges to update order statuses.
 */
@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusServlet extends HttpServlet{
    /**
     * Handles POST requests to update the status of an order.
     * It validates user permissions and the input parameters before attempting to update the order status.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
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
