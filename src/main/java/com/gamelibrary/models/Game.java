package com.gamelibrary.models;

public class Game {
    private int id;
    private String name;
    private int developerId;
    private double price;
    private double sizeGB;
    private int ageRestriction;
    private String genre;
    private String description;
    private boolean approved;

    public Game(int id, String name, int developerId, double price, double sizeGB, int ageRestriction, String genre, String description) {
        this.id = id;
        this.name = name;
        this.developerId = developerId;
        this.price = price;
        this.sizeGB = sizeGB;
        this.ageRestriction = ageRestriction;
        this.genre = genre;
        this.description = description;
        this.approved = false; // По умолчанию игра не одобрена
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public int getDeveloperId() { return developerId; }
    public double getPrice() { return price; }
    public double getSizeGB() { return sizeGB; }
    public int getAgeRestriction() { return ageRestriction; }
    public String getGenre() { return genre; }
    public String getDescription() { return description; }
    public boolean isApproved() { return approved; }

    public void setApproved(boolean approved) { this.approved = approved; }
}