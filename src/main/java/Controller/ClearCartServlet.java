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
/**
 * The ClearCartServlet class handles requests to clear the user's shopping cart.
 * If the user is logged in, it clears all items from their cart and refreshes the item list on the shop page.
 */
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
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (user.getCart() != null) {
            user.getCart().clear();
        }

        session.setAttribute("user", user);
        List<Item> items = getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("shopSite.jsp").forward(request, response);
    }
}