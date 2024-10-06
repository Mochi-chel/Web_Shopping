package Controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Item;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class cartServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Hämta sessionen
        HttpSession session = request.getSession();

        // Hämta kundvagnen från sessionen
        User user = (User) session.getAttribute("user");

        // Kontrollera om kundvagnen existerar, om inte, skapa en ny kundvagn
        if (user == null) {
            response.sendRedirect("login.jsp");  // Om ingen användare är inloggad, skicka till inloggningssidan
            return;
        }

        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            user.setCart(cart);
        }

        // Hämta varor från kundvagnen och totalpriset
        List<Item> cartItems = cart.getList();
        double totalPrice = cart.getTotalPrice();

        // Lägg till kundvagnens varor och totalpris som attribut
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", totalPrice);

        // Skicka vidare till cart.jsp för att visa kundvagnen
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Logik för att hantera POST-förfrågningar (t.ex. öka eller minska antal produkter i vagnen)
        doGet(request, response); // Vid POST-omdirigering, anropa doGet för att visa uppdaterad kundvagn
    }
}
