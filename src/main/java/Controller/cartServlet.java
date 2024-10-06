package Controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Item;
import model.User;

import java.io.IOException;
import java.util.List;
/**
 * The cartServlet class handles requests to view the shopping cart for a logged-in user.
 * It retrieves the user's cart items and total price and forwards this data to the cart.jsp view.
 */
@WebServlet("/cart")
public class cartServlet extends HttpServlet{
    /**
     * Handles GET requests to display the user's cart.
     * Checks if a user is logged in, retrieves the user's cart, and forwards cart details to the view.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            user.setCart(cart);
        }

        List<Item> cartItems = cart.getList();
        double totalPrice = cart.getTotalPrice();

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", totalPrice);

        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
