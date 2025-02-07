package com.gamelibrary.controllers;

import com.gamelibrary.models.Game;
import com.gamelibrary.services.DeveloperService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    public int createGame(String name, int developerId, double price, double sizeGB, int ageRestriction, String genre, String description) {
        return developerService.createGame(name, developerId, price, sizeGB, ageRestriction, genre, description);
    }

    public List<Game> getGamesByDeveloper(int developerId) {
        return developerService.getGamesByDeveloper(developerId);
    }

    public void deleteGame(int gameId, int developerId) {
        developerService.deleteGame(gameId, developerId);
    }

    public double getEarnings(int developerId) {
        return developerService.getEarnings(developerId);
    }
}