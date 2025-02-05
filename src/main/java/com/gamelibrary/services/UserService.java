package com.gamelibrary.services;

import com.gamelibrary.models.*;
import com.gamelibrary.repositories.CartRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.PurchaseRepository;
import com.gamelibrary.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final CartRepository cartRepository;
    private final PurchaseRepository purchaseRepository;

    public UserService(UserRepository userRepository, GameRepository gameRepository, CartRepository cartRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.cartRepository = cartRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void registerUser(int id, String username, String password, Role role) {
        User user = new User(id, username, password, role, 0.0, false);
        userRepository.addUser(user);
    }

    public User login(String username, String password) {
        return userRepository.getUserByUsername(username);
    }

    public void addToCart(int userId, int gameId) {
        Cart cart = new Cart(userId, gameId);
        cartRepository.addToCart(cart);
    }

    public List<Game> getCartGames(int userId) {
        return cartRepository.getCartByUserId(userId).stream()
                .map(c -> gameRepository.getGameById(c.getGameId()))
                .toList();
    }

    public void purchaseGames(int userId) {
        List<Cart> cartItems = cartRepository.getCartByUserId(userId);
        User user = userRepository.getUserById(userId);
        double totalCost = cartItems.stream()
                .mapToDouble(c -> gameRepository.getGameById(c.getGameId()).getPrice())
                .sum();

        if (user.getBalance() >= totalCost) {
            user.setBalance(user.getBalance() - totalCost);
            cartItems.forEach(c -> {
                Purchase purchase = new Purchase(purchaseRepository.getAllPurchases().size() + 1, userId, c.getGameId(), LocalDate.now());
                purchaseRepository.addPurchase(purchase);
            });
            cartRepository.getCartByUserId(userId).clear();
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void removeFromCart(int userId, int gameId) {
        cartRepository.removeFromCart(userId, gameId);
    }

    public void topUpBalance(int userId, double amount, String cardType) {
        User user = userRepository.getUserById(userId);
        double commission = cardType.equals("Visa") ? 0.02 : 0.03;
        user.setBalance(user.getBalance() + amount * (1 - commission));
    }

    public List<Purchase> getPurchasedGames(int userId) {
        return purchaseRepository.getPurchasesByUserId(userId);
    }
}