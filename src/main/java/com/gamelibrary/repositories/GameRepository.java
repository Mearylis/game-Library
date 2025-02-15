package com.gamelibrary.repositories;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.Review;
import com.gamelibrary.models.SystemRequirements;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {
    private final Connection connection;

    public GameRepository(Connection connection) {
        this.connection = connection;
    }

    public void addGame(Game game) {
        String query = "INSERT INTO games (name, developer_id, price, size_gb, age_restriction, genre, description, approved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, game.getName());
            stmt.setInt(2, game.getDeveloperId());
            stmt.setDouble(3, game.getPrice());
            stmt.setDouble(4, game.getSizeGB());
            stmt.setInt(5, game.getAgeRestriction());
            stmt.setString(6, game.getGenre());
            stmt.setString(7, game.getDescription());
            stmt.setBoolean(8, false);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Game getGameById(int id) {
        String query = "SELECT * FROM games WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Game(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("developer_id"),
                        rs.getDouble("price"),
                        rs.getDouble("size_gb"),
                        rs.getInt("age_restriction"),
                        rs.getString("genre"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        String query = "SELECT * FROM games";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                games.add(new Game(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("developer_id"),
                        rs.getDouble("price"),
                        rs.getDouble("size_gb"),
                        rs.getInt("age_restriction"),
                        rs.getString("genre"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public void deleteGame(int gameId) {
        String query = "DELETE FROM games WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGame(Game game) {
        String query = "UPDATE games SET name = ?, developer_id = ?, price = ?, size_gb = ?, age_restriction = ?, genre = ?, description = ?, approved = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, game.getName());
            stmt.setInt(2, game.getDeveloperId());
            stmt.setDouble(3, game.getPrice());
            stmt.setDouble(4, game.getSizeGB());
            stmt.setInt(5, game.getAgeRestriction());
            stmt.setString(6, game.getGenre());
            stmt.setString(7, game.getDescription());
            stmt.setBoolean(8, game.isApproved());
            stmt.setInt(9, game.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SystemRequirements getMinimumRequirements(int gameId) {
        return new SystemRequirements(
                "Windows 7 Sp1 - 8 - 8.1 - 10",
                "AMD Athlon X2 2.8 GHZ, Intel Core 2 Duo 2.4 GHZ",
                "2 GB ОЗУ",
                "DirectX 10.1 (e.g. AMD Radeon HD 6450, Nvidia GeForce GT 460)",
                "версии 11",
                "Широкополосное подключение к интернету",
                "15 GB",
                "Integrated",
                "SteamVR or Oculus PC. Keyboard or gamepad required"
        );
    }

    public SystemRequirements getRecommendedRequirements(int gameId) {
        return new SystemRequirements(
                "Windows 7 Sp1 - 8 - 8.1 - 10",
                "AMD Six-Core CPU, Intel Quad-Core CPU",
                "6 GB ОЗУ",
                "DirectX 11 (e.g. AMD Radeon 290x, Nvidia GeForce GTX 970)",
                "версии 11",
                "Широкополосное подключение к интернету",
                "30 GB",
                "Integrated",
                "С 1 января 2024 года клиент Steam поддерживает только Windows 10 и более поздние версии"
        );
    }

    public List<Review> getReviewsForGame(int gameId) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM reviews WHERE game_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("id"),
                        rs.getInt("game_id"),
                        rs.getInt("user_id"),
                        rs.getString("review"),
                        rs.getInt("rating"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void saveReview(int gameId, int userId, String text, int rating) {
        String query = "INSERT INTO reviews (game_id, user_id, review, rating) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            stmt.setInt(2, userId);
            stmt.setString(3, text);
            stmt.setInt(4, rating);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
