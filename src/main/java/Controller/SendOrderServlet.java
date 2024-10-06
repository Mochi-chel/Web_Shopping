package Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import model.*;
/**
 * The SendOrderServlet class is a servlet that handles the process of placing an order.
 * It retrieves the user's cart and creates an order based on the items in the cart.
 */
@WebServlet("/sendOrder")
public class SendOrderServlet extends HttpServlet {
    /**
     * Handles POST requests to process the order placement.
     * It checks if the user is logged in and if the cart is not empty,
     * then it creates an order and adds it to the database.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
    @Override
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
