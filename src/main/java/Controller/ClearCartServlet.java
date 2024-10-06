package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/clearCart")
public class ClearCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.removeAttribute("cartItems");
        session.removeAttribute("totalPrice");

        request.setAttribute("message", "Your cart has been cleared.");

        // Redirect to the cart page
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}