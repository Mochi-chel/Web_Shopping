package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    public static boolean checkIfUsernameAlreadyExists(String userName){
        Connection con = DBManager.getConnection();

        String query = "SELECT * FROM user WHERE userName = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, userName);  // Sätt in användarnamnet i SQL-frågan

            try (ResultSet rs = pstmt.executeQuery()) {  // Kör frågan och hämta resultatet
                if (rs.next()) {  // Om det finns en rad i resultatet, finns användaren
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());  // Hantera eventuella SQL-fel
        }finally {
            DBManager.closeConnection(con);
        }
        return false;
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
            DBManager.closeConnection(con);
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
            DBManager.closeConnection(con);
        }
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
            DBManager.closeConnection(con);
        }

        return false;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection con = DBManager.getConnection();

        String query = "SELECT * FROM user";  // SQL-fråga för att hämta alla användare
        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                String userTypeStr = rs.getString("userType");
                User.UserType userType = User.UserType.valueOf(userTypeStr);

                User user = new User(userName, userType);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBManager.closeConnection(con);
        }

        return users;
    }

    public static boolean deleteUser(String userName) {
        Connection con = DBManager.getConnection();

        String query = "DELETE FROM user WHERE userName = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, userName);

            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("I can delete person");
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting user: " + e.getMessage());
        } finally {
            DBManager.closeConnection(con);
        }

        System.out.println("Kunde inte radera person");

        return false;
    }

    public static boolean updateUserType(String userName, User.UserType userType) {
        Connection con = DBManager.getConnection();
        String updateSQL = "UPDATE user SET userType = ? WHERE userName = ?";

        try (PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
            pstmt.setString(1, userType.name());
            pstmt.setString(2, userName);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Could not update user type: " + e.getMessage());
            return false;
        } finally {
            DBManager.closeConnection(con);
        }
    }
}
