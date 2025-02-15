package com.gamelibrary.repositories;

import com.gamelibrary.models.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameRepository {
    private final List<Game> games;

    public GameRepository() {
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void deleteGame(int gameId) {
        games.removeIf(game -> game.getId() == gameId);
    }

    public Game getGameById(int id) {
        return games.stream().filter(game -> game.getId() == id).findFirst().orElse(null);
    }

    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    public List<Game> getGamesByDeveloper(int developerId) {
        return games.stream()
                .filter(game -> game.getDeveloperId() == developerId)
                .collect(Collectors.toList());
    }

    public void approveGame(int gameId) {

    }
}
