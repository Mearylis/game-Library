package com.gamelibrary.repositories;

import com.gamelibrary.models.User;

import java.util.ArrayList;
import java.util.List;

public class FriendRepository {
    private List<User> friends = new ArrayList<>();

    public List<User> getFriendsPlayingGame(int userId, int gameId) {
        // Logic to get friends playing the game
        return friends;
    }
}