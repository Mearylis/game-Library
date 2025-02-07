package com.gamelibrary.repositories;

import com.gamelibrary.models.Game;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GameRepository {
    private List<Game> games = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
    }

    public void approveGame(int gameId) {
        for (Game game : games) {
            if (game.getId() == gameId) {
                game.setApproved(true);
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

    public List<Game> getAllGames() {
        return games;
    }

    // Добавление рейтинга к игре
    public void rateGame(int gameId, int rating) {
        Game game = games.stream()
                .filter(g -> g.getId() == gameId)
                .findFirst()
                .orElse(null);

        if (game != null) {
            game.addRating(rating);
            System.out.println("Rating added for game: " + game.getName());
        } else {
            System.out.println("Game not found.");
        }
    }

    // Получение списка топ-игр по рейтингу
    public List<Game> getTopRatedGames(int topN) {
        return games.stream()
                .sorted(Comparator.comparingDouble(Game::getAverageRating).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }
}
