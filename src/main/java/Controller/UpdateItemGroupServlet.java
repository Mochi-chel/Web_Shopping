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
import model.ItemDB;
import model.User;

import static model.ItemDB.getAllItems;

@WebServlet("/updateItemGroup")
public class UpdateItemGroupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta itemId och den nya gruppen från förfrågan
        String itemIdStr = request.getParameter("itemId");
        String newGroup = request.getParameter("newGroup");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;  // Avsluta metoden här om ingen användare finns i sessionen.
        }

        if(!user.getUserType().equals(User.UserType.admin)){
            List<Item> items = getAllItems();
            request.setAttribute("items", items);
            request.getRequestDispatcher("shopSite.jsp").forward(request, response);
        }

        if (itemIdStr != null && newGroup != null && !newGroup.trim().isEmpty()) {
            int itemId = Integer.parseInt(itemIdStr);

            // Uppdatera gruppen i databasen
            boolean success = ItemDB.updateItemGroup(itemId, newGroup);


            if (success) {
            } else {
                // Hantera fel om uppdateringen misslyckades
                request.setAttribute("message", "Failed to update item group.");
            }
        } else {
            // Hantera fel om itemId eller newGroup saknas eller om newGroup är tomt
            request.setAttribute("message", "Invalid input.");

        }
        List<Item> items = getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("shopSite.jsp").forward(request, response);
    }
}
