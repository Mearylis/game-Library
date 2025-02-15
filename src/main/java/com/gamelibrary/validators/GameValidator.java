package com.gamelibrary.validators;

public class GameValidator {
    public static boolean validateGame(String name, double price, double sizeGB, int ageRestriction) {
        return name != null && !name.isEmpty() && price > 0 && sizeGB > 0 && ageRestriction >= 0;
    }
}