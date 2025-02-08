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
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public int getDeveloperId() { return developerId; }
    public void setDeveloperId(int developerId) { this.developerId = developerId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}