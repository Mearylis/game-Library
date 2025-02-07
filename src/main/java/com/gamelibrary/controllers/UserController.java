package com.gamelibrary.controllers;

import com.gamelibrary.exceptions.InsufficientBalanceException;
import com.gamelibrary.exceptions.InvalidCardException;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.services.UserService;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(int id, String username, String password, String role) {
        userService.registerUser(id, username, password, role);
    }

    public User login(String username, String password) {
        return userService.login(username, password);
    }

    public void updateUsername(int userId, String newUsername) {
        userService.updateUsername(userId, newUsername);
    }

    public void addToCart(int userId, Game game) {
        userService.addToCart(userId, game);
    }

    public void removeFromCart(int userId, int gameId) {
        userService.removeFromCart(userId, gameId);
    }

    public List<Game> getCart(int userId) {
        return userService.getCart(userId);
    }

    public void addFunds(int userId, double amount, String cardNumber) throws InvalidCardException {
        userService.addFunds(userId, amount, cardNumber);
    }

    public void purchaseGames(int userId) throws InsufficientBalanceException {
        userService.purchaseGames(userId);
    }

    public List<Game> getPurchasedGames(int userId) {
        return userService.getPurchasedGames(userId);
    }
}