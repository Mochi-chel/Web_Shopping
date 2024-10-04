import model.Controller;
import model.DBManager;

import java.sql.*;

import static model.ItemDB.addItem;

public class testThingy {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        //Controller controller = new Controller();


        addItem("GODIS", 99.89);
    }


}
