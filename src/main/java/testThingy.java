import model.*;

import java.sql.*;
import java.util.List;

import static model.ItemDB.*;
import static model.OrderDB.addOrder;
import static model.UserDB.*;

public class testThingy {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:mydatabase.db";

        //addItem("candy", 9.99, 5, "sweets");
        //addItem("tomato", 10.2, 30, "fruit");

        //addUser(new User("Michel", User.UserType.admin), "CAT");
        //System.out.println(getUserType("Michel").name());
        //System.out.println(getPassword("Michel"));

        //System.out.println(checkIfUsernameAlreadyExists("Michel"));
        //System.out.println(checkIfUsernameAlreadyExists("MAKKAPAKKA"));

        //System.out.println(getItemById(2).getName());


        /*
        List<Item> list = getAllItems();

        for(Item i : list){
            System.out.println(i.toString());
        }
        */

        User user = new User("Simon", User.UserType.admin);
        user.getCart().addItem(new Item("tomato", 2, 10.2, 1, "fruit"));
        user.getCart().addItem(new Item("candy", 1, 9.99, 100, "sweets"));

        addOrder(new Order(1, 29.89, "SENT", user));

    }


}
