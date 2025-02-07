package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.repositories.GameRepository;

import java.time.LocalDate;
import java.util.List;

public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void addGame(int id, String name, int developerId, double price) {
        Game game = new Game(id, name, developerId, price, LocalDate.now(), false);
        gameRepository.addGame(game);
    }

    public Game getGameById(int id) {
        return gameRepository.getGameById(id);
    }

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    public void approveGame(int id) {
        gameRepository.approveGame(id);
    }

    public void deleteGame(int id) {
        gameRepository.deleteGame(id);
    }
}