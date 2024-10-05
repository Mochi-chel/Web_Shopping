package model;

public class Order {
    private int id;
    private double totalPrice;
    private String status;
    private User user;

    public Order(int id, double totalPrice, String status, User user) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.user = user;
    }

    public Order(double totalPrice, String status, User user) {
        this.id = -1;
        this.totalPrice = totalPrice;
        this.status = status;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(id).append("\n")
                .append("Username: ").append(user.getUserName()).append("\n")
                .append("Total Price: ").append(totalPrice).append("\n")
                .append("Status: ").append(status).append("\n")
                .append("Items in Order:\n");



        return sb.toString();
    }

}
