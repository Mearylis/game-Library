package com.gamelibrary.services;

import com.gamelibrary.models.*;
import com.gamelibrary.repositories.*;

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
        User user = userRepository.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void addToCart(int userId, int gameId) {
        Cart cart = new Cart(userId, gameId);
        cartRepository.addToCart(cart);
    }

    public List<Game> getCartGames(int userId) {
        List<Cart> carts = cartRepository.getCartByUserId(userId);
        return carts.stream().map(cart -> gameRepository.getGameById(cart.getGameId())).toList();
    }

    public void purchaseGames(int userId) {
        List<Game> cartGames = getCartGames(userId);
        User user = userRepository.getUserById(userId);
        for (Game game : cartGames) {
            if (user.getBalance() >= game.getPrice()) {
                user.setBalance(user.getBalance() - game.getPrice());
                Purchase purchase = new Purchase(purchaseRepository.getAllPurchases().size() + 1, userId, game.getId(), LocalDate.now());
                purchaseRepository.addPurchase(purchase);
                cartRepository.removeFromCart(userId, game.getId());
            } else {
                System.out.println("Insufficient balance for game: " + game.getName());
            }
        }
    }

    public void removeFromCart(int userId, int gameId) {
        cartRepository.removeFromCart(userId, gameId);
    }

    public void topUpBalance(int userId, double amount, String cardNumber, String cardExpiryDate, String cardCVV) {
        User user = userRepository.getUserById(userId);
        user.setCardNumber(cardNumber);
        user.setCardExpiryDate(cardExpiryDate);
        user.setCardCVV(cardCVV);
        user.setBalance(user.getBalance() + amount);
    }

    public List<Purchase> getPurchasedGames(int userId) {
        return purchaseRepository.getPurchasesByUserId(userId);
    }

    public double getUserBalance(int userId) {
        User user = userRepository.getUserById(userId);
        return user.getBalance();
    }

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }
}