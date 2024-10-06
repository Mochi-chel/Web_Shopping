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

import static model.ItemDB.addItem;
import static model.ItemDB.getAllItems;
/**
 * The AddNewItemServlet class handles requests for adding a new item to the database.
 * Only users with the admin role are allowed to add new items.
 */
@WebServlet("/addItem")
public class AddNewItemServlet extends HttpServlet {

    /**
     * Handles GET requests to display the addItem.jsp page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("addItem.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to add a new item to the database.
     * Validates that the user is an admin, that all fields are filled, and that
     * price and stock values are correctly formatted. Redirects back to addItem.jsp
     * in case of any error, or to shopSite.jsp upon successful item addition.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error is detected
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");
        String group = request.getParameter("group");

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

        if(name == null || priceStr == null || stockStr == null || group == null){
            request.setAttribute("errorMessage", "All fields are required. Please fill in all fields.");
            request.getRequestDispatcher("addItem.jsp").forward(request, response);
        }

        try {
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);

            boolean success = addItem(name, price, stock, group);

            if (success) {
                List<Item> items = getAllItems();
                request.setAttribute("items", items);
                request.getRequestDispatcher("shopSite.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to add new item.");
                request.getRequestDispatcher("addItem.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid input.");
            request.getRequestDispatcher("addItem.jsp").forward(request, response);
        }
    }
}