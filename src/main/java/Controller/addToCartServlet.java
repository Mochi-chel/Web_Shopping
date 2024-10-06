package Controller;

import jakarta.servlet.http.HttpSession;
import model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static model.ItemDB.getAllItems;

/**
 * The addToCartServlet class handles requests to add an item to the user's shopping cart.
 * It checks the item's availability, updates the cart, and provides feedback if the item
 * cannot be added due to insufficient stock.
 */
@WebServlet("/addToCart")
public class addToCartServlet extends HttpServlet{
    /**
     * Handles POST requests to add an item to the user's cart.
     * Validates user session, checks stock availability, and updates the cart.
     * Redirects to the shop page or provides a warning message if stock is insufficient.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error is detected
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String itemIdStr = request.getParameter("itemId");
        if (itemIdStr != null) {
            int itemId = Integer.parseInt(itemIdStr);
            Item item = ItemDB.getItemById(itemId);

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


            int numberInCart = 0;
            int index = -1;
            for (int i = 0; i < cart.getList().size(); i++)
            {
                if(cart.getList().get(i).getId() == itemId)
                {
                    index = i;
                    numberInCart = cart.getList().get(i).getStock();
                    break;
                }
            }

            if((item.getStock() - numberInCart) > 0)
            {
                if(index != -1)
                {
                    cart.increase(item);
                }
                else{
                    cart.addItem(new Item(item.getName(), item.getId(), item.getPrice(), 1, item.getGroup()));
                }
                user.setCart(cart);
                response.sendRedirect("shopSite");
            }
            else {
                request.setAttribute("warning", "Cannot add " + item.getName() + " to cart. Not enough stock.");

                List<Item> items = getAllItems();
                request.setAttribute("items", items);
                request.getRequestDispatcher("shopSite.jsp").forward(request, response);
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item ID is required.");
        }
    }
}
