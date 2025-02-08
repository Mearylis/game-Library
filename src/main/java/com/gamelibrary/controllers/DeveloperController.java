package com.gamelibrary.controllers;

import com.gamelibrary.models.Game;
import com.gamelibrary.services.DeveloperService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    public void createGame(Game game) {
        developerService.createGame(game);
    }

    public void viewGamesByDeveloper(int developerId) {
        List<Game> games = developerService.viewGamesByDeveloper(developerId);
        games.forEach(game -> System.out.println("ID: " + game.getId() + ", Name: " + game.getName()));
    }

    public void deleteGame(int gameId, int developerId) {
        developerService.deleteGame(gameId, developerId);
    }
}