package View;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderDB;

import java.io.IOException;

@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("newStatus");

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
