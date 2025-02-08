// Database.java
package com.gamelibrary.databases;

import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final UserRepository userRepository;
    private static final String URL = "jdbc:postgresql://localhost:5432/gamelibrary";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public Database(UserRepository userRepository) {
        this.userRepository = userRepository;
        loadUsers();
        if (userRepository.getAllUsers().isEmpty()) {
            initializeUsers();
        }
    }

    public void initializeUsers() {
        userRepository.addUser(new User(1, "admin", "adminpass", Role.ADMIN, 100.0, false));
        userRepository.addUser(new User(2, "developer", "devpass", Role.DEVELOPER, 50.0, false));
        userRepository.addUser(new User(3, "user", "userpass", Role.USER, 25.0, false));
        saveUsers();
    }

    private void saveUsers() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false); // Отключить авто-коммит
            String query = "INSERT INTO users (id, username, password, role, balance, banned) VALUES (?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT (id) DO NOTHING"; // Пропустить вставку, если пользователь уже существует
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (User user : userRepository.getAllUsers()) {
                    statement.setInt(1, user.getId());
                    statement.setString(2, user.getUsername());
                    statement.setString(3, user.getPassword());
                    statement.setString(4, user.getRole().name());
                    statement.setDouble(5, user.getBalance());
                    statement.setBoolean(6, user.isBanned());
                    statement.executeUpdate();
                }
                connection.commit(); // Зафиксировать изменения
            } catch (SQLException e) {
                connection.rollback(); // Откатить в случае ошибки
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUsers() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM users";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Role role = Role.valueOf(resultSet.getString("role"));
                    double balance = resultSet.getDouble("balance");
                    boolean banned = resultSet.getBoolean("banned");
                    users.add(new User(id, username, password, role, balance, banned));
                }
                users.forEach(userRepository::addUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}