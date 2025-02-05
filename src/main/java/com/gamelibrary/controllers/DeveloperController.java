package com.gamelibrary.controllers;

import com.gamelibrary.models.Game;
import com.gamelibrary.services.DeveloperService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    public void createGame(int id, String name, int developerId, double price) {
        developerService.createGame(id, name, developerId, price);
    }

    public List<Game> getGamesByDeveloper(int developerId) {
        return developerService.getGamesByDeveloper(developerId);
    }

    public void deleteGame(int gameId, int developerId) {
        developerService.deleteGame(gameId, developerId);
    }
}