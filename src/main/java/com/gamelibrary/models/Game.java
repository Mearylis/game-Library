package com.gamelibrary.models;

import java.time.LocalDate;
import java.util.Objects;

public class Game {
    private int id;
    private String name;
    private int developerId;
    private double price;
    private double sizeGB;
    private int ageRestriction;
    private String description;
    private LocalDate creationDate;
    private boolean approved;

    public Game(int id, String name, int developerId, double price, double sizeGB, int ageRestriction, String description, LocalDate creationDate, boolean approved) {
        this.id = id;
        this.name = name;
        this.developerId = developerId;
        this.price = price;
        this.sizeGB = sizeGB;
        this.ageRestriction = ageRestriction;
        this.description = description;
        this.creationDate = creationDate;
        this.approved = approved;
    }

    public int getId() {
        return id; }
    public String getName() {
        return name; }
    public int getDeveloperId() {
        return developerId; }
    public double getPrice() {
        return price; }
    public double getSizeGB() {
        return sizeGB; }
    public int getAgeRestriction() {
        return ageRestriction; }
    public String getDescription() {
        return description; }
    public LocalDate getCreationDate() {
        return creationDate; }
    public boolean isApproved() {
        return approved; }
    public void setDescription(String description) {
        this.description = description; }
    public void setApproved(boolean approved) {
        this.approved = approved; }
    public int getDiscount() {
        return 0; }

    @Override
    public String toString() {
        return String.format("""
                \n\033[1;34m=== ОБ ЭТОЙ ИГРЕ ===\033[0m
                Name: %s
                Price: $%.2f
                Size: %.1f GB
                Age Restriction: %d+
                Created on: %s
                Approved: %s
                
                \033[1;33mОписание:\033[0m %s
                """, name, price, sizeGB, ageRestriction, creationDate, approved ? "Yes" : "No", description);
    }
}
