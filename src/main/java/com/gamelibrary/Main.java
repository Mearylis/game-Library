package com.gamelibrary;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.controllers.UserController;
import com.gamelibrary.databases.Database;
import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.CartRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.PurchaseRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.services.AdminService;
import com.gamelibrary.services.DeveloperService;
import com.gamelibrary.services.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameRepository gameRepository = new GameRepository();
        UserRepository userRepository = new UserRepository();
        CartRepository cartRepository = new CartRepository();
        PurchaseRepository purchaseRepository = new PurchaseRepository();

        Database database = new Database(gameRepository, userRepository);
        AdminService adminService = new AdminService(gameRepository, userRepository);
        DeveloperService developerService = new DeveloperService(gameRepository);
        UserService userService = new UserService(userRepository, gameRepository, cartRepository, purchaseRepository);

        AdminController adminController = new AdminController(adminService);
        DeveloperController developerController = new DeveloperController(developerService);
        UserController userController = new UserController(userService, gameRepository);

        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (true) {
            if (currentUser == null) {
                System.out.println("1. Register\n2. Login\n3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter role (USER, DEVELOPER):");
                    Role role = Role.valueOf(scanner.nextLine().toUpperCase());
                    int id = userRepository.getAllUsers().size() + 1;
                    userController.registerUser(id, username, password, role);
                    System.out.println("User registered successfully.");
                } else if (choice == 2) {
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    currentUser = userController.login(username, password);
                    if (currentUser != null) {
                        System.out.println("Login successful.");
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                } else if (choice == 3) {
                    break;
                }
            } else {
                switch (currentUser.getRole()) {
                    case ADMIN -> {
                        System.out.println("1. Approve Game\n2. Delete Game\n3. Ban User\n4. Unban User\n5. Delete User\n6. List Users\n7. List Games\n8. Logout");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice == 1) {
                            System.out.println("Enter game ID to approve:");
                            int gameId = scanner.nextInt();
                            adminController.approveGame(gameId);
                        } else if (choice == 2) {
                            System.out.println("Enter game ID to delete:");
                            int gameId = scanner.nextInt();
                            adminController.deleteGame(gameId);
                        } else if (choice == 3) {
                            System.out.println("Enter user ID to ban:");
                            int userId = scanner.nextInt();
                            adminController.banUser(userId);
                        } else if (choice == 4) {
                            System.out.println("Enter user ID to unban:");
                            int userId = scanner.nextInt();
                            adminController.unbanUser(userId);
                        } else if (choice == 5) {
                            System.out.println("Enter user ID to delete:");
                            int userId = scanner.nextInt();
                            adminController.deleteUser(userId);
                        } else if (choice == 6) {
                            adminController.getAllUsers().forEach(u -> System.out.println(u.getUsername() + " - " + u.getRole()));
                        } else if (choice == 7) {
                            adminController.getAllGames().forEach(g -> System.out.println(g.getName() + " - " + g.getPrice()));
                        } else if (choice == 8) {
                            currentUser = null;
                        }
                    }
                    case DEVELOPER -> {
                        System.out.println("1. Create Game\n2. List My Games\n3. Delete Game\n4. Logout");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice == 1) {
                            System.out.println("Enter game name:");
                            String name = scanner.nextLine();
                            System.out.println("Enter game price:");
                            double price = scanner.nextDouble();
                            int id = gameRepository.getAllGames().size() + 1;
                            developerController.createGame(id, name, currentUser.getId(), price);
                        } else if (choice == 2) {
                            developerController.getGamesByDeveloper(currentUser.getId()).forEach(g -> System.out.println(g.getName() + " - " + g.getPrice()));
                        } else if (choice == 3) {
                            System.out.println("Enter game ID to delete:");
                            int gameId = scanner.nextInt();
                            developerController.deleteGame(gameId, currentUser.getId());
                        } else if (choice == 4) {
                            currentUser = null;
                        }
                    }
                    case USER -> {
                        System.out.println("1. Add to Cart\n2. View Cart\n3. Purchase Games\n4. Remove from Cart\n5. Top Up Balance\n6. View Purchased Games\n7. Logout");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice == 1) {
                            System.out.println("Enter game ID to add to cart:");
                            int gameId = scanner.nextInt();
                            userController.addToCart(currentUser.getId(), gameId);
                        } else if (choice == 2) {
                            userController.getCartGames(currentUser.getId()).forEach(g -> System.out.println(g.getName() + " - $" + g.getPrice()));
                        } else if (choice == 3) {
                            userController.purchaseGames(currentUser.getId());
                        } else if (choice == 4) {
                            System.out.println("Enter game ID to remove from cart:");
                            int gameId = scanner.nextInt();
                            userController.removeFromCart(currentUser.getId(), gameId);
                        } else if (choice == 5) {
                            // Top-Up Balance Logic
                            System.out.println("Enter 16-digit card number:");
                            String cardNumber = scanner.nextLine();

                            // Call UserController to handle top-up balance
                            userController.topUpBalance(currentUser.getId(), cardNumber);
                        } else if (choice == 6) {
                            userController.getPurchasedGames(currentUser.getId()).forEach(p -> System.out.println(p.getGameId() + " - " + p.getPurchaseDate()));
                        } else if (choice == 7) {
                            currentUser = null;
                        }
                    }

                }
            }
        }
    }
}