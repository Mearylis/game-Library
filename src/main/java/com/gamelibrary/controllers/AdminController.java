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

    public void banUser(int userId) {
        adminService.banUser(userId);
    }

    public void unbanUser(int userId) {
        adminService.unbanUser(userId);
    }

    public void approveGame(int gameId) {
        adminService.approveGame(gameId);
    }

    public void rejectGame(int gameId) {
        adminService.rejectGame(gameId);
    }

    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    public List<Game> getAllGames() {
        return adminService.getAllGames();
    }
}