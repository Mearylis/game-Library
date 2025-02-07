package com.gamelibrary.factories;

import com.gamelibrary.models.Game;

public class GameFactory {
    public static Game createGame(int id, String name, int developerId, double price, double sizeGB, int ageRestriction, String genre, String description) {
        return new Game(id, name, developerId, price, sizeGB, ageRestriction, genre, description);
    }
}