package com.gamelibrary.models;

import java.time.LocalDate;

public class Purchase {
    private int id;
    private int userId;
    private int gameId;
    private LocalDate purchaseDate;

    public Purchase(int id, int userId, int gameId, LocalDate purchaseDate) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
        this.purchaseDate = purchaseDate;
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

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
}