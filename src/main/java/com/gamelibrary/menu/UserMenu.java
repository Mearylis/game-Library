package com.gamelibrary.menus;

import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import java.util.Scanner;
import com.gamelibrary.controllers.UserController;
import com.gamelibrary.exceptions.InsufficientBalanceException;
import com.gamelibrary.exceptions.InvalidCardException;
import com.gamelibrary.exceptions.AgeRestrictionException;
import com.gamelibrary.models.Review;
import com.gamelibrary.repositories.ChatRepository;
import java.util.List;

public class UserMenu {

    public static void handleUserActions(Scanner scanner, UserController userController, User currentUser, GameRepository gameRepository, ChatRepository chatRepository) {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Games");
            System.out.println("2. View Cart");
            System.out.println("3. Add Funds");
            System.out.println("4. Purchase Games");
            System.out.println("5. View Purchased Games");
            System.out.println("6. Friends Menu");
            System.out.println("7. Game Chat");
            System.out.println("8. Leave a Review");
            System.out.println("9. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewGames(scanner, gameRepository, currentUser, userController);
                case 2 -> viewCart(scanner, userController, currentUser);
                case 3 -> addFunds(scanner, userController, currentUser);
                case 4 -> purchaseGames(scanner, userController, currentUser);
                case 5 -> viewPurchasedGames(userController, currentUser);
                case 6 -> FriendsMenu.handleFriendsMenu(scanner, userController, currentUser);
                case 7 -> ChatMenu.handleGameChat(scanner, userController, chatRepository, currentUser, gameRepository);
                case 8 -> leaveReview(scanner, userController, currentUser, gameRepository);
                case 9 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewGames(Scanner scanner, GameRepository gameRepository, User currentUser, UserController userController) {
        System.out.println("\n=== Available Games ===");
        List<Game> games = gameRepository.getAllGames();
        for (Game game : games) {
            System.out.printf("ID: %d | Name: %s | Price: $%.2f | Age Restriction: %d+\n",
                    game.getId(), game.getName(), game.getPrice(), game.getAgeRestriction());
        }

        System.out.print("\nEnter Game ID to add to cart (or 0 to go back): ");
        int gameId = scanner.nextInt();
        scanner.nextLine();

        if (gameId != 0) {
            Game selectedGame = gameRepository.getGameById(gameId);
            if (selectedGame != null) {
                try {
                    userController.addToCart(currentUser.getId(), selectedGame);
                    System.out.println("Game added to cart!");
                } catch (AgeRestrictionException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Game not found.");
            }
        }
    }

    private static void viewCart(Scanner scanner, UserController userController, User currentUser) {
        List<Game> cart = userController.getCart(currentUser.getId());
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        System.out.println("\n=== Your Cart ===");
        double total = 0.0;
        for (Game game : cart) {
            System.out.printf("ID: %d | Name: %s | Price: $%.2f\n",
                    game.getId(), game.getName(), game.getPrice());
            total += game.getPrice();
        }
        System.out.printf("Total: $%.2f\n", total);

        System.out.print("\nEnter Game ID to remove from cart (or 0 to go back): ");
        int gameId = scanner.nextInt();
        scanner.nextLine();

        if (gameId != 0) {
            userController.removeFromCart(currentUser.getId(), gameId);
            System.out.println("Game removed from cart!");
        }
    }

    private static void addFunds(Scanner scanner, UserController userController, User currentUser) {
        System.out.print("\nEnter amount to add: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter card number (16 digits): ");
        String cardNumber = scanner.nextLine();

        try {
            userController.addFunds(currentUser.getId(), amount, cardNumber);
            System.out.printf("$%.2f added to your balance.\n", amount);
        } catch (InvalidCardException e) {
            System.out.println("Invalid card number. Please try again.");
        }
    }

    private static void purchaseGames(Scanner scanner, UserController userController, User currentUser) {
        List<Game> cart = userController.getCart(currentUser.getId());
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        double total = cart.stream().mapToDouble(Game::getPrice).sum();
        System.out.printf("\nTotal cost: $%.2f\n", total);
        System.out.printf("Current balance: $%.2f\n", currentUser.getBalance());
        System.out.print("Confirm purchase? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            try {
                userController.purchaseGames(currentUser.getId());
                System.out.println("Purchase successful!");
            } catch (InsufficientBalanceException e) {
                System.out.println("Insufficient balance. Please add funds.");
            }
        } else {
            System.out.println("Purchase canceled.");
        }
    }

    private static void viewPurchasedGames(UserController userController, User currentUser) {
        List<Game> purchasedGames = userController.getPurchasedGames(currentUser.getId());
        if (purchasedGames.isEmpty()) {
            System.out.println("\nYou have not purchased any games yet.");
            return;
        }

        System.out.println("\n=== Purchased Games ===");
        for (Game game : purchasedGames) {
            System.out.printf("ID: %d | Name: %s | Price: $%.2f\n",
                    game.getId(), game.getName(), game.getPrice());
        }
    }

    private static void leaveReview(Scanner scanner, UserController userController, User currentUser, GameRepository gameRepository) {
        List<Game> purchasedGames = userController.getPurchasedGames(currentUser.getId());
        if (purchasedGames.isEmpty()) {
            System.out.println("\nYou have not purchased any games yet.");
            return;
        }

        System.out.println("\n=== Purchased Games ===");
        for (Game game : purchasedGames) {
            System.out.printf("ID: %d | Name: %s | Price: $%.2f\n",
                    game.getId(), game.getName(), game.getPrice());
        }

        System.out.print("\nEnter Game ID to leave a review (or 0 to go back): ");
        int gameId = scanner.nextInt();
        scanner.nextLine();

        if (gameId != 0) {
            Game selectedGame = gameRepository.getGameById(gameId);
            if (selectedGame != null) {
                handleReviews(scanner, gameRepository, selectedGame, currentUser);
            } else {
                System.out.println("Game not found.");
            }
        }
    }

    private static void handleGameDetails(Scanner scanner, GameRepository gameRepository, Game game, User currentUser) {
        if (!isUserOldEnough(currentUser, game.getAgeRestriction())) {
            System.out.println("\033[1;31mYou do not meet the age requirement for this game.\033[0m");
            return;
        }

        System.out.println(game.toString());

        while (true) {
            System.out.println("\n\033[1;34m--- Game: " + game.getName() + " ---\033[0m");
            System.out.println("1. 🌟 View Reviews & Leave a Review");
            System.out.println("2. 🖥 View System Requirements");
            System.out.println("3. 🔙 Back to Main Menu");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("\033[1;31mInvalid input! Please enter a number.\033[0m");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> handleReviews(scanner, gameRepository, game, currentUser);
                case 2 -> viewSystemRequirements(gameRepository, game);
                case 3 -> {
                    System.out.println("\n\033[1;34mReturning to main menu...\033[0m");
                    return;
                }
                default -> System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");
            }
        }
    }

    private static void viewSystemRequirements(GameRepository gameRepository, Game game) {
        System.out.println("\n\033[1;36m=== SYSTEM REQUIREMENTS ===\033[0m");

        System.out.println("\n\033[1;33mMINIMUM:\033[0m");
        System.out.println(gameRepository.getMinimumRequirements(game.getId()).getFormatted());

        System.out.println("\n\033[1;32mRECOMMENDED:\033[0m");
        System.out.println(gameRepository.getRecommendedRequirements(game.getId()).getFormatted());
    }

    private static boolean isUserOldEnough(User user, int ageRestriction) {
        return user.getAge() >= ageRestriction;
    }

    private static void handleReviews(Scanner scanner, GameRepository gameRepository, Game game, User currentUser) {
        System.out.println("\n=== Reviews for " + game.getName() + " ===");

        List<Review> reviews = gameRepository.getReviewsForGame(game.getId());
        if (reviews.isEmpty()) {
            System.out.println("No reviews yet. Be the first to write one!");
        } else {
            System.out.println("\n=== Latest Reviews ===");
            for (Review review : reviews) {
                System.out.println(review);
            }
        }

        System.out.print("\nWould you like to leave a review? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            System.out.print("Enter your rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();

            if (rating < 1 || rating > 5) {
                System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                return;
            }

            System.out.print("Enter your review: ");
            String text = scanner.nextLine();

            gameRepository.saveReview(game.getId(), currentUser.getId(), text, rating);
            System.out.println("Thank you for your review!");
        }
    }
}