package Controller;

import jakarta.servlet.http.HttpSession;
import model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
/**
 * The ItemlistServlet class is a servlet responsible for retrieving the list of items
 * available in the shop and forwarding the request to the corresponding JSP page for display.
 */
@WebServlet("/shopSite")
public class ItemlistServlet extends HttpServlet{
    /**
     * Handles GET requests to retrieve the list of items.
     * It checks if the user is logged in and fetches all items from the database.
     * If successful, it forwards the request to "shopSite.jsp".
     * If there is an error during the retrieval, it sends an error response.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws IOException if an input or output error is detected during the operation
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }


        try {
            List<Item> items = ItemDB.getAllItems();

            request.setAttribute("items", items);

            RequestDispatcher dispatcher = request.getRequestDispatcher("shopSite.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Fel vid hämtning av items.");
        }
    }

}
