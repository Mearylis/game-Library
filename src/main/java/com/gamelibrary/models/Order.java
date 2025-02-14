package com.gamelibrary.models;

import java.util.Date;

public class Order {
    private int id;
    private int userId;
    private Date orderDate;

    public Order(int id, int userId, Date orderDate) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public Date getOrderDate() { return orderDate; }

    public void setId(int id) { this.id = id; }
}