package View;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.io.IOException;

import model.*;

@WebServlet("/sendOrder")
public class SendOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        Cart cart = user.getCart();

        if (user != null && cart.getList() != null && !cart.getList().isEmpty()) {

            Order order = new Order(cart.getTotalPrice(), "Pending",user);

            boolean isAdded = OrderDB.addOrder(order);

            if (isAdded) {
                user.getCart().clear();
                request.setAttribute("message", "Order placed successfully!");
            } else {
                request.setAttribute("message", "Failed to place the order. Please try again.");
            }
        } else {
            request.setAttribute("message", "Your cart is empty or user is not logged in.");
        }

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}
