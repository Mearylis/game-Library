package com.gamelibrary.models;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String senderName;
    private int gameId;
    private String text;
    private LocalDateTime timestamp;

    public Message(int id, String senderName, int gameId, String text, LocalDateTime timestamp) {
        this.id = id;
        this.senderName = senderName;
        this.gameId = gameId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public int getGameId() {
        return gameId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}