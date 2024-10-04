import model.Controller;
import model.Item;
import model.ItemDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class getFromDatabaseTest {

    public static void main(String[] args){
        Controller controller = new Controller();

        Item item = ItemDB.getItemById(2, controller);

        System.out.println("Id: " + item.getId() + "\nNamn: " + item.getName() + "\nPris: " + item.getPrice());
    }
}
