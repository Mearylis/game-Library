package com.gamelibrary.repositories;

import com.gamelibrary.models.Game;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {
    private List<Game> games = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
    }

    public void approveGame(int gameId) {
        games.stream()
                .filter(game -> game.getId() == gameId)
                .findFirst()
                .ifPresent(game -> game.setApproved(true));
    }

    public void deleteGame(int gameId) {
        games.removeIf(game -> game.getId() == gameId);
    }

    public List<Game> getAllGames() {
        return games;
    }
}
