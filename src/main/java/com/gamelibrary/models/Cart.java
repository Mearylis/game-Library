// src/main/java/com/gamelibrary/models/Cart.java
package com.gamelibrary.models;

public class Cart {
    private int userId;
    private int gameId;

    public Cart(int userId, int gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public int getGameId() {
        return gameId;
    }
}