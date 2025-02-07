package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.repositories.GameRepository;
import java.util.List;
import java.util.stream.Collectors;

public class DeveloperService {
    private final GameRepository gameRepository;

    public DeveloperService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Create new game with default 'approved = false'
    public void createGame(int id, String name, int developerId, double price) {
        // Category defaulted to 'Uncategorized'
        Game game = new Game(id, name, price, false, developerId, "Uncategorized");
        gameRepository.addGame(game);
        System.out.println("Game created: " + name + " (ID: " + id + ")");
    }

    public List<Game> viewGamesByDeveloper(int developerId) {
        return gameRepository.getAllGames().stream()
                .filter(g -> g.getDeveloperId() == developerId)
                .collect(Collectors.toList());
    }
}
