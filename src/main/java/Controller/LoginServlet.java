package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.UserDB;

import java.io.IOException;

import static model.UserDB.*;
/**
 * The LoginServlet class is a servlet that manages user login functionality.
 * It handles GET requests to display the login page and POST requests to process user login attempts.
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    /**
     * Handles GET requests to display the login page.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);

    }
    /**
     * Handles POST requests to process user login attempts.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean validUser = validateUser(username, password);

        if (validUser) {
            request.setAttribute("loginSuccess", true);
            request.setAttribute("message", "Inloggningen lyckades. Välkommen, " + username + "!");

            HttpSession session = request.getSession();
            session.setAttribute("user", new User(username, getUserType(username)));

            response.sendRedirect("shopSite");
        } else {
            request.setAttribute("loginSuccess", false);
            request.setAttribute("message", "Felaktigt användarnamn eller lösenord.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }

    }

    private boolean validateUser(String username, String password) {

        if(checkIfUsernameAlreadyExists(username)){
            if(password.equals(getPassword(username))){
                return true;
            }
        }

        return false;
    }

    public User.UserType getUserType(String userName){
        return UserDB.getUserType(userName);
    }
}

