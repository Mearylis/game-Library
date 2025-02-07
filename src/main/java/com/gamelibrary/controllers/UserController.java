package com.gamelibrary.controllers;

import com.gamelibrary.models.Role;

import com.gamelibrary.services.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(int id, String username, String password, String role) {
        userService.registerUser(id, username, password, role);
    }

    public void login(String username, String password) {
        userService.login(username, password);
    }

    public void topUpBalance(int userId, double amount, String accountType) {
        userService.topUpBalance(userId, amount, accountType);
    }
}
