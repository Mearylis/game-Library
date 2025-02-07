package com.gamelibrary.controllers;

import com.gamelibrary.models.*;
import com.gamelibrary.services.UserService;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(int id, String username, String password, Role role) {
        userService.registerUser(id, username, password, role);
    }

    public User login(String username, String password) {
        return userService.login(username, password);
    }

    public void addToCart(int userId, int gameId) {
        userService.addToCart(userId, gameId);
    }

    public List<Game> getCartGames(int userId) {
        return userService.getCartGames(userId);
    }

    public void purchaseGames(int userId) {
        userService.purchaseGames(userId);
    }

    public void removeFromCart(int userId, int gameId) {
        userService.removeFromCart(userId, gameId);
    }

    public void topUpBalance(int userId, double amount, String cardNumber, String cardExpiryDate, String cardCVV) {
        userService.topUpBalance(userId, amount, cardNumber, cardExpiryDate, cardCVV);
    }

    public List<Purchase> getPurchasedGames(int userId) {
        return userService.getPurchasedGames(userId);
    }

    public double getUserBalance(int userId) {
        return userService.getUserBalance(userId);
    }

    public List<Game> getAllGames() {
        return userService.getAllGames();
    }
}