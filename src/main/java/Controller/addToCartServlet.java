package View;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;

@WebServlet("/addToCart")
public class addToCartServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String itemIdStr = request.getParameter("itemId");
        if (itemIdStr != null) {
            int itemId = Integer.parseInt(itemIdStr);
            Item item = ItemDB.getItemById(itemId);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            Cart cart = user.getCart();

            if (cart == null) {
                cart = new Cart();
                user.setCart(cart);
            }


            int numberInCart = 0;
            int index = -1;
            for (int i = 0; i < cart.getList().size(); i++)
            {
                if(cart.getList().get(i).getId() == itemId)
                {
                    index = i;
                    numberInCart = cart.getList().get(i).getStock();
                    break;
                }
            }

            if((item.getStock() - numberInCart) > 0)
            {
                if(index != -1)
                {
                    cart.increase(item);
                }
                else{
                    cart.addItem(new Item(item.getName(), item.getId(), item.getPrice(), 1, item.getGroup()));
                }
                user.setCart(cart);
                response.sendRedirect("shopSite");
            }
            else {
                request.setAttribute("warning", "Cannot add " + item.getName() + " to cart. Not enough stock.");

                List<Item> items = getAllItems();
                request.setAttribute("items", items);
                request.getRequestDispatcher("shopSite.jsp").forward(request, response);
            }
            //System.out.println("Here is addToCartServlet! And the size is: " + cart.getList().size());

            /*for(int i = 0; i < cart.getList().size(); i++)
            {
                System.out.println(i + ".");
                System.out.println(cart.getList().get(i).getName() + " " + cart.getList().get(i).getStock() + " " + cart.getList().get(i).getPrice());
            }*/


        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item ID is required.");
        }
    }
}
