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
/**
 * The UpdateItemGroupServlet class is responsible for updating the group of a specified item.
 * It ensures that only users with admin privileges can perform this operation.
 */
@WebServlet("/updateItemGroup")
public class UpdateItemGroupServlet extends HttpServlet {
    /**
     * Handles POST requests to update the item group for a specific item.
     * It validates user permissions and the input parameters before attempting to update the item group.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemIdStr = request.getParameter("itemId");
        String newGroup = request.getParameter("newGroup");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if(!user.getUserType().equals(User.UserType.admin)){
            List<Item> items = getAllItems();
            request.setAttribute("items", items);
            request.getRequestDispatcher("shopSite.jsp").forward(request, response);
        }

        if (itemIdStr != null && newGroup != null && !newGroup.trim().isEmpty()) {
            int itemId = Integer.parseInt(itemIdStr);

            boolean success = ItemDB.updateItemGroup(itemId, newGroup);


            if (success) {
            } else {
                request.setAttribute("message", "Failed to update item group.");
            }
        } else {
            request.setAttribute("message", "Invalid input.");

        }
        List<Item> items = getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("shopSite.jsp").forward(request, response);
    }
}
