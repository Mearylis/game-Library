// src/main/java/com/gamelibrary/models/User.java
package com.gamelibrary.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private Role role;
    private double balance;
    private boolean banned;
    private List<Game> cart;
    private List<Game> purchasedGames;

    public User(int id, String username, String password, Role role, double balance, boolean banned) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.banned = banned;
        this.cart = new ArrayList<>();
        this.purchasedGames = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public boolean isBanned() { return banned; }
    public void setBanned(boolean banned) { this.banned = banned; }

    public List<Game> getCart() { return cart; }
    public List<Game> getPurchasedGames() { return purchasedGames; }

    public boolean deductBalance(double amount) {
        if (balance < amount) {
            System.out.println("Insufficient balance. Current balance: $" + balance);
            return false;
        }
        balance -= amount;
        System.out.println("Deducted $" + amount + ". Remaining balance: $" + balance);
        return true;
    }

    public void topUpBalance(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid top-up amount. Must be positive.");
            return;
        }
        balance += amount;
        System.out.println("Successfully topped up: $" + amount + ". New balance: $" + balance);
    }
}