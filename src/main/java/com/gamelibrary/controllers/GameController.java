package com.gamelibrary.controllers;

import com.gamelibrary.models.Game;
import com.gamelibrary.services.GameService;

import java.util.List;

public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public void addGame(int id, String name, int developerId, double price) {
        gameService.addGame(id, name, developerId, price);
    }

    public Game getGameById(int id) {
        return gameService.getGameById(id);
    }

    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    public void approveGame(int id) {
        gameService.approveGame(id);
    }

    public void deleteGame(int id) {
        gameService.deleteGame(id);
    }
}