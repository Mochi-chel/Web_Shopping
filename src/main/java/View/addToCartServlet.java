package View;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addToCart")
public class addToCartServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String itemIdStr = request.getParameter("itemId");
        if (itemIdStr != null) {
            int itemId = Integer.parseInt(itemIdStr);
            Item item = ItemDB.getItemById(itemId);

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                cart = new Cart(); // Skapa en ny kundvagn om den inte finns
                session.setAttribute("cart", cart);
            }

            if(cart.checkIfItemExists(item)){
                cart.increase(item);
            }
            else{
                cart.addItem(new Item(item.getName(), item.getId(), item.getPrice(), 1, item.getGroup()));
            }

            System.out.println("Here is addToCartServlet! And the size is: " + cart.getList().size());

            for(int i = 0; i < cart.getList().size(); i++)
            {
                System.out.println(i + ".");
                System.out.println(cart.getList().get(i).getName() + " " + cart.getList().get(i).getStock() + " " + cart.getList().get(i).getPrice());
            }

            response.sendRedirect("shopSite");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item ID is required.");
        }
    }
}
