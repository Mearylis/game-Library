package com.gamelibrary.models;

public class Game {
    private int id;
    private String name;
    private double price;
    private boolean approved;
    private int developerId; // Added field

    public Game(int id, String name, double price, boolean approved, int developerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.approved = approved;
        this.developerId = developerId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isApproved() { return approved; }
    public int getDeveloperId() { return developerId; }

    public void setApproved(boolean approved) { this.approved = approved; }
}
