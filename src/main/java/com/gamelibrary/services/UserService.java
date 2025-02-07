package com.gamelibrary.services;

import com.gamelibrary.models.User;
import com.gamelibrary.models.Role;
import com.gamelibrary.repositories.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to register a user (int ID, String username, String password, String role)
    public void registerUser(int id, String username, String password, String role) {
        // Convert role from String to enum Role
        Role userRole = Role.valueOf(role.toUpperCase());

        // Create a new User instance
        User newUser = new User(id, username, password, userRole, 0.0, false);

        // Add the user to the repository
        userRepository.addUser(newUser);
        System.out.println("User registered: " + username + " with role: " + role);
    }

    // Method to log in a user (String username, String password)
    public User login(String username, String password) {
        // Find the user in the repository based on username and password
        for (User user : userRepository.getAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful for user: " + username);
                return user;
            }
        }
        System.out.println("Invalid credentials for user: " + username);
        return null;
    }
}
