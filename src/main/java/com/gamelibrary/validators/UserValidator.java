package com.gamelibrary.validators;

import com.gamelibrary.utils.ValidationUtils;

public class UserValidator {
    public static boolean validate(String username, String password) {
        return ValidationUtils.isValidUsername(username) && ValidationUtils.isValidPassword(password);
    }
}