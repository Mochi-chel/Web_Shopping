package model;

import jakarta.servlet.ServletException; // Ändrat från javax till jakarta
import jakarta.servlet.http.HttpServlet; // Ändrat från javax till jakarta
import jakarta.servlet.http.HttpServletRequest; // Ändrat från javax till jakarta
import jakarta.servlet.http.HttpServletResponse; // Ändrat från javax till jakarta
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller extends HttpServlet {
    private DBManager dbManager;
    private boolean userStatus;
    private boolean connectionStatus;

    public Controller(){
        dbManager = new DBManager();
    }

    public DBManager getDbManager(){
        return dbManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Item> items = ItemDB.getAllItems(); // Hämta alla items

        //Item item = ItemDB.getItemById(2);

        //List<Item> items = new ArrayList<Item>();
        //items.add(new Item("Jacka", 1, 500));
        //items.add(item);

        request.setAttribute("itemList", items); // Sätta attributet i requestet

        request.getRequestDispatcher("/WEB-INF/views/items.jsp").forward(request, response); // Vidarebefordra till JSP
    }


    public void addUser(/*Input från frontend (username, password och userType)*/){
        /*
        omvandla det vi fått av fråntend (t.ex json) till java representationen av det datat
        (Det här bör ge oss en user (username och en userType) och en password)

        UserDB.addUser(user, password);
        */
    }
}
