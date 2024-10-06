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

@WebServlet("/clearCart")
public class ClearCartServlet extends HttpServlet {
    /**
     * Handles POST requests to clear the user's cart.
     * Checks if the user is logged in, clears their cart if it exists, and forwards updated data to the shop view.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.removeAttribute("cartItems");
        session.removeAttribute("totalPrice");

        request.setAttribute("message", "Your cart has been cleared.");

        // Redirect to the cart page
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}