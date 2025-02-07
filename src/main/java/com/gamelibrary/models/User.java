package com.gamelibrary.models;

public class User {
    private int id;
    private String username;
    private String password;
    private Role role;
    private double balance;
    private boolean banned;
    private String bankAccount;

    public User(int id, String username, String password, Role role, double balance, boolean banned) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.banned = banned;
        this.bankAccount = "";
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public double getBalance() { return balance; }
    public boolean isBanned() { return banned; }
    public String getBankAccount() { return bankAccount; }

    public void setBalance(double balance) { this.balance = balance; }
    public void setBanned(boolean banned) { this.banned = banned; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }

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
