package com.gamelibrary.repositories;

import com.gamelibrary.models.Game;

import java.util.ArrayList;
import java.util.List;

public class GameRepository {
    private List<Game> games = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
    }

    public List<Game> getAllGames() {
        return games;
    }

    public Game getGameById(int id) {
        return games.stream().filter(g -> g.getId() == id).findFirst().orElse(null);
    }

    public void approveGame(int id) {
        Game game = getGameById(id);
        if (game != null) {
            game.setApproved(true);
        }
    }

    public void deleteGame(int id) {
        games.removeIf(g -> g.getId() == id);
    }
}