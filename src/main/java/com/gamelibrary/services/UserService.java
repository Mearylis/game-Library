package com.gamelibrary.services;
import com.gamelibrary.models.Role;

import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(int id, String username, String password, String role) {
        userRepository.addUser(new User(id, username, password, Role.valueOf(role.toUpperCase()), 0.0, false));
    }

    public User login(String username, String password) {
        return userRepository.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void topUpBalance(int userId, double amount, String accountType) {
        User user = userRepository.getUserById(userId);
        if (user == null) return;

        double fee = accountType.equalsIgnoreCase("kaspi") ? 0.10 : 0.05;
        user.setBalance(user.getBalance() + (amount - (amount * fee)));
        userRepository.updateUser(user);
    }
}
