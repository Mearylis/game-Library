package com.gamelibrary.models;

public class User {
    private int id;
    private String username;
    private String password;
    private Role role;
    private double balance;
    private boolean banned;
    private String cardNumber;
    private String cardExpiryDate; // Add this field
    private String cardCVV; // Add this field

    public User(int id, String username, String password, Role role, double balance, boolean banned) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.banned = banned;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}