package com.gamelibrary.controllers;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.services.UserService;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(User user) {
        userService.registerUser(user);
    }

    public User loginUser(String username, String password) {
        return userService.loginUser(username, password);
    }

    public User loginAdmin(String username, String password) {
        return userService.loginAdmin(username, password);
    }

    public List<Game> viewAllGames() {
        return userService.viewAllGames();
    }

    public void addToCart(User user, Game game) {
        userService.addToCart(user, game);
    }

    public void removeFromCart(User user, int gameId) {
        userService.removeFromCart(user, gameId);
    }

    public void purchaseGames(User user) {
        userService.purchaseGames(user);
    }
    public void topUpBalance(User user, double amount, String cardNumber) {
        if (cardNumber.length() != 16) {
            System.out.println("Error: Card number must be 16 digits.");
            return;
        }

        char firstDigit = cardNumber.charAt(0);
        double commissionRate;

        if (firstDigit == '4') {
            commissionRate = 0.05; // Kaspi card
        } else if (firstDigit == '3') {
            commissionRate = 0.10; // Visa card
        } else {
            System.out.println("Error: Invalid card type. Kaspi cards start with 4 and Visa cards start with 3.");
            return;
        }

        double commission = amount * commissionRate;
        double finalAmount = amount - commission;
        user.setBalance(user.getBalance() + finalAmount);
        System.out.println("Top-up successful. Commission: " + commission + ". Final amount added: " + finalAmount);
    }
}