package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

public class ItemDB extends Item{
    public static Collection searchItems(String group) throws SQLException {
        Vector v = new Vector();
        Connection con = DBManager.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select item_id, name from Item where item_group = "+group);
        while (rs.next()) {
            int i = rs.getInt("item_id");
            String name = rs.getString("name");
            v.addElement(new ItemDB(i, name));
        }
        return v;
    }

    private ItemDB(int id, String name) {
        super(id, name);
    }
}