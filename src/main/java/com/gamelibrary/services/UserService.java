package com.gamelibrary.services;

import com.gamelibrary.models.*;
import com.gamelibrary.repositories.CartRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.PurchaseRepository;
import com.gamelibrary.repositories.UserRepository;

import java.util.List;
import java.time.LocalDate;

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
    public void addBalance(int userId, double amount) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            user.setBalance(user.getBalance() + amount);
        } else {
            System.out.println("User not found. Unable to add balance.");
        }
    }

    public void topUpBalance(int userId, String cardNumber) {
        User user = userRepository.getUserById(userId);

        if (user == null) {
            System.out.println("User not found. Unable to top up balance.");
            return;
        }

        if (user.getCardNumber() != null) {
            System.out.printf("Your current balance is $%.2f.%n", user.getBalance());
            return;
        }

        if (!cardNumber.matches("\\d{16}")) {
            System.out.println("Invalid card number! Please enter a valid 16-digit card number.");
            return;
        }

        user.setCardNumber(cardNumber);
        int generatedBalance = (int) (Math.random() * (10000 - 2000 + 1)) + 2000;
        user.setBalance(user.getBalance() + generatedBalance);

        System.out.printf("Card added successfully! Your account has been credited with $%d.%n", generatedBalance);
        System.out.printf("New Balance: $%.2f%n", user.getBalance());
    }

    public List<Purchase> getPurchasedGames(int userId) {
        return purchaseRepository.getPurchasesByUserId(userId);
    }
}
