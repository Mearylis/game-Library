package com.gamelibrary.models;

public class OrderItem {
    private int gameId;
    private String gameName;
    private double gamePrice;

    public OrderItem(int gameId, String gameName, double gamePrice) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gamePrice = gamePrice;
    }

    public int getGameId() { return gameId; }
    public String getGameName() { return gameName; }
    public double getGamePrice() { return gamePrice; }
}
