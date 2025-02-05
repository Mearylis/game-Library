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
            connection = DriverManager.getConnection("jdbc:h2:mem:gamelibrary;DB_CLOSE_DELAY=-1", "sa", "");
            createTables();
            populateData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), role VARCHAR(255), balance DOUBLE, banned BOOLEAN)");
            stmt.execute("CREATE TABLE IF NOT EXISTS games (id INT PRIMARY KEY, name VARCHAR(255), developerId INT, price DOUBLE, creationDate DATE, approved BOOLEAN)");
            stmt.execute("CREATE TABLE IF NOT EXISTS carts (userId INT, gameId INT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS purchases (id INT PRIMARY KEY, userId INT, gameId INT, purchaseDate DATE)");
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