package com.gamelibrary.menus;

import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.User;
import java.util.List;
import java.util.Scanner;

public class DeveloperMenu {
    public static void handleDeveloperActions(Scanner scanner, DeveloperController developerController, User currentUser) {
        while (true) {
            System.out.println("\n=== Developer Menu ===");
            System.out.println("1. Create Game");
            System.out.println("2. View My Games");
            System.out.println("3. Delete Game");
            System.out.println("4. View Earnings");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createGame(scanner, developerController, currentUser);
                case 2 -> viewMyGames(developerController, currentUser);
                case 3 -> deleteGame(scanner, developerController, currentUser);
                case 4 -> viewEarnings(developerController, currentUser);
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createGame(Scanner scanner, DeveloperController developerController, User currentUser) {
        System.out.print("Enter game name: ");
        String name = scanner.nextLine();

        System.out.print("Enter game price: $");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter game size (GB): ");
        double sizeGB = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter age restriction: ");
        int ageRestriction = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        int gameId = developerController.createGame(name, currentUser.getId(), price, sizeGB, ageRestriction, genre, description);
        System.out.printf("Game created successfully! ID: %d\n", gameId);
    }

    private static void viewMyGames(DeveloperController developerController, User currentUser) {
        System.out.println("\n=== My Games ===");
        List<Game> games = developerController.getGamesByDeveloper(currentUser.getId());
        for (Game game : games) {
            System.out.printf("ID: %d | Name: %s | Price: $%.2f | Status: %s\n",
                    game.getId(), game.getName(), game.getPrice(), game.isApproved() ? "Approved" : "Pending");
        }
    }

    private static void deleteGame(Scanner scanner, DeveloperController developerController, User currentUser) {
        System.out.print("Enter Game ID to delete: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();
        developerController.deleteGame(gameId, currentUser.getId());
        System.out.println("Game deleted successfully!");
    }

    private static void viewEarnings(DeveloperController developerController, User currentUser) {
        double earnings = developerController.getEarnings(currentUser.getId());
        System.out.printf("\nYour total earnings: $%.2f\n", earnings);
    }
}