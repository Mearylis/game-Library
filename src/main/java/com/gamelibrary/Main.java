package com.gamelibrary;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.services.AdminService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instantiate repositories
        GameRepository gameRepository = new GameRepository();
        UserRepository userRepository = new UserRepository();

        // Instantiate services and controllers
        AdminService adminService = new AdminService(gameRepository, userRepository);
        AdminController adminController = new AdminController(adminService);

        // Prepopulate users
        userRepository.addUser(new User(1, "admin", "adminpass", Role.ADMIN, 100.0, false));
        userRepository.addUser(new User(2, "user1", "userpass", Role.USER, 50.0, false));
        userRepository.addUser(new User(3, "user2", "userpass", Role.USER, 75.0, false));

        // Prepopulate games
        gameRepository.addGame(new Game(1, "Game1", 50.0, false, 101));
        gameRepository.addGame(new Game(2, "Game2", 30.0, false, 102));
        gameRepository.addGame(new Game(3, "Game3", 20.0, true, 101));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Admin Menu");
            System.out.println("2. User Menu");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> adminMenu(scanner, adminController);
                case 2 -> userMenu(scanner, userRepository);
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void adminMenu(Scanner scanner, AdminController adminController) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View All Users");
            System.out.println("2. View All Games");
            System.out.println("3. Approve Game");
            System.out.println("4. Delete Game");
            System.out.println("5. Ban User");
            System.out.println("6. Unban User");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> adminController.viewAllUsers();
                case 2 -> adminController.viewAllGames();
                case 3 -> {
                    System.out.print("Enter Game ID to approve: ");
                    int gameId = scanner.nextInt();
                    adminController.approveGame(gameId);
                }
                case 4 -> {
                    System.out.print("Enter Game ID to delete: ");
                    int gameId = scanner.nextInt();
                    adminController.deleteGame(gameId);
                }
                case 5 -> {
                    System.out.print("Enter User ID to ban: ");
                    int userId = scanner.nextInt();
                    adminController.banUser(userId);
                }
                case 6 -> {
                    System.out.print("Enter User ID to unban: ");
                    int userId = scanner.nextInt();
                    adminController.unbanUser(userId);
                }
                case 7 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void userMenu(Scanner scanner, UserRepository userRepository) {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Link Bank Account");
            System.out.println("2. Top-Up Balance");
            System.out.println("3. View All Users");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter your user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter your bank account number: ");
                    String bankAccount = scanner.nextLine();
                    userRepository.updateBankAccount(userId, bankAccount);
                }
                case 2 -> {
                    System.out.print("Enter your user ID: ");
                    int userId = scanner.nextInt();
                    System.out.print("Enter top-up amount: ");
                    double amount = scanner.nextDouble();
                    User user = userRepository.getUserById(userId);
                    if (user != null) {
                        user.topUpBalance(amount);
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 3 -> {
                    userRepository.getAllUsers().forEach(u ->
                            System.out.println("ID: " + u.getId() + ", Username: " + u.getUsername() +
                                    ", Balance: " + u.getBalance() + ", Bank Account: " + u.getBankAccount())
                    );
                }
                case 4 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
