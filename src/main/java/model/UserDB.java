package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserDB {

    public static boolean checkIfUsernameAlreadyExists(String userName){
        Connection con = DBManager.getConnection();

        String query = "SELECT * FROM user WHERE userName = ?";  // SQL-fråga
        try (PreparedStatement pstmt = con.prepareStatement(query)) {  // Förbered SQL-frågan

            pstmt.setString(1, userName);  // Sätt in användarnamnet i SQL-frågan

            try (ResultSet rs = pstmt.executeQuery()) {  // Kör frågan och hämta resultatet
                if (rs.next()) {  // Om det finns en rad i resultatet, finns användaren
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());  // Hantera eventuella SQL-fel
        }finally {
            DBManager.closeConnection(con);  // Stäng anslutningen efter användning
        }
        return false;  // Användaren finns inte
    }


    /**
     * May only be used when username exists.
     * @param userName
     * @return
     */
    public static String getPassword(String userName){
        Connection con = DBManager.getConnection();

        String query = "SELECT password FROM user WHERE username = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setString(1, userName);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next())
                {
                    return rs.getString("password");
                }
                else{
                    throw new IllegalStateException("Could not find password of user you were looking for!");
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Could not execute query: SELECT password FROM user WHERE username = ?");
        }finally {
            DBManager.closeConnection(con);  // Stäng anslutningen efter användning
        }
    }

    public static User.UserType getUserType(String userName){
        Connection con = DBManager.getConnection();

        String query = "SELECT userType FROM user WHERE username = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setString(1, userName);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return User.UserType.valueOf(rs.getString("userType"));
                }
                else{
                    throw new IllegalStateException("Could not find userType of user you were looking for");
                }
            }

        } catch (SQLException e){
            throw new RuntimeException("Could not execute query: SELECT userType FROM user WHERE username = ?");
        }finally {
            DBManager.closeConnection(con);  // Stäng anslutningen efter användning
        }
        //return new User("GoGa", User.UserType.admin);
    }



    public static boolean addUser(User user, String password) throws SQLException {
        Connection con = DBManager.getConnection();

        String insertSQL = "INSERT INTO user (userName, password, userType) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, password);
            pstmt.setString(3, user.getUserType().name());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("User successfully added!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Could not add user to DB!");
        }finally {
            DBManager.closeConnection(con);  // Stäng anslutningen efter användning
        }

        return false;
    }
}
