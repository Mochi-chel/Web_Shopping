package Controller;

import model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

@WebServlet("/shopSite")
public class ItemlistServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            // Hämta alla items från databasen
            //System.out.println("Innan hämtning av items");
            List<Item> items = ItemDB.getAllItems();

            //System.out.println("Antal items hämtade: " + items.size());
            // Sätta listan av items som ett attribut i förfrågan
            request.setAttribute("items", items);

            // Skicka förfrågan vidare till JSP-sidan
            //System.out.println("Attribute settled");
            RequestDispatcher dispatcher = request.getRequestDispatcher("shopSite.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Hantera eventuella fel, t.ex. visa ett felmeddelande
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Fel vid hämtning av items.");
        }
    }

}
