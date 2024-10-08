package model;

public class User {

    public static enum UserType {
        admin, customer, staff
    }

    private Cart cart;
    private String userName;
    private UserType userType;

    public User(String userName, UserType userType){
        this.userName = userName;
        this.userType = userType;
        this.cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public String getUserName(){
        return userName;
    }

    public UserType getUserType(){
        return userType;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


}
