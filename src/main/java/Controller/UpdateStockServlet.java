package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import model.Item;
import model.User;

import static model.ItemDB.*;
/**
 * The UpdateStockServlet class handles updating the stock levels of items.
 * It allows authorized users to add or remove stock based on their requests.
 */
@WebServlet("/updateStock")
public class UpdateStockServlet extends HttpServlet {
    /**
     * Handles POST requests to update the stock of an item.
     * Validates user authentication and processes the requested stock operation.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta itemId och operation från förfrågan
        String itemIdStr = request.getParameter("itemId");
        String operation = request.getParameter("operation");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;  // Avsluta metoden här om ingen användare finns i sessionen.
        }

        if (itemIdStr == null || operation == null) {
            response.sendRedirect("shopSite.jsp"); // Omdirigera tillbaka om något saknas
            return;
        }

        if(user.getUserType().equals(User.UserType.customer)){
            List<Item> items = getAllItems();
            request.setAttribute("items", items);
            request.getRequestDispatcher("shopSite.jsp").forward(request, response);
        }

        boolean updateSuccess = false;
        int itemId = Integer.parseInt(itemIdStr);

        if(operation.equals("add")){
            updateSuccess = updateStock(itemId, 1);
            request.setAttribute("message", "Lyckades ändra");
        }
        else if(operation.equals("remove")){
            updateSuccess = updateStock(itemId, -1);
            request.setAttribute("message", "Lyckades INTE ändra");
        }

        // Kontrollera att itemId och operation inte är null

            // Omdirigera tillbaka till produktlistan efter uppdateringen
        List<Item> items = getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("shopSite.jsp").forward(request, response);
    }
}

