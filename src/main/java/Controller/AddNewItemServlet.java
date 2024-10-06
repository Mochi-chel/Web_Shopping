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

@WebServlet("/addItem")
public class AddNewItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ladda JSP-sidan för att lägga till ett nytt item
        request.getRequestDispatcher("addItem.jsp").forward(request, response);
    }


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
            return;  // Avsluta metoden här om ingen användare finns i sessionen.
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

            // Kalla metoden i ItemDB för att skapa det nya itemet
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