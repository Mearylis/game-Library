// src/main/java/com/gamelibrary/repositories/UserRepository.java
package com.gamelibrary.repositories;

import com.gamelibrary.models.User;
import com.gamelibrary.models.Role;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public User getUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void banUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            user.setBanned(true);
        }
    }

    public void unbanUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            user.setBanned(false);
        }
    }

    public void deleteUser(int id) {
        users.removeIf(u -> u.getId() == id);
    }
}