package model;


import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;


/*
 * Cart?
 *
 *
 * */
/*public class LookItems {
    public Hashtable getItemsWithGroup(String s) throws SQLException {
        Collection c = Item.searchItems(s);
        Hashtable t = new Hashtable();
        t.put("size", c.size());

        Iterator it = c.iterator();

        int index = 0;
        while(it.hasNext()) {
            Hashtable item = new Hashtable();
            Item o = (Item) it.next();

            item.put("name", o.getName());
            item.put("price", o.getPrice());
            t.put("Item"+index, item);
            index++;
        }
        return t;
    }
}
*/
