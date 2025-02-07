package com.gamelibrary.databases;

import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.UserRepository;

public class Database {
    private final UserRepository userRepository;

    public Database(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void initializeUsers() {
        userRepository.addUser(new User(1, "admin", "adminpass", Role.ADMIN, 100.0, false));
        userRepository.addUser(new User(2, "developer", "devpass", Role.DEVELOPER, 50.0, false));
        userRepository.addUser(new User(3, "user", "userpass", Role.USER, 25.0, false));
    }
}
