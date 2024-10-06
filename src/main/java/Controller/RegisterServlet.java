package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.User.UserType;

import java.io.IOException;
import java.sql.SQLException;

import static model.UserDB.addUser;
import static model.UserDB.checkIfUsernameAlreadyExists;
/**
 * The RegisterServlet class is a servlet that handles user registration requests.
 * It processes user input from the registration form and manages the creation of new user accounts.
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    /**
     * Handles GET requests to display the registration form.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for returning the servlet's response
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException      if an input or output error is detected during the operation
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Handles POST requests to process the registration form data.
     * It validates the username, creates a new user, and adds the user to the database.
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
        String userType = request.getParameter("userType");


        if(!checkIfUsernameAlreadyExists(username)){
            User user = new User(username, UserType.valueOf(userType));
            try {
                addUser(user, password);
                request.setAttribute("message", "Regisreringen lyckades, " + username + "!");
                request.setAttribute("registerSuccess", true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            request.setAttribute("message", "Regisreringen lyckades inte!");
            request.setAttribute("registerSuccess", false);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }
}

