package com.gamelibrary.application;

import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.databases.Database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public DatabaseInitializer(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public void initializeDatabase() {
        try (Connection connection = gameRepository.getDatabaseManager().getConnection();
             BufferedReader reader = new BufferedReader(new InputStreamReader(
                     getClass().getClassLoader().getResourceAsStream("scheme.sql")));
             Statement statement = connection.createStatement()) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            statement.execute(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Database(userRepository).loadUsers();
    }
}