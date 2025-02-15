package com.gamelibrary.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private double balance;
    private int age; // New field
    private List<Game> cart = new ArrayList<>();
    private List<Game> purchasedGames = new ArrayList<>();

    // Constructor for User with age
    public User(int id, String username, String password, String role, double balance, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.age = age;
    }

    // Constructor without age (for backward compatibility or specific cases)
    public User(int id, String username) {
        this.id = id;
        this.username = username;
        this.password = "";
        this.role = "USER";
        this.balance = 0.0;
        this.age = 0; // Default value for age
    }

    // Getters and setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public double getBalance() { return balance; }
    public int getAge() { return age; } // Getter for age
    public List<Game> getCart() { return cart; }
    public List<Game> getPurchasedGames() { return purchasedGames; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setAge(int age) { this.age = age; } // Setter for age
}
