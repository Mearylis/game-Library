package com.gamelibrary.controllers;

import com.gamelibrary.models.User;
import com.gamelibrary.services.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register a user by calling UserService.registerUser
    public void registerUser(int id, String username, String password, String role) {
        userService.registerUser(id, username, password, role);
    }

    // Login a user by calling UserService.login
    public User login(String username, String password) {
        return userService.login(username, password);
    }
}
