package com.gamelibrary.services;

import com.gamelibrary.models.*;
import com.gamelibrary.repositories.CartRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.PurchaseRepository;
import com.gamelibrary.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
        if (userRepository.getUserByUsername(username) != null) {
            System.out.println("Error: A user with this username already exists.");
            return;
        }
        User newUser = new User(id, username, password, role, 0.0, false);
        userRepository.addUser(newUser);
        System.out.println("User registered successfully.");
    }


    public User login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new IllegalArgumentException("Invalid username or password.");
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

    public void purchaseGames(int userId, String cardNumber, String bank) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<Cart> cartItems = cartRepository.getCartByUserId(userId);
        double totalCost = cartItems.stream()
                .mapToDouble(c -> gameRepository.getGameById(c.getGameId()).getPrice())
                .sum();

        if (user.getBalance() < totalCost) {
            System.out.printf("Insufficient balance. You need %.2f more.%n", totalCost - user.getBalance());
            return;
        }

        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty. Nothing to purchase.");
            return;
        }

        user.setBalance(user.getBalance() - totalCost);
        for (Cart cart : cartItems) {
            Purchase purchase = new Purchase(
                    purchaseRepository.getAllPurchases().size() + 1,
                    userId, cart.getGameId(), LocalDate.now()
            );
            purchaseRepository.addPurchase(purchase);
        }

        cartRepository.getCartByUserId(userId).clear();
        System.out.println("Purchase successful! Your games are now in your library.");
    }




    public void removeFromCart(int userId, int gameId) {
        cartRepository.removeFromCart(userId, gameId);
    }

    public void topUpBalance(int userId, double amount, String cardNumber, String cardType) {
        Scanner scanner = new Scanner(System.in);

//        if (!Validator.validate(cardNumber)) {
//            System.out.println("Invalid credit card number. Transaction failed.");
//            return;
//        }

        User user = userRepository.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount. Must be greater than zero.");
            return;
        }

        if (!cardType.equalsIgnoreCase("Kaspi") && !cardType.equalsIgnoreCase("Alfa") && !cardType.equalsIgnoreCase("Halyk")) {
            System.out.println("This platform currently supports only Kaspi Bank, Alfa Bank, and Halyk Bank.");
            return;
        }

        double commission;
        switch (cardType.toLowerCase()) {
            case "visa" -> commission = 0.02;
            case "mastercard" -> commission = 0.03;
            case "american express" -> commission = 0.04;
            default -> {
                System.out.println("Unsupported card type. Transaction failed.");
                return;
            }
        }

        double finalAmount = amount * (1 - commission);
        user.setBalance(user.getBalance() + finalAmount);

        System.out.printf("Balance successfully topped up! New balance: %.2f%n", user.getBalance());

        System.out.println("Your balance has been updated. You can now purchase games!");
    }


    public List<Purchase> getPurchasedGames(int userId) {
        return purchaseRepository.getPurchasesByUserId(userId);
    }
}