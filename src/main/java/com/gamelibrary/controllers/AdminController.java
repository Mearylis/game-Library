package com.gamelibrary.controllers;

import com.gamelibrary.services.AdminService;

public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public void approveGame(int gameId) {
        adminService.approveGame(gameId);
    }

    public void deleteGame(int gameId) {
        adminService.deleteGame(gameId);
    }

    public void banUser(int userId) {
        adminService.banUser(userId);
    }

    public void unbanUser(int userId) {
        adminService.unbanUser(userId);
    }

    public void deleteUser(int userId) {
        adminService.deleteUser(userId);
    }

    public void viewAllUsers() {
        adminService.getAllUsers().forEach(user -> System.out.println("ID: " + user.getId() + ", Name: " + user.getUsername()));
    }

    public void viewAllGames() {
        adminService.getAllGames().forEach(game -> System.out.println("ID: " + game.getId() + ", Name: " + game.getName()));
    }
}