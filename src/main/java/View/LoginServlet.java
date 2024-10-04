package View;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;

import static model.UserDB.*;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);

    }

    //@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta användarnamn och lösenord från formuläret
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Kontrollera användarnamn och lösenord (mock-up för enkelhetens skull)
        boolean validUser = validateUser(username, password);

        if (validUser) {
            // Om användaren är giltig, sätt attribut och skicka tillbaka till JSP med meddelande
            request.setAttribute("loginSuccess", true);
            request.setAttribute("message", "Inloggningen lyckades. Välkommen, " + username + "!");
        } else {
            // Felmeddelande om inloggningen misslyckas
            request.setAttribute("loginSuccess", false);
            request.setAttribute("message", "Felaktigt användarnamn eller lösenord.");
        }

        // Skicka tillbaka till samma login.jsp (forward)

        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);

    }

    private boolean validateUser(String username, String password) {

        //Kontroll mot databasen

        if(checkIfUsernameAlreadyExists(username)){
            if(password.equals(getPassword(username))){
                User user = new User(username, getUserType(username));      //Det här är den inloggade personen
                return true;
            }
        }

        return false;

        //return true;//"admin".equals(username) && "password123".equals(password);
    }
}

