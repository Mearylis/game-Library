package com.gamelibrary;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.controllers.UserController;
import com.gamelibrary.databases.Database;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.Role;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.CartRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.PurchaseRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.services.AdminService;
import com.gamelibrary.services.DeveloperService;
import com.gamelibrary.services.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();
        GameRepository gameRepository = new GameRepository();
        CartRepository cartRepository = new CartRepository();
        PurchaseRepository purchaseRepository = new PurchaseRepository();

        UserService userService = new UserService(userRepository, gameRepository, cartRepository, purchaseRepository);
        UserController userController = new UserController(userService, userRepository, cartRepository, gameRepository, purchaseRepository);

        Database database = new Database(gameRepository, userRepository);

        AdminService adminService = new AdminService(gameRepository, userRepository);
        DeveloperService developerService = new DeveloperService(gameRepository);
        AdminController adminController = new AdminController(adminService);
        DeveloperController developerController = new DeveloperController(developerService);

        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (true) {
            try {
                if (currentUser == null) {
                    System.out.println("1. Register\n2. Login\n3. Continue as Guest\n4. Exit");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> {
                            System.out.println("Enter username:");
                            String username = scanner.nextLine();
                            System.out.println("Enter password:");
                            String password = scanner.nextLine();
                            System.out.println("Enter role (USER, DEVELOPER):");
                            Role role = Role.valueOf(scanner.nextLine().toUpperCase());
                            int id = userRepository.getAllUsers().size() + 1;
                            userController.registerUser(id, username, password, role);
                            System.out.println("");
                        }
                        case 2 -> {
                            System.out.println("Enter username:");
                            String username = scanner.nextLine();
                            System.out.println("Enter password:");
                            String password = scanner.nextLine();
                            currentUser = userController.login(username, password);
                            if (currentUser != null) {
                                System.out.println("Login successful.");
                                handleUserProfileMenu(scanner, userController, currentUser);
                            } else {
                                System.out.println("Invalid username or password.");
                            }
                        }

                        case 3 -> handleGuestActions(scanner, gameRepository);
                        case 4 -> {
                            System.out.println("Exiting the program. Goodbye!");
                            System.exit(0);
                        }
                        default -> System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    switch (currentUser.getRole()) {
                        case ADMIN -> handleAdminActions(scanner, adminController);
                        case DEVELOPER -> handleDeveloperActions(scanner, developerController, gameRepository, currentUser);
                        case USER -> handleUserActions(scanner, userController, gameRepository, currentUser);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid value. Please try again.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid role or value entered. Please try again.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }


    private static void handleUserProfileMenu(Scanner scanner, UserController userController, User currentUser) {
        while (true) {
            System.out.println("\n\033[1;34m=== Profile Menu ===\033[0m");
            System.out.println("1. ✏ Edit Username");
            System.out.println("2. 🎮 Go to Main Menu");
            System.out.println("3. 🔙 Logout");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("\033[1;31mInvalid input! Please enter a number.\033[0m");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> updateUsername(scanner, userController, currentUser);
                case 2 -> {
                    handleUserActions(scanner, userController, new GameRepository(), currentUser);
                    return;
                }
                case 3 -> {
                    System.out.println("\n\033[1;34mLogging out...\033[0m");
                    return;
                }
                default -> System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");
            }
        }
    }




    private static void handleGuestActions(Scanner scanner, GameRepository gameRepository) {
        while (true) {
            System.out.println("1. View Games\n2. View Promotions\n3. Register as a User\n4. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            final GameRepository gameRepository1 = gameRepository;
            switch (choice) {
                case 1 -> {
                    System.out.println("Available Games:");
                    gameRepository1.getAllGames().forEach(game ->
                            System.out.println("ID: " + game.getId() + " | Name: " + game.getName() + " | Price: $" + game.getPrice()));
                }
                case 2 -> {
                    System.out.println("Current Promotions:");
//                    displayPromotions(GameRepository gameRepository1);
                    gameRepository1.getAllGames().stream()
                            .filter(game -> game.getDiscount() > 0)
                            .forEach(game -> System.out.println("Game: " + game.getName() + " | Discount: " + game.getDiscount() + "%"));
                }
                case 3 -> {
                    System.out.println("Redirecting to registration...");
                    return;
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }



    private static void handleGuestActions(Scanner scanner, GameRepository gameRepository) {
        while (true) {
            System.out.println("1. View Games\n2. View Promotions\n3. Register as a User\n4. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            final GameRepository gameRepository1 = gameRepository;
            switch (choice) {
                case 1 -> {
                    System.out.println("Available Games:");
                    gameRepository1.getAllGames().forEach(game ->
                            System.out.println("ID: " + game.getId() + " | Name: " + game.getName() + " | Price: $" + game.getPrice()));
                }
                case 2 -> {
                    System.out.println("Current Promotions:");
                    displayPromotions(GameRepository gameRepository1);
                    gameRepository1.getAllGames().stream()
                            .filter(game -> game.getDiscount() > 0)
                            .forEach(game -> System.out.println("Game: " + game.getName() + " | Discount: " + game.getDiscount() + "%"));
                }
                case 3 -> {
                    System.out.println("Redirecting to registration...");
                    return;
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }







//    public static void displayPromotions(GameRepository gameRepository) {
//        List<Game> allGames = gameRepository.getAllGames();
//
//        LocalDate currentDate = LocalDate.now();
//        List<Game> oldGames = allGames.stream()
//                .filter(game -> game.getReleaseDate().isBefore(currentDate.minusYears(5)))
//                .collect(Collectors.toList());
//        Random random = new Random();
//        while (oldGames.size() < 5 && oldGames.size() < allGames.size()) {
//            Game randomGame = allGames.get(random.nextInt(allGames.size()));
//            if (!oldGames.contains(randomGame)) {
//                oldGames.add(randomGame);
//            }
//        }
//
//        System.out.println("Current Promotions:");
//        System.out.println("There is a 20% discount on these games:");
//
//        for (Game game : oldGames) {
//            System.out.println(game.getName() + " - Original Price: $" + game.getPrice() +
//                    " | Discounted Price: $" + (game.getPrice() * 0.8));
//        }
//    }

    private static void handleAdminActions(Scanner scanner, AdminController adminController) {
        try {
            System.out.println("1. Approve Game\n2. Delete Game\n3. Ban User\n4. Unban User\n5. Delete User\n6. List Users\n7. List Games\n8. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                try {
                    System.out.println("Enter game ID to approve:");
                    int gameId = scanner.nextInt();
                    scanner.nextLine();
                    adminController.approveGame(gameId);
                    System.out.println("Game approved successfully.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for game ID. Please enter a numeric value.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("An error occurred while approving the game: " + e.getMessage());
                }
            } else if (choice == 2) {
                try {
                    System.out.println("Enter game ID to delete:");
                    int gameId = scanner.nextInt();
                    scanner.nextLine();
                    adminController.deleteGame(gameId);
                    System.out.println("Game deleted successfully.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for game ID. Please enter a numeric value.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("An error occurred while deleting the game: " + e.getMessage());
                }
            } else if (choice == 3) {
                try {
                    System.out.println("Enter user ID to ban:");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    adminController.banUser(userId);
                    System.out.println("User banned successfully.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for user ID. Please enter a numeric value.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("An error occurred while banning the user: " + e.getMessage());
                }
            } else if (choice == 4) {
                try {
                    System.out.println("Enter user ID to unban:");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    adminController.unbanUser(userId);
                    System.out.println("User unbanned successfully.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for user ID. Please enter a numeric value.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("An error occurred while unbanning the user: " + e.getMessage());
                }
            } else if (choice == 5) {
                try {
                    System.out.println("Enter user ID to delete:");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    adminController.deleteUser(userId);
                    System.out.println("User deleted successfully.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for user ID. Please enter a numeric value.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("An error occurred while deleting the user: " + e.getMessage());
                }
            } else if (choice == 6) {
                adminController.getAllUsers().forEach(u -> System.out.println(u.getUsername() + " - " + u.getRole()));
            } else if (choice == 7) {
                adminController.getAllGames().forEach(g -> System.out.println(g.getName() + " - $" + g.getPrice()));
            } else if (choice == 8) {
                System.out.println("Exiting...");
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid value. Please try again.");
            scanner.nextLine();
        }
    }


    private static void handleDeveloperActions(Scanner scanner, DeveloperController developerController, GameRepository gameRepository, User currentUser) {
        try {
            while (true) {
                System.out.println("\n\033[1;34m=== Developer Menu ===\033[0m");
                System.out.println("1. 🎮 Create Game");
                System.out.println("2. 📜 List Of Games");
                System.out.println("3. ❌ Delete Game");
                System.out.println("4. 🔙 Logout");
                System.out.print("Select an option: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("\033[1;31mInvalid input! Please enter a numeric value.\033[0m");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> createGame(scanner, developerController, gameRepository, currentUser);
                    case 2 -> listDeveloperGames(developerController, currentUser);
                    case 3 -> deleteGame(scanner, developerController, gameRepository, currentUser);
                    case 4 -> {
                        System.out.println("\033[1;34mLogging out...\033[0m");
                        return;
                    }
                    default -> System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
        }
    }



    private static void createGame(Scanner scanner, DeveloperController developerController, GameRepository gameRepository, User currentUser) {
        try {
            System.out.println("\n\033[1;34m=== Create a New Game ===\033[0m");

            System.out.print("Enter game name: ");
            String name = scanner.nextLine();

            System.out.print("Enter game price ($): ");
            if (!scanner.hasNextDouble()) {
                System.out.println("\033[1;31mInvalid price! Please enter a numeric value.\033[0m");
                scanner.nextLine();
                return;
            }
            double price = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter game size (GB): ");
            if (!scanner.hasNextDouble()) {
                System.out.println("\033[1;31mInvalid size! Please enter a numeric value.\033[0m");
                scanner.nextLine();
                return;
            }
            double sizeGB = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter age restriction (e.g. 0, 12, 16, 18): ");
            if (!scanner.hasNextInt()) {
                System.out.println("\033[1;31mInvalid age restriction! Please enter a numeric value.\033[0m");
                scanner.nextLine();
                return;
            }
            int ageRestriction = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter game genre (e.g. RPG, Shooter, Adventure): ");
            String genre = scanner.nextLine();

            System.out.println("Enter game description: ");
            String description = scanner.nextLine();

            int id = gameRepository.getAllGames().size() + 1;
            developerController.createGame(id, name, currentUser.getId(), price, sizeGB, ageRestriction, genre, description);

            System.out.println("\033[1;32mGame created successfully!\033[0m");
        } catch (Exception e) {
            System.out.println("❌ An error occurred while creating the game: " + e.getMessage());
        }
    }




    private static void listDeveloperGames(DeveloperController developerController, User currentUser) {
        try {
            List<Game> developerGames = developerController.getGamesByDeveloper(currentUser.getId());

            if (developerGames.isEmpty()) {
                System.out.println("\033[1;33mYou have not created any games yet.\033[0m");
                return;
            }

            System.out.println("\n\033[1;36m=== Your Games ===\033[0m");
            for (Game game : developerGames) {
                System.out.printf("""
                    \033[1;34mGame ID:\033[0m %d
                    \033[1;34mName:\033[0m %s
                    \033[1;34mPrice:\033[0m $%.2f
                    \033[1;34mSize:\033[0m %.1f GB
                    \033[1;34mAge Restriction:\033[0m %d+
                    \033[1;34mGenre:\033[0m %s
                    \033[1;34mDescription:\033[0m %s
                    -------------------------------
                    """, game.getId(), game.getName(), game.getPrice(), game.getSizeGB(), game.getAgeRestriction(), game.getGenre(), game.getDescription());
            }
        } catch (Exception e) {
            System.out.println("❌ An error occurred while listing your games: " + e.getMessage());
        }
    }




    private static void deleteGame(Scanner scanner, DeveloperController developerController, GameRepository gameRepository, User currentUser) {
        try {
            System.out.print("\n\033[1;34mEnter game ID to delete: \033[0m");

            if (!scanner.hasNextInt()) {
                System.out.println("\033[1;31mInvalid input! Please enter a numeric value.\033[0m");
                scanner.nextLine();
                return;
            }

            int gameId = scanner.nextInt();
            scanner.nextLine();
            Game game = gameRepository.getGameById(gameId);

            if (game == null || game.getDeveloperId() != currentUser.getId()) {
                System.out.println("\033[1;31mGame not found or you do not have permission to delete this game.\033[0m");
                return;
            }

            System.out.printf("\n\033[1;33mAre you sure you want to delete \"%s\"? (yes/no): \033[0m", game.getName());
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("\033[1;34mDeletion cancelled.\033[0m");
                return;
            }

            developerController.deleteGame(gameId, currentUser.getId());
            System.out.println("\033[1;32mGame deleted successfully.\033[0m");
        } catch (Exception e) {
            System.out.println("❌ An error occurred while deleting the game: " + e.getMessage());
        }
    }





    private static void handleUserActions(Scanner scanner, UserController userController, GameRepository gameRepository, User currentUser) {
        while (currentUser != null) {
            try {
                System.out.println("1. Add to Cart\n2. View Cart\n3. Purchase Games\n4. Remove from Cart\n5. Top Up Balance\n6. View Purchased Games\n7. View Game Details\n8. Exit ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        try {
                            System.out.println("Enter game ID to add to cart:");
                            gameRepository.getAllGames().forEach(game ->
                                    System.out.println("ID: " + game.getId() + " | Name: " + game.getName() + " | Price: $" + game.getPrice())
                            );
                            int gameId = scanner.nextInt();
                            scanner.nextLine();

                            if (gameRepository.getGameById(gameId) == null) {
                                System.out.println("No game found with ID: " + gameId + ". Please try again.");
                            } else {
                                userController.addToCart(currentUser.getId(), gameId);
                                System.out.println("Game added to cart successfully.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid game ID. Please enter a numeric value.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("An unexpected error occurred: " + e.getMessage());
                        }
                    }

                    case 2 -> {
                        try {
                            var cartGames = userController.getCartGames(currentUser.getId());
                            if (cartGames.isEmpty()) {
                                System.out.println("Your cart is empty.");
                            } else {
                                cartGames.forEach(g -> System.out.println(g.getName() + " - $" + g.getPrice()));
                            }
                        } catch (Exception e) {
                            System.out.println("An error occurred while fetching your cart: " + e.getMessage());
                        }
                    }

                    case 3 -> {
                        try {
                            System.out.println("Enter card number:");
                            String cardNumber = scanner.nextLine();
                            System.out.println("Enter bank:");
                            String bank = scanner.nextLine();
                            userController.purchaseGames(currentUser.getId(), cardNumber, bank);
                        } catch (Exception e) {
                            System.out.println("An error occurred during the purchase process: " + e.getMessage());
                        }
                    }

                    case 4 -> {
                        try {
                            System.out.println("Enter game ID to remove from cart:");
                            int gameId = scanner.nextInt();
                            scanner.nextLine();
                            userController.removeFromCart(currentUser.getId(), gameId);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid game ID. Please enter a numeric value.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("An error occurred while removing the game from your cart: " + e.getMessage());
                        }
                    }

                    case 5 -> {
                        try {
                            System.out.println("Enter amount to top up:");
                            double amount = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("Enter card type (Visa, Mastercard, Other):");
                            String cardType = scanner.nextLine();
                            System.out.println("Enter card number:");
                            String cardNumber = scanner.nextLine();
                            userController.topUpBalance(currentUser.getId(), amount, cardNumber, cardType);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid amount. Please enter a valid number.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("An error occurred while topping up your balance: " + e.getMessage());
                        }
                    }

                    case 6 -> {
                        try {
                            var purchasedGames = userController.getPurchasedGames(currentUser.getId());
                            if (purchasedGames.isEmpty()) {
                                System.out.println("You have not purchased any games yet.");
                            } else {
                                purchasedGames.forEach(p -> System.out.println("Game ID: " + p.getGameId() + " | Purchase Date: " + p.getPurchaseDate()));
                            }
                        } catch (Exception e) {
                            System.out.println("An error occurred while fetching your purchased games: " + e.getMessage());
                        }
                    }
                    case 7 -> {
                        System.out.println("Enter the game ID to view details:");
                        int gameId = scanner.nextInt();
                        scanner.nextLine();

                        var game = gameRepository.getGameById(gameId);
                        if (game == null) {
                            System.out.println("Game not found. Please enter a valid ID.");
                            //} else {
                            //handleUserActions(scanner, userController, currentUser, game); болу керек негізі можешь исправить куррентюзермен геймды косып
                        }
                    }
                    case 8 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }


                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid value. Please try again.");
                scanner.nextLine();
            }
        }
    }



    private static void handleGameChat(Scanner scanner, UserController userController, ChatRepository chatRepository, User currentUser, Game game) {
        while (true) {
            System.out.println("\n\033[1;34m--- Chat for " + game.getName() + " ---\033[0m");
            System.out.println("1. 📩 Send Message");
            System.out.println("2. 📜 View Chat History");
            System.out.println("3. 🔙 Back to Game Menu");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("\033[1;31mInvalid input! Please enter a number.\033[0m");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> sendMessage(scanner, userController, chatRepository, currentUser, game);
                case 2 -> viewChat(chatRepository, game);
                case 3 -> {
                    System.out.println("\033[1;34mReturning to game menu...\033[0m");
                    return;
                }
                default -> System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");
            }
        }
    }





    private static void sendMessage(Scanner scanner, UserController userController, ChatRepository chatRepository, User currentUser, Game game) {
        System.out.print("\n\033[1;36mEnter your message:\033[0m ");
        String messageText = scanner.nextLine();

        if (messageText.isEmpty()) {
            System.out.println("\033[1;31mMessage cannot be empty!\033[0m");
            return;
        }

        chatRepository.sendMessage(currentUser.getId(), game.getId(), messageText);
        System.out.println("\033[1;32mMessage sent!\033[0m");
    }

    // 📌 Метод просмотра истории чата
    private static void viewChat(ChatRepository chatRepository, Game game) {
        List<Message> messages = chatRepository.getMessagesByGame(game.getId());

        if (messages.isEmpty()) {
            System.out.println("\n\033[1;33mNo messages in chat yet.\033[0m");
            return;
        }

        System.out.println("\n\033[1;36m--- Chat History for " + game.getName() + " ---\033[0m");
        for (Message msg : messages) {
            System.out.printf("[%s] User %d: %s\n", msg.getTimestamp(), msg.getSenderId(), msg.getText());
        }
    }





    private static void handleUserProfileMenu(Scanner scanner, UserController userController, GameRepository gameRepository, User currentUser) {
        while (true) {
            System.out.println("\n\033[1;34m=== Profile Menu ===\033[0m");
            System.out.println("1. ✏ Edit Username");
            System.out.println("2. 🎮 Go to Main Menu");
            System.out.println("3. 👥 Friends Menu");
            System.out.println("4. 🔙 Logout");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("\033[1;31mInvalid input! Please enter a number.\033[0m");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> updateUsername(scanner, userController, currentUser);
                case 2 -> handleUserActions(scanner, userController, gameRepository, currentUser);
                case 3 -> handleFriendsMenu(scanner, userController, gameRepository, currentUser);
                case 4 -> {
                    System.out.println("\n\033[1;34mLogging out...\033[0m");
                    return;
                }
                default -> System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");
            }
        }
    }



    private static void handleFriendsMenu(Scanner scanner, UserController userController, GameRepository gameRepository, User currentUser) {
        while (true) {
            System.out.println("\n\033[1;34m=== Friends Menu ===\033[0m");
            System.out.println("1. ➕ Add Friend");
            System.out.println("2. 👥 View Friends");
            System.out.println("3. 🎮 View Common Games");
            System.out.println("4. 🔙 Back to Profile Menu");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("\033[1;31mInvalid input! Please enter a number.\033[0m");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addFriend(scanner, userController, currentUser);
                case 2 -> viewFriends(userController, currentUser);
                case 3 -> viewCommonGames(scanner, userController, gameRepository, currentUser);
                case 4 -> {
                    System.out.println("\n\033[1;34mReturning to Profile Menu...\033[0m");
                    return;
                }
                default -> System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");
            }
        }
    }




    private static void addFriend(Scanner scanner, UserController userController, User currentUser) {
        System.out.print("\n\033[1;36mEnter Friend's User ID:\033[0m ");
        if (!scanner.hasNextInt()) {
            System.out.println("\033[1;31mInvalid input! Please enter a number.\033[0m");
            scanner.nextLine();
            return;
        }

        int friendId = scanner.nextInt();
        scanner.nextLine();

        userController.addFriend(currentUser.getId(), friendId);
    }






    private static void viewFriends(UserController userController, User currentUser) {
        List<Integer> friends = userController.getFriends(currentUser.getId());

        if (friends.isEmpty()) {
            System.out.println("\n\033[1;33mYou have no friends added yet.\033[0m");
            return;
        }

        System.out.println("\n\033[1;36mYour Friends:\033[0m");
        for (int friendId : friends) {
            System.out.println("- Friend ID: " + friendId);
        }
    }






    private static void viewCommonGames(Scanner scanner, UserController userController, GameRepository gameRepository, User currentUser) {
        System.out.print("\n\033[1;36mEnter Friend's User ID:\033[0m ");
        if (!scanner.hasNextInt()) {
            System.out.println("\033[1;31mInvalid input! Please enter a number.\033[0m");
            scanner.nextLine();
            return;
        }

        int friendId = scanner.nextInt();
        scanner.nextLine();

        List<Game> commonGames = gameRepository.getCommonGames(currentUser.getId(), friendId);

        if (commonGames.isEmpty()) {
            System.out.println("\n\033[1;33mNo common games found.\033[0m");
            return;
        }

        System.out.println("\n\033[1;36mCommon Games with Friend " + friendId + ":\033[0m");
        for (Game game : commonGames) {
            System.out.println("- " + game.getName() + " ($" + game.getPrice() + ")");
        }
    }





    private static void updateUsername(Scanner scanner, UserController userController, User currentUser) {
        System.out.print("\n\033[1;36mEnter new username:\033[0m ");
        String newUsername = scanner.nextLine();

        if (newUsername.isEmpty()) {
            System.out.println("\033[1;31mUsername cannot be empty!\033[0m");
            return;
        }

        userController.updateUsername(currentUser.getId(), newUsername);
    }




    private static void handleReviews(Scanner scanner, Game game) {
        System.out.println("\n\033[1;36m=== REVIEWS FOR " + game.getName() + " ===\033[0m");

        List<String> reviews = gameRepository.getReviews(game.getId());

        if (reviews.isEmpty()) {
            System.out.println("\033[1;33mNo reviews yet. Be the first to review!\033[0m");
        } else {
            for (String review : reviews) {
                System.out.println("- " + review);
            }
        }

        System.out.print("\n\033[1;36mWould you like to leave a review? (yes/no): \033[0m");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            System.out.print("\033[1;36mEnter your review: \033[0m");
            String newReview = scanner.nextLine();
            gameRepository.addReview(game.getId(), newReview);
            System.out.println("\033[1;32mThank you for your review!\033[0m");
        }
    }






//    private static void handleUserActions(Scanner scanner, UserController userController, User currentUser, Game game) {
//    while (true) {
//        System.out.println("\n--- Game: " + game.getName() + " ---");
//        System.out.println("1. Leave a Review\n2. Donate to the Game\n3. Return to Main Menu");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1 -> { // Оставить отзыв
//                System.out.println("Enter your review:");
//                String review = scanner.nextLine();
//
//                int rating;
//                while (true) {
//                    System.out.println("Rate the game (1-5):");   сделай так чтобы все рейтинги от всех игроков на эту конкретную игру сохранялись и чтобы было авг от этих оценок чтобы потом сделать что то по типу *Топ игры по рейтингам*
//                    rating = scanner.nextInt();
//                    scanner.nextLine();
//
//                    if (rating >= 1 && rating <= 5) {
//                        break;
//                    } else {
//                        System.out.println("Invalid rating. Please enter a number between 1 and 5.");
//                    }
//                }
//
//                userController.leaveReview(currentUser.getId(), game.getId(), review, rating);
//                System.out.println("Thank you for your review and rating!");
//            }
//
//            case 2 -> { // Донат в игру
//                System.out.println("Enter donation amount:");
//                double amount = scanner.nextDouble();
//                scanner.nextLine();
//
//                if (userController.getBalance(currentUser.getId()) >= amount) {
//                    userController.withdrawFromCard(currentUser.getId(), amount);
//                    userController.donateToGame(currentUser.getId(), game.getId(), amount);
//                    System.out.println("Donation successful!");
//                } else {
//                    System.out.println("Insufficient balance. Would you like to top up? (yes/no)");
//                    String response = scanner.nextLine();
//                    if (response.equalsIgnoreCase("yes")) {
//                        topUpBalance(scanner, userController, currentUser);
//                    }
//                }
//            }
//
//            case 3 -> {
//                return;
//            }
//
//            default -> System.out.println("Invalid choice. Please try again.");
//        }
//    }
//}
}