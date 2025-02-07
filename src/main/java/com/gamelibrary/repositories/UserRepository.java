package com.gamelibrary.repositories;

import com.gamelibrary.models.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void banUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            user.setBanned(true);
            System.out.println("User " + user.getUsername() + " (ID " + id + ") banned.");
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }

    public void unbanUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            user.setBanned(false);
            System.out.println("User " + user.getUsername() + " (ID " + id + ") unbanned.");
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                System.out.println("User (ID=" + user.getId() + ") updated.");
                return;
            }
        }
        System.out.println("User (ID=" + user.getId() + ") not found. Update failed.");
    }

}
