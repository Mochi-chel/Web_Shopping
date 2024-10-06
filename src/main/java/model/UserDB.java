package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    /**
     * Checks if a username already exists in the database.
     * if the provided username already exists in the "user" table.
     *
     * @param userName the username to be checked
     * @return true if the username already exists in the database, false otherwise
     */
    public static boolean checkIfUsernameAlreadyExists(String userName){
        Connection con = DBManager.getConnection();

        String query = "SELECT * FROM user WHERE userName = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, userName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            DBManager.closeConnection(con);
        }
        return false;
    }


    /**
     * Retrieves the password for a given username from the database.
     * This method should only be used when the username is confirmed to exist.
     *
     * @param userName the username whose password needs to be retrieved
     * @return the password of the specified user
     * @throws IllegalStateException if the username is not found in the database
     * @throws RuntimeException if a SQL execution error occurs during the query
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

    /**
     * Retrieves the user type for a given username from the database.
     *
     * @param userName the username whose user type needs to be retrieved
     * @return the user type of the specified user as an enum of {@link User.UserType}
     * @throws IllegalStateException if the username is not found in the database
     * @throws RuntimeException if a SQL execution error occurs during the query
     */
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


    /**
     * Adds a new user to the database with the specified username, password, and user type.
     *
     * @param user the {@link User} object containing the username and user type to be added
     * @param password the password for the new user
     * @return {@code true} if the user was successfully added to the database, {@code false} otherwise
     * @throws SQLException if a database access error occurs or the SQL query fails
     */
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

    /**
     * Retrieves a list of all users from the database.
     *
     * @return a {@link List} of {@link User} objects, where each object represents a user retrieved from the database
     */
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection con = DBManager.getConnection();

        String query = "SELECT * FROM user";
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

    /**
     * Deletes a user from the database based on the provided username.
     *
     * @param userName the username of the user to be deleted from the database
     * @return {@code true} if the user was successfully deleted, {@code false} otherwise
     */
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

    /**
     * Updates the user type of a specified user in the database.
     *
     * @param userName the username of the user whose type is to be updated
     * @param userType the new user type to be set for the user
     * @return {@code true} if the user type was successfully updated, {@code false} otherwise
     */
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
