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

@WebServlet("/goToShopSite")  // Mappning till denna servlet
public class GoToShopSiteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Item> items = getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("shopSite.jsp").forward(request, response);
    }
}
