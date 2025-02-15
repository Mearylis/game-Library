package com.gamelibrary.services;

import com.gamelibrary.exceptions.AgeRestrictionException;
import com.gamelibrary.exceptions.InsufficientBalanceException;
import com.gamelibrary.exceptions.InvalidCardException;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.utils.CardUtils;
import com.gamelibrary.validators.CardValidator;
import com.gamelibrary.validators.UserValidator;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public UserService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public void registerUser(int id, String username, String password, String role, int age) {
        if (!UserValidator.validate(username, password)) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        User user = new User(id, username, password, role, 0.0, age);
        userRepository.addUser(user);
    }

    public User login(String username, String password) {
        for (User user : userRepository.getAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void updateUsername(int userId, String newUsername) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            user.setUsername(newUsername);
            userRepository.updateUser(user);
        }
    }

    public void addToCart(int userId, Game game) throws AgeRestrictionException {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        if (user.getAge() < game.getAgeRestriction()) {
            throw new AgeRestrictionException("You do not meet the age requirement for this game.");
        }

        userRepository.addGameToCart(userId, game.getId());
    }

    public void removeFromCart(int userId, int gameId) {
        userRepository.removeGameFromCart(userId, gameId);
    }

    public List<Game> getCart(int userId) {
        return userRepository.getCart(userId);
    }

    public void addFunds(int userId, double amount, String cardNumber) throws InvalidCardException {
        if (!CardValidator.isValid(cardNumber)) {
            throw new InvalidCardException("Invalid card number.");
        }

        double amountWithCommission = CardUtils.applyCommission(amount);
        User user = userRepository.getUserById(userId);
        if (user != null) {
            user.setBalance(user.getBalance() + amountWithCommission);
            userRepository.updateUser(user);
        }
    }

    public void purchaseGames(int userId) throws InsufficientBalanceException {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            List<Game> cart = userRepository.getCart(userId);
            double total = cart.stream().mapToDouble(Game::getPrice).sum();
            System.out.printf("Current balance: $%.2f\n", user.getBalance());
            if (user.getBalance() >= total) {
                user.setBalance(user.getBalance() - total);
                userRepository.updateUser(user);
                userRepository.addPurchasedGames(userId, cart);
                userRepository.clearCart(userId);
                System.out.println("Purchase successful!");
            } else {
                throw new InsufficientBalanceException("Insufficient balance.");
            }
        }
    }

    public List<Game> getPurchasedGames(int userId) {
        return userRepository.getPurchasedGames(userId);
    }
}
