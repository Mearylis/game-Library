 package com.gamelibrary.application;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.controllers.UserController;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.GameRepository;

import java.util.Scanner;

public class MenuHandler {
    private final AdminController adminController;
    private final DeveloperController developerController;
    private final UserController userController;
    private final GameRepository gameRepository;
    private final Scanner scanner;

    public MenuHandler(AdminController adminController, DeveloperController developerController, UserController userController, GameRepository gameRepository) {
        this.adminController = adminController;
        this.developerController = developerController;
        this.userController = userController;
        this.gameRepository = gameRepository;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Developer/User Menu");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        User admin = userController.loginAdmin(username, password);
        if (admin != null) {
            System.out.println("Login successful. Welcome, Admin.");
            adminMenu();
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Return to Main Menu");
            System.out.print("Choose an option: ");
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void loginUser() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        User user = userController.loginUser(username, password);
        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getUsername());
            if (user.getRole() == Role.DEVELOPER) {
                developerMenu(user);
            } else {
                userActionsMenu(user);
            }
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private void developerMenu(User developer) {
        while (true) {
            System.out.println("\n=== Developer Menu ===");
            System.out.println("1. Create Game");
            System.out.println("2. View My Games");
            System.out.println("3. Delete My Game");
            System.out.println("4. Return to User Menu");
            System.out.print("Choose an option: ");
            int devChoice = scanner.nextInt();
            scanner.nextLine();

            switch (devChoice) {
                case 1:
                    System.out.print("Enter Game ID: ");
                    int gameId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Game Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Game Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter Game Category: ");
                    String category = scanner.nextLine();

                    developerController.createGame(new Game(gameId, name, price, false, developer.getId(), category));
                    break;
                case 2:
                    developerController.viewGamesByDeveloper(developer.getId());
                    break;
                case 3:
                    System.out.print("Enter Game ID to delete: ");
                    int deleteGameId = scanner.nextInt();
                    developerController.deleteGame(deleteGameId, developer.getId());
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void userActionsMenu(User user) {
        while (true) {
            System.out.println("\n=== User Actions Menu ===");
            System.out.println("1. View All Games");
            System.out.println("2. Add Game to Cart");
            System.out.println("3. Remove Game from Cart");
            System.out.println("4. Purchase Games");
            System.out.println("5. Top Up Balance");
            System.out.println("6. Return to User Menu");
            System.out.print("Choose an option: ");
            int userActionChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userActionChoice) {
                case 1:
                    userController.viewAllGames();
                    break;
                case 2:
                    System.out.print("Enter Game ID: ");
                    int addGameId = scanner.nextInt();
                    Game addGame = gameRepository.getGameById(addGameId);
                    if (addGame != null) {
                        userController.addToCart(user, addGame);
                    } else {
                        System.out.println("Invalid Game ID.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Game ID to remove: ");
                    int removeGameId = scanner.nextInt();
                    userController.removeFromCart(user, removeGameId);
                    break;
                case 4:
                    userController.purchaseGames(user);
                    break;
                case 5:
                    System.out.print("Enter top-up amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter Card Number: ");
                    String cardNumber = scanner.nextLine();
                    userController.topUpBalance(user, amount, cardNumber);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void registerUser() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Role (USER/DEVELOPER): ");
        Role role = Role.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        userController.registerUser(new User(userId, username, password, role, balance, false));
        System.out.println("Registration successful.");
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View All Users");
            System.out.println("2. View All Games");
            System.out.println("3. Approve Game");
            System.out.println("4. Delete Game");
            System.out.println("5. Ban User");
            System.out.println("6. Unban User");
            System.out.println("7. Delete User");
            System.out.println("8. Return to Main Menu");
            System.out.print("Choose an option: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine();

            switch (adminChoice) {
                case 1:
                    adminController.viewAllUsers();
                    break;
                case 2:
                    adminController.viewAllGames();
                    break;
                case 3:
                    System.out.print("Enter Game ID to approve: ");
                    int approveId = scanner.nextInt();
                    adminController.approveGame(approveId);
                    break;
                case 4:
                    System.out.print("Enter Game ID to delete: ");
                    int deleteId = scanner.nextInt();
                    adminController.deleteGame(deleteId);
                    break;
                case 5:
                    System.out.print("Enter User ID to ban: ");
                    int banUserId = scanner.nextInt();
                    adminController.banUser(banUserId);
                    break;
                case 6:
                    System.out.print("Enter User ID to unban: ");
                    int unbanUserId = scanner.nextInt();
                    adminController.unbanUser(unbanUserId);
                    break;
                case 7:
                    System.out.print("Enter User ID to delete: ");
                    int deleteUserId = scanner.nextInt();
                    adminController.deleteUser(deleteUserId);
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}