package com.gamelibrary.controllers;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.services.AdminService;
import java.util.List;

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

    public void viewAllUsers() {
        List<User> users = adminService.getAllUsers();
        for (User u : users) {
            System.out.println("ID: " + u.getId() + ", Username: " + u.getUsername() +
                    ", Role: " + u.getRole() + ", Balance: $" + u.getBalance() +
                    ", Banned: " + u.isBanned());
        }
    }

    public void viewAllGames() {
        List<Game> games = adminService.getAllGames();
        for (Game g : games) {
            System.out.println("ID: " + g.getId() + ", Name: " + g.getName() +
                    ", Price: $" + g.getPrice() + ", Approved: " + g.isApproved() +
                    ", DeveloperID: " + g.getDeveloperId() + ", Category: " + g.getCategory());
        }
    }
}
