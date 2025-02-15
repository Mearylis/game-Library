package com.gamelibrary.repositories;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    // Add a new user with age
    public void addUser(User user) {
        String query = "INSERT INTO users (id, username, password, role, balance, age) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setDouble(5, user.getBalance());
            stmt.setInt(6, user.getAge()); // Add age
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a user by their ID
    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDouble("balance"),
                        rs.getInt("age") // Retrieve age
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDouble("balance"),
                        rs.getInt("age") // Retrieve age
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Update an existing user's details
    public void updateUser(User user) {
        String query = "UPDATE users SET username = ?, password = ?, role = ?, balance = ?, age = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setDouble(4, user.getBalance());
            stmt.setInt(5, user.getAge()); // Update age
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a game to the user's cart
    public void addGameToCart(int userId, int gameId) {
        String query = "INSERT INTO cart (user_id, game_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove a game from the user's cart
    public void removeGameFromCart(int userId, int gameId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND game_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve the user's cart
    public List<Game> getCart(int userId) {
        List<Game> cart = new ArrayList<>();
        String query = "SELECT g.* FROM games g JOIN cart c ON g.id = c.game_id WHERE c.user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cart.add(new Game(
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
        return cart;
    }

    // Clear the user's cart
    public void clearCart(int userId) {
        String query = "DELETE FROM cart WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add purchased games to the user's account
    public void addPurchasedGames(int userId, List<Game> purchasedGames) {
        String query = "INSERT INTO purchased_games (user_id, game_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Game game : purchasedGames) {
                stmt.setInt(1, userId);
                stmt.setInt(2, game.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve the list of purchased games for a user
    public List<Game> getPurchasedGames(int userId) {
        List<Game> purchasedGames = new ArrayList<>();
        String query = "SELECT g.* FROM games g JOIN purchased_games pg ON g.id = pg.game_id WHERE pg.user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                purchasedGames.add(new Game(
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
        return purchasedGames;
    }
}
