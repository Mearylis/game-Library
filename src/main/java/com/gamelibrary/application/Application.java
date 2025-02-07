package com.gamelibrary.application;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.services.AdminService;
import com.gamelibrary.services.DeveloperService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final DeveloperService developerService;
    private final OrderService orderService;
    private final AdminController adminController;
    private final DeveloperController developerController;
    private final Scanner scanner;

    public Application() {
        // Initialize Repositories
        this.gameRepository = new GameRepository();
        this.userRepository = new UserRepository();

        // Initialize Services
        this.adminService = new AdminService(gameRepository, userRepository);
        this.developerService = new DeveloperService(gameRepository);
        this.orderService = new OrderService(userRepository, gameRepository);

        // Initialize Controllers
        this.adminController = new AdminController(adminService);
        this.developerController = new DeveloperController(developerService);

        this.scanner = new Scanner(System.in);

        // Prepopulate data
        prepopulate();
    }

    private void prepopulate() {
        userRepository.addUser(new User(1, "admin", "adminpass", Role.ADMIN, 200.0, false));
        userRepository.addUser(new User(2, "dev", "devpass", Role.DEVELOPER, 150.0, false));
        userRepository.addUser(new User(3, "user", "userpass", Role.USER, 100.0, false));

        // DeveloperId = 2 => dev
        gameRepository.addGame(new Game(1, "Game1", 50.0, false, 2, "Action"));
        gameRepository.addGame(new Game(2, "Game2", 30.0, false, 2, "Puzzle"));
        // DeveloperId = 0 => Unknown
        gameRepository.addGame(new Game(3, "Game3", 20.0, true, 0, "Adventure"));
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Admin Menu");
            System.out.println("2. Developer Menu");
            System.out.println("3. User Menu");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    developerMenu();
                    break;
                case 3:
                    userMenu();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
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
            System.out.println("7. Return to Main Menu");
            System.out.print("Choose an option: ");
            int adminChoice = scanner.nextInt();

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
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void developerMenu() {
        while (true) {
            System.out.println("\n=== Developer Menu ===");
            System.out.println("1. Create Game");
            System.out.println("2. View My Games");
            System.out.println("3. Return to Main Menu");
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
                    System.out.print("Enter Developer ID: ");
                    int devId = scanner.nextInt();
                    System.out.print("Enter Game Price: ");
                    double price = scanner.nextDouble();

                    developerController.createGame(gameId, name, devId, price);
                    break;
                case 2:
                    System.out.print("Enter your Developer ID: ");
                    int developerId = scanner.nextInt();
                    List<Game> devGames = developerService.viewGamesByDeveloper(developerId);
                    if (devGames.isEmpty()) {
                        System.out.println("No games found for developer ID: " + developerId);
                    } else {
                        for (Game g : devGames) {
                            System.out.println("ID: " + g.getId() + ", Name: " + g.getName() +
                                    ", Price: $" + g.getPrice() + ", Approved: " + g.isApproved() +
                                    ", Category: " + g.getCategory());
                        }
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Top Up Balance");
            System.out.println("2. Purchase Games");
            System.out.println("3. View All Users");
            System.out.println("4. Return to Main Menu");
            System.out.print("Choose an option: ");
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    System.out.print("Enter User ID: ");
                    int userId = scanner.nextInt();
                    System.out.print("Enter top-up amount: ");
                    double amount = scanner.nextDouble();
                    User user = userRepository.getUserById(userId);
                    if (user != null) {
                        user.topUpBalance(amount);
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 2:
                    System.out.print("Enter User ID: ");
                    int uid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Game IDs to purchase (comma-separated): ");
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    List<Integer> gameIds = new ArrayList<>();
                    for (String p : parts) {
                        gameIds.add(Integer.parseInt(p.trim()));
                    }
                    orderService.purchaseGames(uid, gameIds);
                    break;
                case 3:
                    List<User> allUsers = userRepository.getAllUsers();
                    for (User u : allUsers) {
                        System.out.println("ID: " + u.getId() + ", Username: " + u.getUsername() +
                                ", Role: " + u.getRole() + ", Balance: $" + u.getBalance() +
                                ", Banned: " + u.isBanned());
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
