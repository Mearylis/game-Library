package com.gamelibrary;

import com.gamelibrary.controllers.UserController;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new BotService());
            System.out.println("Support bot started!");

            Scanner scanner = new Scanner(System.in);
            UserController userController = new UserController(null); // Замените null на реальный UserService
            User currentUser = new User(1, "JohnDoe", "password123", null, 100.0, false); // Тестовый пользователь
            Game game = new Game(1, "Test Game", 59.99, true, 1, "Adventure"); // Тестовая игра

            handleUserActions(scanner, userController, currentUser, game);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleUserActions(Scanner scanner, UserController userController, User currentUser, Game game) {
        while (true) {
            System.out.println("\n--- Game: " + game.getName() + " ---");
            System.out.println("1. Leave a Review\n2. Donate to the Game\n3. View Top Rated Games\n4. Return to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter your review:");
                    String review = scanner.nextLine();

                    int rating;
                    while (true) {
                        System.out.println("Rate the game (1-5):");
                        rating = scanner.nextInt();
                        scanner.nextLine();
                        if (rating >= 1 && rating <= 5) break;
                        else System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                    }

                    userController.leaveReview(currentUser.getId(), game.getId(), review, rating);
                    System.out.println("Thank you for your review and rating!");
                }
                case 3 -> {
                    System.out.println("Enter number of top games to display:");
                    int topN = scanner.nextInt();
                    scanner.nextLine();
                    userController.showTopRatedGames(topN);
                }
                case 4 -> {
                    System.out.println("Returning to Main Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
