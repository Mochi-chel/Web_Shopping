package model;

import java.util.ArrayList;
import java.util.List;


public class Cart {


    private List<Item> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }


    public void addItem(Item item){
        cartItems.add(item);
    }

    public void removeItem(Item item) {
        cartItems.remove(item);
    }

    public void increase(Item item)
    {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getId() == item.getId()) {
                cartItems.get(i).increaseAmount();
                break;
            }
        }
    }

    public boolean checkIfItemExists(Item item){
        for(Item i : cartItems){
            if(i.getId() == item.getId()){
                return true;
            }
        }
        return false;
    }

    public List<Item> getList(){
        return new ArrayList<>(cartItems);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Item item : cartItems) {
            total += item.getPrice()*item.getStock();
        }
        return total;
    }

    public void clear(){
        this.cartItems.clear();
    }


    /*private Hashtable<Item, Integer> table;

    public Cart(){
        table = new Hashtable<>();
    }

    public List<Item> getItems() {
        return items;
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
    }*/

    /*public boolean checkIfItemExists(Item item) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().equals(item)) {
                return true;
            }
        }
        return false;
    }*/

}
