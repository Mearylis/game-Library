package com.gamelibrary.models;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int id;
    private String name;
    private double price;
    private boolean approved;
    private int developerId;
    private String category;
    private List<Integer> ratings;

    public Game(int id, String name, double price, boolean approved, int developerId, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.approved = approved;
        this.developerId = developerId;
        this.category = category;
        this.ratings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isApproved() {
        return approved;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public String getCategory() {
        return category;
    }

    public void addRating(int rating) {
        ratings.add(rating);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }
}
