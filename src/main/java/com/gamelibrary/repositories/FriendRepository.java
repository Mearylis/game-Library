package com.gamelibrary.repositories;

import com.gamelibrary.databases.Database;
import com.gamelibrary.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRepository {
    private final Database database;

    public FriendRepository(Database database) {
        this.database = database;
    }

    public boolean sendFriendRequest(int senderId, int receiverId) {
        try (Connection conn = database.getConnection()) {
            String checkQuery = "SELECT * FROM friends WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, senderId);
                checkStmt.setInt(2, receiverId);
                checkStmt.setInt(3, receiverId);
                checkStmt.setInt(4, senderId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) return false;
            }

            String query = "INSERT INTO friends (sender_id, receiver_id, status) VALUES (?, ?, 'pending')";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, senderId);
                stmt.setInt(2, receiverId);
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error sending friend request: " + e.getMessage());
            return false;
        }
    }

    public List<User> getPendingRequests(int userId) {
        List<User> requests = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            String query = "SELECT u.id, u.username FROM users u JOIN friends f ON u.id = f.sender_id WHERE f.receiver_id = ? AND f.status = 'pending'";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    requests.add(new User(rs.getInt("id"), rs.getString("username")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching friend requests: " + e.getMessage());
        }
        return requests;
    }

    public boolean acceptFriendRequest(int senderId, int receiverId) {
        try (Connection conn = database.getConnection()) {
            String query = "UPDATE friends SET status = 'accepted' WHERE sender_id = ? AND receiver_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, senderId);
                stmt.setInt(2, receiverId);
                int updatedRows = stmt.executeUpdate();
                return updatedRows > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error accepting friend request: " + e.getMessage());
            return false;
        }
    }

    public boolean declineFriendRequest(int senderId, int receiverId) {
        try (Connection conn = database.getConnection()) {
            String query = "DELETE FROM friends WHERE sender_id = ? AND receiver_id = ? AND status = 'pending'";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, senderId);
                stmt.setInt(2, receiverId);
                int deletedRows = stmt.executeUpdate();
                return deletedRows > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error declining friend request: " + e.getMessage());
            return false;
        }
    }

    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            String query = """
                SELECT u.id, u.username FROM users u
                JOIN friends f ON (u.id = f.sender_id OR u.id = f.receiver_id)
                WHERE (f.sender_id = ? OR f.receiver_id = ?) AND f.status = 'accepted'
            """;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    friends.add(new User(rs.getInt("id"), rs.getString("username")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching friends: " + e.getMessage());
        }
        return friends;
    }
}