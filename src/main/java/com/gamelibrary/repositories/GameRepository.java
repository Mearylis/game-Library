package com.gamelibrary.repositories;

import com.gamelibrary.databases.DatabaseManager;
import com.gamelibrary.models.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {
    private final DatabaseManager databaseManager;

    public GameRepository() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public void addGame(Game game) {
        String query = "INSERT INTO games (name, price, approved, developer_id, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, game.getName());
            statement.setDouble(2, game.getPrice());
            statement.setBoolean(3, game.isApproved());
            statement.setInt(4, game.getDeveloperId());
            statement.setString(5, game.getCategory());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Game getGameById(int id) {
        String query = "SELECT * FROM games WHERE id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Game(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("price"),
                            resultSet.getBoolean("approved"),
                            resultSet.getInt("developer_id"),
                            resultSet.getString("category")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        String query = "SELECT * FROM games";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                games.add(new Game(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("approved"),
                        resultSet.getInt("developer_id"),
                        resultSet.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public void approveGame(int gameId) {
        String query = "UPDATE games SET approved = TRUE WHERE id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGame(int gameId) {
        String query = "DELETE FROM games WHERE id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}