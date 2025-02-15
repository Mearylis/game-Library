package com.gamelibrary.menus;

import com.gamelibrary.controllers.UserController;
import com.gamelibrary.models.Game;
import com.gamelibrary.models.Message;
import com.gamelibrary.models.User;
import com.gamelibrary.repositories.ChatRepository;
import com.gamelibrary.repositories.GameRepository;
import java.util.List;
import java.util.Scanner;

public class ChatMenu {
    public static void handleGameChat(Scanner scanner, UserController userController, ChatRepository chatRepository, User currentUser, GameRepository gameRepository) {
        System.out.print("Enter Game ID to chat: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();

        Game game = gameRepository.getGameById(gameId);
        if (game == null) {
            System.out.println("Game not found!");
            return;
        }

        while (true) {
            System.out.println("\n--- Chat for " + game.getName() + " ---");

            List<Message> recentMessages = chatRepository.getLastMessagesByGame(game.getId());

            if (!recentMessages.isEmpty()) {
                System.out.println("\n--- Last 5 Messages ---");
                for (int i = recentMessages.size() - 1; i >= 0; i--) {
                    Message msg = recentMessages.get(i);
                    System.out.printf("[%s] %s: %s\n", msg.getTimestamp(), msg.getSenderName(), msg.getText());
                }
            } else {
                System.out.println("\nNo messages yet. Be the first to write!");
            }

            System.out.println("\n1. Send Message");
            System.out.println("2. View Full Chat History");
            System.out.println("3. Back to Game Menu");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> sendMessage(scanner, chatRepository, currentUser, game);
                case 2 -> viewChat(chatRepository, game);
                case 3 -> {
                    System.out.println("Returning to game menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void sendMessage(Scanner scanner, ChatRepository chatRepository, User currentUser, Game game) {
        System.out.print("Enter your message: ");
        String text = scanner.nextLine();
        chatRepository.sendMessage(currentUser.getId(), game.getId(), text);
        System.out.println("Message sent!");
    }

    private static void viewChat(ChatRepository chatRepository, Game game) {
        List<Message> messages = chatRepository.getLastMessagesByGame(game.getId());

        if (messages.isEmpty()) {
            System.out.println("\nNo messages in chat yet.");
            return;
        }

        System.out.println("\n--- Chat History for " + game.getName() + " ---");
        for (Message msg : messages) {
            System.out.printf("[%s] %s: %s\n", msg.getTimestamp(), msg.getSenderName(), msg.getText());
        }
    }
}