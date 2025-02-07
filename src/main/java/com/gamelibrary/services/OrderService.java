package com.gamelibrary.services;

import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public OrderService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    // Purchase multiple games
    public void purchaseGames(int userId, List<Integer> gameIds) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        if (user.isBanned()) {
            System.out.println("User is banned. Cannot purchase.");
            return;
        }

        List<Game> chosenGames = gameRepository.getAllGames().stream()
                .filter(g -> gameIds.contains(g.getId()) && g.isApproved())
                .collect(Collectors.toList());

        double total = chosenGames.stream().mapToDouble(Game::getPrice).sum();

        if (!user.deductBalance(total)) {
            System.out.println("Purchase canceled. Not enough funds.");
            return;
        }

        String titles = chosenGames.stream().map(Game::getName).collect(Collectors.joining(", "));
        System.out.println("Purchased: " + titles + " for $" + total);
    }
}
