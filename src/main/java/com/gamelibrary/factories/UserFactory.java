package com.gamelibrary.factories;

import com.gamelibrary.models.User;

public class UserFactory {
    public static User createUser(int id, String username, String password, String role, double balance) {
        return new User(id, username, password, role, balance);
    }
}