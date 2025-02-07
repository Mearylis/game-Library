package com.gamelibrary.models;

import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private List<OrderItem> orderItems;
    private double totalPrice;

    public Order(int orderId, int userId, List<OrderItem> orderItems, double totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() { return orderId; }
    public int getUserId() { return userId; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public double getTotalPrice() { return totalPrice; }
}
