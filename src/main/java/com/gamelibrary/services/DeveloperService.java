package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.repositories.GameRepository;

public class DeveloperService {
    private final GameRepository gameRepository;

    public DeveloperService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Existing method to add a game with all five parameters
    public void addGame(int id, String name, double price, boolean approved, int developerId) {
        Game game = new Game(id, name, price, approved, developerId);
        gameRepository.addGame(game);
        System.out.println("Game added successfully.");
    }

    // New method that creates a game with approved defaulting to false
    public void createGame(int id, String name, int developerId, double price) {
        addGame(id, name, price, false, developerId);
    }

    public void viewGamesByDeveloper(int developerId) {
        gameRepository.getAllGames().stream()
                .filter(game -> game.getDeveloperId() == developerId)
                .forEach(game -> System.out.println("Game: " + game.getName() + ", Price: $" + game.getPrice()));
    }
}
