package com.gamelibrary.controllers;

import com.gamelibrary.models.*;
import com.gamelibrary.services.UserService;
import com.gamelibrary.repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final UserService userService;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private GameRepository gameRepository;
    private PurchaseRepository purchaseRepository;

    public UserController(UserService userService, UserRepository userRepository, CartRepository cartRepository, GameRepository gameRepository, PurchaseRepository purchaseRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.gameRepository = gameRepository;
        this.purchaseRepository = purchaseRepository;

    }

    public void registerUser(int id, String username, String password, Role role) {
        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username or password cannot be empty.");
        }

        if (userRepository.getUserByUsername(username) != null) {
            System.out.println("Error: A user with this username already exists.");
            return;
        }

        User newUser = new User(id, username, password, role);
        userRepository.addUser(newUser);
        System.out.println("User registered successfully.");
    }


    public User login(String username, String password) {
        System.out.println("Welcome! " + username);
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
        }
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new IllegalArgumentException("Invalid username or password.");
    }



    public void addToCart(int userId, int gameId) {
        userService.addToCart(userId, gameId);
    }

    public List<Game> getCartGames(int userId) {
        return userService.getCartGames(userId);
    }

    public void purchaseGames(int userId, String cardNumber, String bank) {

        if (!bank.equals("Kaspi") && !bank.equals("Alfa") && !bank.equals("Halyk")) {
            System.out.println("Error! Unsupported bank. Transaction failed.");
            return;
        }

        User user = userRepository.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<Cart> cartItems = cartRepository.getCartByUserId(userId);
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty. Nothing to purchase.Please top-up Balance");
            return;
        }

        double totalCost = cartItems.stream()
                .mapToDouble(c -> gameRepository.getGameById(c.getGameId()).getPrice())
                .sum();

        if (user.getBalance() < totalCost) {
            System.out.printf("Insufficient balance. You need %.2f more.%n", totalCost - user.getBalance());
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
        userService.removeFromCart(userId, gameId);
    }

    public void topUpBalance(int userId, double amount, String cardNumber, String cardType) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter card number (16 digits): ");
        cardNumber = scanner.nextLine();

        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            System.out.println("Error! The card number must contain exactly 16 digits.");
            return;
        }

        String bank = getBankByCardNumber(cardNumber);
        if (bank == null) {
            System.out.println("Error! Unsupported bank. Transaction failed.");
            return;
        }

        System.out.println("You have selected bank: " + bank);
        System.out.print("Enter the amount to top up: ");
        double amountToTopUp = scanner.nextDouble();

        if (amountToTopUp <= 0) {
            System.out.println("Error! The amount must be positive.");
            return;
        }

        User user = userRepository.getUserById(userId);
        if (user == null) {
            System.out.println("User not found. Cannot top up balance.");
            return;
        }

        user.setBalance(user.getBalance() + amountToTopUp);
        userRepository.updateUser(user);

        double amountInTenge = convertCurrency(amountToTopUp, bank, "Kaspi");
        double amountInRuble = convertCurrency(amountToTopUp, bank, "Alfa");
        double amountInDollar = convertCurrency(amountToTopUp, bank, "Halyk");

        System.out.printf("Balance successfully topped up by %.2f %s.%n", amountToTopUp, getCurrencyByBank(bank));
        System.out.printf("Equivalent in Tenge: %.2f%n", amountInTenge);
        System.out.printf("Equivalent in Ruble: %.2f%n", amountInRuble);
        System.out.printf("Equivalent in Dollar: %.2f%n", amountInDollar);
    }

    private double convertCurrency(double amount, String fromBank, String toBank) {
        double conversionRate = getConversionRate(fromBank, toBank);
        return amount * conversionRate;
    }

    private double getConversionRate(String fromBank, String toBank) {
        if (fromBank.equals(toBank)) {
            return 1.0;
        }

        return switch (fromBank + "-" + toBank) {
            case "Kaspi-Alfa" -> 0.2; // 5 Tenge = 1 Ruble
            case "Kaspi-Halyk" -> 0.002; // 500 Tenge = 1 Dollar
            case "Alfa-Kaspi" -> 5.0; // 1 Ruble = 5 Tenge
            case "Alfa-Halyk" -> 0.01; // 100 Rubles = 1 Dollar
            case "Halyk-Kaspi" -> 500.0; // 1 Dollar = 500 Tenge
            case "Halyk-Alfa" -> 100.0; // 1 Dollar = 100 Rubles
            default -> 1.0;
        };
    }

    private String getBankByCardNumber(String cardNumber) {
        String prefix = cardNumber.substring(0, 4);
        return switch (prefix) {
            case "4400" -> "Kaspi";
            case "4276" -> "Alfa";
            case "5522" -> "Halyk";
            default -> null;
        };
    }

    private String getCurrencyByBank(String bank) {
        return switch (bank) {
            case "Kaspi" -> "tenge";
            case "Alfa" -> "ruble";
            case "Halyk" -> "dollar";
            default -> "unknown currency";
        };
    }

    public List<Purchase> getPurchasedGames(int userId) {
        return userService.getPurchasedGames(userId);
    }
}
