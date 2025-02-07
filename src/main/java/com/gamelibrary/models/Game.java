package com.gamelibrary.models;

import java.time.LocalDate;

public class Game {
    private int id;
    private String name;
    private int developerId;
    private double price;
    private LocalDate creationDate;
    private boolean approved;
    private LocalDate lastPlayedDate;

    public Game(int id, String name, int developerId, double price, LocalDate creationDate, boolean approved) {
        this.id = id;
        this.name = name;
        this.developerId = developerId;
        this.price = price;
        this.creationDate = creationDate;
        this.approved = approved;
        this.lastPlayedDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDate getLastPlayedDate() {
        return lastPlayedDate;
    }

    public void setLastPlayedDate(LocalDate lastPlayedDate) {
        this.lastPlayedDate = lastPlayedDate;
    }
}