package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.repositories.GameRepository;

import java.util.List;

public class DeveloperService {
    private final GameRepository gameRepository;

    public DeveloperService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void createGame(Game game) {
        gameRepository.addGame(game);
    }

    public List<Game> viewGamesByDeveloper(int developerId) {
        return gameRepository.getAllGames().stream()
                .filter(game -> game.getDeveloperId() == developerId)
                .toList();
    }

    public void deleteGame(int gameId, int developerId) {
        Game game = gameRepository.getGameById(gameId);
        if (game != null && game.getDeveloperId() == developerId) {
            gameRepository.deleteGame(gameId);
        }
    }
}