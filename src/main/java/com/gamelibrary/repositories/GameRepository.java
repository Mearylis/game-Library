package com.gamelibrary.repositories;

import com.gamelibrary.models.Game;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {
    private List<Game> games = new ArrayList<>();

    public GameRepository() {
        // Add some sample games for testing
        games.add(new Game(1, "Game 1", 1, 20.0, LocalDate.now(), true));
        games.add(new Game(2, "Game 2", 2, 30.0, LocalDate.now(), true));
    }

    // Add a game to the repository
    public void addGame(Game game) {
        games.add(game);
    }

    // Get all games
    public List<Game> getAllGames() {
        return games;
    }

    // Find a game by its ID
    public Game getGameById(int id) {
        return games.stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Approve a game by its ID
    public void approveGame(int id) {
        Game game = getGameById(id);
        if (game != null) {
            game.setApproved(true);
        }
    }

    // Delete a game by its ID
    public void deleteGame(int id) {
        games.removeIf(g -> g.getId() == id);
    }
}
