package Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import model.*;

import static model.ItemDB.getItemById;

@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;  // Avsluta metoden här om ingen användare finns i sessionen.
        }
        Cart cart = user.getCart();

        String itemId = request.getParameter("itemId");
        String operation = request.getParameter("operation");

        if (cart != null && itemId != null) {
            //System.out.println(itemId);
            //System.out.println("Im in the if-statement of cart != null && itemID != null");

            int index = 0;
            while (index < cart.getList().size()) {
                Item item = cart.getList().get(index);
                if (String.valueOf(item.getId()).equals(itemId)) {
                    break;
                }
                index++;
            }

            if (cart.getList().get(index) != null) {
                if ("add".equals(operation))
                {
                    Item itemInLayer = getItemById(cart.getList().get(index).getId());
                    System.out.println(itemInLayer.getName());
                    System.out.println(itemInLayer.getStock());
                    if (itemInLayer.getStock() > cart.getList().get(index).getStock()) {
                        cart.getList().get(index).increaseAmount();
                    }
                    else
                    {
                        request.setAttribute("warning", "Cannot add more of " + itemInLayer.getName() + ". Not enough stock.");

                        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp"); // Justera till den korrekta sökvägen
                        dispatcher.forward(request, response);
                        return;
                    }
                }
                else if ("remove".equals(operation))
                {
                    int currentStock = cart.getList().get(index).getStock();

                    if (currentStock > 1)
                    {
                        cart.getList().get(index).decreaseAmoount();
                    }
                    else
                    {
                        cart.removeItem(cart.getList().get(index)); // Ta bort varan om mängden blir 0
                    }
                }

                //Uppdatera session
                session.setAttribute("user", user);
                request.setAttribute("message", "Cart updated successfully.");
            }
        } else {
            request.setAttribute("message", "Cart is empty or item not found.");
        }

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}
