package model;

import java.util.Hashtable;
import java.util.Map;

public class Cart {
    private Hashtable<Item, Integer> table;

    public Cart(){
        table = new Hashtable<>();
    }

    public void addItem(Item item){
        if(table.containsKey(item)){
            table.put(item, table.get(item)+1);
        }
        else{
            table.put(item, 1);
        }
    }

    public void deleteItem(Item item)
    {
        table.remove(item);
    }

    public double getTotalPrice() {
        double total = 0;

        for (Map.Entry<Item, Integer> entry : table.entrySet()) {
            int quantity = entry.getValue();  // Hämta antalet för varan
            total += entry.getKey().getPrice() * quantity;  // Pris * antal
        }

        return total;
    }

}
