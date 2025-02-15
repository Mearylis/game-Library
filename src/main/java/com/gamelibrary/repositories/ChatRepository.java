package com.gamelibrary.repositories;

import com.gamelibrary.models.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatRepository {
    private final Connection connection;
    private static final int DEFAULT_LIMIT = 50;

    public ChatRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Message> getLastMessagesByGame(int gameId) {
        return getLastMessagesByGame(gameId, DEFAULT_LIMIT);
    }

    public List<Message> getLastMessagesByGame(int gameId, int limit) {
        List<Message> messages = new ArrayList<>();
        String query = """
            SELECT messages.id, users.username, messages.text, messages.timestamp
            FROM messages
            JOIN users ON messages.sender_id = users.id
            WHERE messages.game_id = ?
            ORDER BY messages.timestamp DESC
            LIMIT ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("id"),
                        rs.getString("username"),
                        gameId,
                        rs.getString("text"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching messages: " + e.getMessage());
        }

        return messages;
    }

    public void sendMessage(int senderId, int gameId, String text) {
        String query = "INSERT INTO messages (sender_id, game_id, text) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, gameId);
            stmt.setString(3, text);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
}