package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;

import java.util.List;

public class AdminService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public AdminService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public void approveGame(int gameId) {
        gameRepository.approveGame(gameId);
    }

    public void deleteGame(int gameId) {
        gameRepository.deleteGame(gameId);
    }

    public void banUser(int userId) {
        userRepository.banUser(userId);
    }

    public void unbanUser(int userId) {
        userRepository.unbanUser(userId);
    }

    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }
}