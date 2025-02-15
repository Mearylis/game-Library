package com.gamelibrary.models;

import java.time.LocalDateTime;

public class Review {
    private int id;
    private int gameId;
    private int userId;
    private String text;
    private int rating;
    private LocalDateTime timestamp;

    public Review(int id, int gameId, int userId, String text, int rating, LocalDateTime timestamp) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
        this.text = text;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public int getGameId() { return gameId; }
    public int getUserId() { return userId; }
    public String getText() { return text; }
    public int getRating() { return rating; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("""
                \n\033[1;34m=== Отзыв ===\033[0m
                Рейтинг: %d/5
                Текст: %s
                Дата: %s
                """, rating, text, timestamp);
    }
}