package com.gamelibrary.menus;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.models.User;
import com.gamelibrary.models.Game;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static void handleAdminActions(Scanner scanner, AdminController adminController) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Ban User");
            System.out.println("2. Unban User");
            System.out.println("3. Approve Game");
            System.out.println("4. Reject Game");
            System.out.println("5. View All Users");
            System.out.println("6. View All Games");
            System.out.println("7. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> banUser(scanner, adminController);
                case 2 -> unbanUser(scanner, adminController);
                case 3 -> approveGame(scanner, adminController);
                case 4 -> rejectGame(scanner, adminController);
                case 5 -> viewAllUsers(adminController);
                case 6 -> viewAllGames(adminController);
                case 7 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void banUser(Scanner scanner, AdminController adminController) {
        System.out.print("Enter User ID to ban: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        adminController.banUser(userId);
        System.out.println("User banned successfully!");
    }

    private static void unbanUser(Scanner scanner, AdminController adminController) {
        System.out.print("Enter User ID to unban: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        adminController.unbanUser(userId);
        System.out.println("User unbanned successfully!");
    }

    private static void approveGame(Scanner scanner, AdminController adminController) {
        System.out.print("Enter Game ID to approve: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();
        adminController.approveGame(gameId);
        System.out.println("Game approved successfully!");
    }

    private static void rejectGame(Scanner scanner, AdminController adminController) {
        System.out.print("Enter Game ID to reject: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();
        adminController.rejectGame(gameId);
        System.out.println("Game rejected successfully!");
    }

    private static void viewAllUsers(AdminController adminController) {
        System.out.println("\n=== All Users ===");
        List<User> users = adminController.getAllUsers();
        for (User user : users) {
            System.out.printf("ID: %d | Username: %s | Role: %s | Balance: $%.2f\n",
                    user.getId(), user.getUsername(), user.getRole(), user.getBalance());
        }
    }

    private static void viewAllGames(AdminController adminController) {
        System.out.println("\n=== All Games ===");
        List<Game> games = adminController.getAllGames();
        for (Game game : games) {
            System.out.printf("ID: %d | Name: %s | Price: $%.2f | Developer ID: %d\n",
                    game.getId(), game.getName(), game.getPrice(), game.getDeveloperId());
        }
    }
}