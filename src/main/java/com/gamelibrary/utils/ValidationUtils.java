package com.gamelibrary.utils;

public class ValidationUtils {
    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 50;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.length() == 16 && cardNumber.matches("\\d+");
    }
}