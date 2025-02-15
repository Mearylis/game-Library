package com.gamelibrary.menus;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.controllers.UserController;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.ChatRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;
import java.util.Scanner;

public class MainMenu {
    public static void mainMenu(Scanner scanner, UserController userController, UserRepository userRepository,
                                GameRepository gameRepository, AdminController adminController,
                                DeveloperController developerController, ChatRepository chatRepository) {
        User currentUser = null;

        while (true) {
            try {
                if (currentUser == null) {
                    System.out.println("\n=== Main Menu ===");
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("3. Continue as Guest");
                    System.out.println("4. Exit");
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
                            System.out.print("Enter username: ");
                            String username = scanner.nextLine();
                            System.out.print("Enter password: ");
                            String password = scanner.nextLine();
                            System.out.print("Enter role (USER, DEVELOPER): ");
                            String role = scanner.nextLine().toUpperCase();
                            System.out.print("Enter your age: "); // Запрашиваем возраст
                            int age = scanner.nextInt();
                            scanner.nextLine(); // Очистка буфера после nextInt()
                            int id = userRepository.getAllUsers().size() + 1;
                            userController.registerUser(id, username, password, role, age); // Передаем возраст
                            System.out.println("Registration successful!");
                        }
                        case 2 -> {
                            System.out.print("Enter username: ");
                            String username = scanner.nextLine();
                            System.out.print("Enter password: ");
                            String password = scanner.nextLine();
                            currentUser = userController.login(username, password);
                            if (currentUser != null) {
                                System.out.println("Login successful!");
                                System.out.println("User role: " + currentUser.getRole());

                                switch (currentUser.getRole()) {
                                    case "ADMIN" -> AdminMenu.handleAdminActions(scanner, adminController);
                                    case "DEVELOPER" -> DeveloperMenu.handleDeveloperActions(scanner, developerController, currentUser);
                                    case "USER" -> UserMenu.handleUserActions(scanner, userController, currentUser, gameRepository, chatRepository);
                                    default -> System.out.println("Unknown role. Please contact support.");
                                }
                            } else {
                                System.out.println("Invalid username or password.");
                            }
                        }
                        case 3 -> GuestMenu.handleGuestActions(scanner, gameRepository);
                        case 4 -> {
                            System.out.println("Exiting the program. Goodbye!");
                            System.exit(0);
                        }
                        default -> System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    switch (currentUser.getRole()) {
                        case "ADMIN" -> AdminMenu.handleAdminActions(scanner, adminController);
                        case "DEVELOPER" -> DeveloperMenu.handleDeveloperActions(scanner, developerController, currentUser);
                        case "USER" -> UserMenu.handleUserActions(scanner, userController, currentUser, gameRepository, chatRepository);
                        default -> System.out.println("Unknown role. Please contact support.");
                    }
                    currentUser = null;
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}