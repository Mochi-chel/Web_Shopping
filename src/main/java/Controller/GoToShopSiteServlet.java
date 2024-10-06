package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Item;

import java.io.IOException;
import java.util.List;

import static model.ItemDB.getAllItems;
/**
 * The GoToShopSiteServlet class handles requests to display the main shopping page.
 * Retrieves all available items from the database and forwards them to the shop view.
 */
@WebServlet("/goToShopSite")
public class GoToShopSiteServlet extends HttpServlet {
    /**
     * Handles GET requests to load the shopping site.
     * Retrieves a list of available items from the database and forwards them to "shopSite.jsp" for display.
     *
     * @param request  the HttpServletRequest object containing the client request data
     * @param response the HttpServletResponse object for sending the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Item> items = getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("shopSite.jsp").forward(request, response);
    }
}
