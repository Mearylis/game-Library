package com.gamelibrary.menus;

import com.gamelibrary.repositories.GameRepository;
import java.util.Scanner;

public class GuestMenu {
    public static void handleGuestActions(Scanner scanner, GameRepository gameRepository) {
        while (true) {
            System.out.println("\n=== Guest Menu ===");
            System.out.println("1. View Games");
            System.out.println("2. View Promotions");
            System.out.println("3. Register as a User");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\nAvailable Games:");
                    gameRepository.getAllGames().forEach(game ->
                            System.out.printf("ID: %d | Name: %s | Price: $%.2f\n",
                                    game.getId(), game.getName(), game.getPrice()));
                }
                case 2 -> {
                    System.out.println("\nCurrent Promotions:");
                    gameRepository.getAllGames().stream()
                            .filter(game -> game.getPrice() < 20)
                            .forEach(game -> System.out.printf("Game: %s | Price: $%.2f\n",
                                    game.getName(), game.getPrice()));
                }
                case 3 -> {
                    System.out.println("\nRedirecting to registration...");
                    return;
                }
                case 4 -> {
                    System.out.println("Returning to the main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}