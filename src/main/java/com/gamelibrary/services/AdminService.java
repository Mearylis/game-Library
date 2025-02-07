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
        System.out.println("Game with ID " + gameId + " approved.");
    }

    public void deleteGame(int gameId) {
        gameRepository.deleteGame(gameId);
        System.out.println("Game with ID " + gameId + " deleted.");
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    public void banUser(int userId) {
        userRepository.banUser(userId);
        System.out.println("User with ID " + userId + " banned.");
    }

    public void unbanUser(int userId) {
        userRepository.unbanUser(userId);
        System.out.println("User with ID " + userId + " unbanned.");
    }
}
