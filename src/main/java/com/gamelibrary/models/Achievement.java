package com.gamelibrary.models;

public class Achievement {
    private int id;
    private int userId;
    private int gameId;
    private String description;

    public Achievement(int id, int userId, int gameId, String description) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getGameId() {
        return gameId;
    }

    public String getDescription() {
        return description;
    }
}