package com.gamelibrary.repositories;

import com.gamelibrary.models.User;
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

    public List<User> getAllUsers() {
        return users;
    }

    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                return;
            }
        }
    }

    public void updateBankAccount(int userId, String bankAccount) {
        User user = getUserById(userId);
        if (user != null) {
            user.setBankAccount(bankAccount);
            updateUser(user);
            System.out.println("Bank account updated for user " + user.getUsername());
        } else {
            System.out.println("User not found.");
        }
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
