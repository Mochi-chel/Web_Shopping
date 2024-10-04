import model.Controller;
import model.DBManager;
import model.User;

import java.sql.*;

import static model.ItemDB.addItem;
import static model.UserDB.*;

public class testThingy {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:mydatabase.db";

        //addItem("GODIS", 99.89);

        //addUser(new User("Michel", User.UserType.admin), "CAT");
        //System.out.println(getUserType("Michel").name());
        //System.out.println(getPassword("Michel"));

        //System.out.println(checkIfUsernameAlreadyExists("Michel"));
        //System.out.println(checkIfUsernameAlreadyExists("MAKKAPAKKA"));
    }


}
