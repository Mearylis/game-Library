package com
        .gamelibrary.menus;

import com.gamelibrary.controllers.UserController;
import com.gamelibrary.models.User;
import java.util.List;
import java.util.Scanner;

public class FriendsMenu {
    public static void handleFriendsMenu(Scanner scanner, UserController userController, User currentUser) {
        while (true) {
            System.out.println("\n=== Friends Menu ===");
            System.out.println("1. Send Friend Request");
            System.out.println("2. View Friend Requests");
            System.out.println("3. View Friends");
            System.out.println("4. Back");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> sendFriendRequest(scanner, userController, currentUser);
                case 2 -> viewFriendRequests(scanner, userController, currentUser);
                case 3 -> viewFriends(userController, currentUser);
                case 4 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void sendFriendRequest(Scanner scanner, UserController userController, User currentUser) {
        System.out.print("\nEnter the username of the friend you want to add: ");
        String friendUsername = scanner.nextLine().trim();

        if (friendUsername.isEmpty()) {
            System.out.println("Username cannot be empty!");
            return;
        }

        int friendId = userController.getUserIdByUsername(friendUsername);

        if (friendId == -1) {
            System.out.println("User not found!");
            return;
        }

        if (friendId == currentUser.getId()) {
            System.out.println("You cannot add yourself as a friend!");
            return;
        }

        boolean requestSent = userController.sendFriendRequest(currentUser.getId(), friendId);
        if (requestSent) {
            System.out.println("Friend request sent to " + friendUsername);
        } else {
            System.out.println("A pending request already exists or you are already friends.");
        }
    }

    private static void viewFriendRequests(Scanner scanner, UserController userController, User currentUser) {
        List<User> pendingRequests = userController.getPendingRequests(currentUser.getId());

        if (pendingRequests.isEmpty()) {
            System.out.println("\nNo new friend requests.");
            return;
        }

        System.out.println("\nPending Friend Requests:");
        for (int i = 0; i < pendingRequests.size(); i++) {
            System.out.println((i + 1) + ". " + pendingRequests.get(i).getUsername());
        }

        System.out.print("\nEnter the number of the request to respond (or 0 to go back): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input!");
            scanner.nextLine();
            return;
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            return;
        }

        if (choice < 1 || choice > pendingRequests.size()) {
            System.out.println("Invalid selection!");
            return;
        }

        User selectedUser = pendingRequests.get(choice - 1);

        System.out.print("\nDo you want to accept (yes) or decline (no) the request from " + selectedUser.getUsername() + "? ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            boolean accepted = userController.acceptFriendRequest(selectedUser.getId(), currentUser.getId());
            if (accepted) {
                System.out.println("You are now friends with " + selectedUser.getUsername());
            } else {
                System.out.println("Error accepting friend request.");
            }
        } else if (response.equals("no")) {
            boolean declined = userController.declineFriendRequest(selectedUser.getId(), currentUser.getId());
            if (declined) {
                System.out.println("Friend request from " + selectedUser.getUsername() + " declined.");
            } else {
                System.out.println("Error declining friend request.");
            }
        } else {
            System.out.println("Invalid input! Type 'yes' to accept or 'no' to decline.");
        }
    }

    private static void viewFriends(UserController userController, User currentUser) {
        List<User> friends = userController.getFriends(currentUser.getId());

        if (friends.isEmpty()) {
            System.out.println("You have no friends yet.");
            return;
        }

        System.out.println("\n=== Your Friends ===");
        for (User friend : friends) {
            System.out.printf("Username: %s\n", friend.getUsername());
        }
    }
}