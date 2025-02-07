package com.gamelibrary.databases;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.models.Role;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Database {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private Connection connection;

    public Database(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        initialize();
    }

    private void initialize() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/gamelibrary", "postgres", "1234");
            createTables();
            populateData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "username VARCHAR(255) UNIQUE NOT NULL, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "role VARCHAR(50) NOT NULL, " +
                    "balance DECIMAL(10,2) DEFAULT 0.00, " +
                    "banned BOOLEAN DEFAULT FALSE" +
                    ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS games (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "developerId INT REFERENCES users(id) ON DELETE CASCADE, " +
                    "price DECIMAL(10,2) DEFAULT 0.00, " +
                    "creationDate DATE DEFAULT CURRENT_DATE, " +
                    "approved BOOLEAN DEFAULT FALSE, " +
                    "lastPlayedDate DATE DEFAULT CURRENT_DATE" +
                    ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS carts (" +
                    "userId INT REFERENCES users(id) ON DELETE CASCADE, " +
                    "gameId INT REFERENCES games(id) ON DELETE CASCADE, " +
                    "PRIMARY KEY (userId, gameId)" +
                    ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS purchases (" +
                    "id SERIAL PRIMARY KEY, " +
                    "userId INT REFERENCES users(id) ON DELETE CASCADE, " +
                    "gameId INT REFERENCES games(id) ON DELETE CASCADE, " +
                    "purchaseDate DATE DEFAULT CURRENT_DATE" +
                    ")");
        }
    }

    private void populateData() {
        userRepository.addUser(new User(1, "admin", "admin", Role.ADMIN, 0.0, false));
        userRepository.addUser(new User(2, "dev1", "dev1", Role.DEVELOPER, 0.0, false));
        userRepository.addUser(new User(3, "user1", "user1", Role.USER, 100.0, false));

        gameRepository.addGame(new Game(1, "Game 1", 2, 29.99, LocalDate.now(), true));
        gameRepository.addGame(new Game(2, "Game 2", 2, 39.99, LocalDate.now(), false));
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}