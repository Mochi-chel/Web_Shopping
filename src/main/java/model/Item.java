package model;

import java.sql.SQLException;
import java.util.Collection;

public class Item {
    private int id;
    private String name;
    private double price;

    public Item(String name, int id, double price){
        this.id = id;
        this.name = name;
        this.price = price;
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

    public double getPrice(){
        return this.price;
    }

    @Override
    public String toString() {
        return "Id: " + id + "Name: " + name + ", price: " + price;
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