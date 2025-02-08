package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public UserService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public void registerUser(User user) {
        userRepository.addUser(user);
    }

    public User loginUser(String username, String password) {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public User loginAdmin(String username, String password) {
        User admin = userRepository.getAllUsers().stream()
                .filter(user -> user.getRole() == Role.ADMIN)
                .findFirst().orElse(null);
        if (admin != null && admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    public List<Game> viewAllGames() {
        return gameRepository.getAllGames().stream()
                .filter(Game::isApproved)
                .toList();
    }

    public void addToCart(User user, Game game) {
        user.getCart().add(game);
    }

    public void removeFromCart(User user, int gameId) {
        user.getCart().removeIf(game -> game.getId() == gameId);
    }

    public void purchaseGames(User user) {
        List<Game> cart = user.getCart();
        double total = cart.stream().mapToDouble(Game::getPrice).sum();
        if (user.deductBalance(total)) {
            user.getPurchasedGames().addAll(cart);
            cart.clear();
        }
    }

    public void topUpBalance(User user, double amount, String cardType) {
        user.topUpBalance(amount);
    }
}