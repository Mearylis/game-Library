package com.gamelibrary.models;

import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private String description;
    private int categoryId;
    private double price;
    private LocalDate creationDate;

    public Product(int id, String name, String description, int categoryId, double price, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}