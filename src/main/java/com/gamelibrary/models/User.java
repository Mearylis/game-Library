package com.gamelibrary.models;

public class User {
    private int id;
    private String username;
    private String password;
    private Role role;
    private double balance;
    private boolean banned;
    private String bankAccount; // New field for bank account details

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

    // Top-up balance applying fee based on bank account type:
    // - 16-digit number: Mastercard (5% fee)
    // - Starts with "KZ" and longer than 16 characters: Kaspi (10% fee)
    public void topUpBalance(double amount) {
        if (bankAccount == null || bankAccount.isEmpty()) {
            System.out.println("No bank account linked.");
            return;
        }
        double fee = 0;
        if (bankAccount.matches("\\d{16}")) { // Matches exactly 16 digits → Mastercard
            fee = 0.05;
            System.out.println("Using Mastercard (5% fee).");
        } else if (bankAccount.startsWith("KZ") && bankAccount.length() > 16) {
            fee = 0.10;
            System.out.println("Using Kaspi account (10% fee).");
        } else {
            System.out.println("Invalid bank account format.");
            return;
        }
        double netAmount = amount - (amount * fee);
        this.balance += netAmount;
        System.out.println("Top-up successful. Amount added: " + netAmount + ". New balance: " + this.balance);
    }
}
