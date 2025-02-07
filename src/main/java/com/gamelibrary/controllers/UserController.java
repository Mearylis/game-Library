package com.gamelibrary.controllers;

import com.gamelibrary.models.*;
import com.gamelibrary.services.UserService;
import com.gamelibrary.repositories.GameRepository;

import java.util.List;

public class UserController {
    private final UserService userService;
    private final GameRepository gameRepository;

    public UserController(UserService userService, GameRepository gameRepository) {
        this.userService = userService;
        this.gameRepository = gameRepository;
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

    public void topUpBalance(int userId, String cardNumber) {
        userService.topUpBalance(userId, cardNumber);
    }

    public void addBalance(int userId, double amount) {
        userService.addBalance(userId, amount);
    }


    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    public List<Purchase> getPurchasedGames(int userId) {
        return userService.getPurchasedGames(userId);
    }
}
