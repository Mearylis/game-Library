package com.gamelibrary.models;

public class User {
    private int id;
    private String username;
    private String password;
    private Role role;
    private double balance;
    private boolean banned;

    public User(int id, String username, String password, Role role, double balance, boolean banned) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.banned = banned;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
