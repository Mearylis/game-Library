package com.gamelibrary.controllers;

import com.gamelibrary.exceptions.InsufficientBalanceException;
import com.gamelibrary.exceptions.InvalidCardException;
import com.gamelibrary.exceptions.AgeRestrictionException;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.FriendRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.services.UserService;
import com.gamelibrary.validators.UserValidator;

import java.util.List;

public class UserController {
    private final UserService userService;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public UserController(UserService userService, FriendRepository friendRepository, UserRepository userRepository) {
        this.userService = userService;
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public void registerUser(int id, String username, String password, String role, int age) {
        if (!UserValidator.validate(username, password)) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        User user = new User(id, username, password, role, 0.0, age);
        userRepository.addUser(user);
    }

    public User login(String username, String password) {
        return userService.login(username, password);
    }

    public void updateUsername(int userId, String newUsername) {
        userService.updateUsername(userId, newUsername);
    }

    public void addToCart(int userId, Game game) throws AgeRestrictionException {
        try {
            userService.addToCart(userId, game);
            System.out.println("Game added to cart!");
        } catch (AgeRestrictionException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding game to cart: " + e.getMessage());
        }
    }

    public void removeFromCart(int userId, int gameId) {
        userService.removeFromCart(userId, gameId);
    }

    public List<Game> getCart(int userId) {
        return userService.getCart(userId);
    }

    public void addFunds(int userId, double amount, String cardNumber) throws InvalidCardException {
        userService.addFunds(userId, amount, cardNumber);
    }

    public void purchaseGames(int userId) throws InsufficientBalanceException {
        userService.purchaseGames(userId);
    }

    public List<Game> getPurchasedGames(int userId) {
        return userService.getPurchasedGames(userId);
    }

    public boolean sendFriendRequest(int userId, int friendId) {
        return friendRepository.sendFriendRequest(userId, friendId);
    }

    public List<User> getPendingRequests(int userId) {
        return friendRepository.getPendingRequests(userId);
    }

    public boolean acceptFriendRequest(int senderId, int receiverId) {
        return friendRepository.acceptFriendRequest(senderId, receiverId);
    }

    public int getUserIdByUsername(String username) {
        for (User user : userRepository.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user.getId();
            }
        }
        return -1;
    }

    public boolean declineFriendRequest(int senderId, int receiverId) {
        return friendRepository.declineFriendRequest(senderId, receiverId);
    }

    public List<User> getFriends(int userId) {
        return friendRepository.getFriends(userId);
    }
}
