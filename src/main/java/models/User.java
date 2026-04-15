package models;

import java.util.LinkedList;
import java.util.List;

public class User {
    private String dni;
    private List<Order> orders;

    public User (String dni){
        this.dni = dni;
        this.orders = new LinkedList<Order>();
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }

    public List<Order> orders() {
        return this.orders;
    }
}
