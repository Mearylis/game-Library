package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;

import java.util.List;

public class AdminService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public AdminService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public void banUser(int userId) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            user.setRole("BANNED");
            userRepository.updateUser(user);
        }
    }

    public void unbanUser(int userId) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            user.setRole("USER");
            userRepository.updateUser(user);
        }
    }

    public void approveGame(int gameId) {
        Game game = gameRepository.getGameById(gameId);
        if (game != null) {
            game.setApproved(true);
            gameRepository.updateGame(game);
        }
    }

    public void rejectGame(int gameId) {
        gameRepository.deleteGame(gameId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }
}