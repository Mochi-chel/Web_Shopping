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

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }

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

