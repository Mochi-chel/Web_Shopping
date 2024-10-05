package model;

import java.sql.SQLException;
import java.util.Collection;

public class Item {
    private int id;
    private String name;
    private double price;
    private int stock;
    private String group;

    public Item(String name, int id, double price, int stock, String group){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.group = group;
    }

    public Item(int id, String name){
        this.id = id;
        this.name = name;
        this.price = 0;
    }

    static public Collection searchItems(String group) throws SQLException {
        return ItemDB.searchItems(group);//Dunno what it is //TODO: GPT
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getStock(){
        return this.stock;
    }

    public String getGroup(){
        return this.group;
    }

    public double getPrice(){
        return this.price;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    public boolean increaseAmount()
    {
        this.stock = this.stock + 1;
        return true;
    }

    public boolean decreaseAmoount(){
        stock = stock-1;
        return true;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name + ", price: " + price + ", stock: " + stock + ", group: " + group;
    }
}
/*public class Item {
private int id;
private String name;
private int price;
static public Collection searchItems(String
group)
{
return ItemDB.searchItems(group);
}
protected Item(int id , String name) {
this.id = id;
this.name = name;
}
public String getName() {
return name;
}
}*/