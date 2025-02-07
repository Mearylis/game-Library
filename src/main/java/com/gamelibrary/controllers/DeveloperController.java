package com.gamelibrary.controllers;

import com.gamelibrary.services.DeveloperService;

public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    // This method now calls the new createGame method in DeveloperService
    public void createGame(int id, String name, int developerId, double price) {
        developerService.createGame(id, name, developerId, price);
    }

    public void viewGamesByDeveloper(int developerId) {
        developerService.viewGamesByDeveloper(developerId);
    }
}
