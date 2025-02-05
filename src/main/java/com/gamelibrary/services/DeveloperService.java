package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.repositories.GameRepository;

import java.time.LocalDate;
import java.util.List;

public class DeveloperService {
    private final GameRepository gameRepository;

    public DeveloperService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void createGame(int id, String name, int developerId, double price) {
        Game game = new Game(id, name, developerId, price, LocalDate.now(), false);
        gameRepository.addGame(game);
    }

    public List<Game> getGamesByDeveloper(int developerId) {
        return gameRepository.getAllGames().stream().filter(g -> g.getDeveloperId() == developerId).toList();
    }

    public void deleteGame(int gameId, int developerId) {
        Game game = gameRepository.getGameById(gameId);
        if (game != null && game.getDeveloperId() == developerId) {
            gameRepository.deleteGame(gameId);
        }
    }
}