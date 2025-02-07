package com.gamelibrary.models;

public class Game {
    private int id;
    private String name;
    private double price;
    private boolean approved;
    private int developerId;
    private String category;

    public Game(int id, String name, double price, boolean approved, int developerId, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.approved = approved;
        this.developerId = developerId;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isApproved() { return approved; }
    public int getDeveloperId() { return developerId; }
    public String getCategory() { return category; }

    public void setApproved(boolean approved) { this.approved = approved; }
}
