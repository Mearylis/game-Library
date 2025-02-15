package com.gamelibrary.repositories;

import com.gamelibrary.models.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatRepository {
    private final Connection connection;

    public ChatRepository(Connection connection) {
        this.connection = connection;
    }

    // 📌 1️⃣ Отправка сообщений в БД
    public void sendMessage(int senderId, int gameId, String text) {
        String query = "INSERT INTO messages (sender_id, game_id, text) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, gameId);
            stmt.setString(3, text);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Error sending message: " + e.getMessage());
        }
    }

    // 📌 2️⃣ Получение истории чата с именем отправителя
    public List<Message> getMessagesByGame(int gameId) {
        List<Message> messages = new ArrayList<>();
        String query = """
            SELECT messages.id, messages.sender_id, users.username, messages.game_id, messages.text, messages.timestamp
            FROM messages
            JOIN users ON messages.sender_id = users.id
            WHERE messages.game_id = ?
            ORDER BY messages.timestamp ASC
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("id"),
                        rs.getInt("sender_id"),
                        rs.getString("username"),
                        rs.getInt("game_id"),
                        rs.getString("text"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching messages: " + e.getMessage());
        }

        return messages;
    }
}
