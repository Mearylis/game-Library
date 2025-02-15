package com.gamelibrary.validators;

import com.gamelibrary.utils.ValidationUtils;

public class CardValidator {
    public static boolean isValid(String cardNumber) {
        return ValidationUtils.isValidCardNumber(cardNumber);
    }
}