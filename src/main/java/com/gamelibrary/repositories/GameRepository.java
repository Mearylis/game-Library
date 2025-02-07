package com.gamelibrary.repositories;

import com.gamelibrary.models.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameRepository {
    private List<Game> games = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
    }

    public void approveGame(int gameId) {
        for (Game g : games) {
            if (g.getId() == gameId) {
                g.setApproved(true);
                System.out.println("Game with ID " + gameId + " approved.");
                return;
            }
        }
        System.out.println("Game with ID " + gameId + " not found.");
    }

    public void deleteGame(int gameId) {
        games.removeIf(game -> game.getId() == gameId);
        System.out.println("Deleted game with ID: " + gameId);
    }

    public List<Game> getGamesByCategory(String category) {
        return games.stream()
                .filter(g -> g.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Game> getAllGames() {
        return games;
    }
}
