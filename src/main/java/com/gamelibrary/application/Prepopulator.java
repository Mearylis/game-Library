package com.gamelibrary.application;

import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.UserRepository;

public class Prepopulator {
    private final UserRepository userRepository;

    public Prepopulator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void prepopulate() {
        userRepository.addUser(new User(1, "admin", "adminpass", Role.ADMIN, 200.0, false));
    }
}