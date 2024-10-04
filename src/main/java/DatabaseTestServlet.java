import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/databaseTest")
public class DatabaseTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Ladda JDBC-drivrutinen
            Class.forName("org.sqlite.JDBC");
            // Anslut till SQLite-databasen
            String url = "jdbc:sqlite:mydatabase.db";
            Connection conn = DriverManager.getConnection(url);

            // Om anslutningen är lyckad
            out.println("Connection successful!");

            // Stäng anslutningen
            conn.close();

        } catch (ClassNotFoundException e) {
            out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            out.println("SQL Exception: " + e.getMessage());
        }
    }
}
