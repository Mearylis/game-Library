package com.gamelibrary;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.controllers.UserController;
import com.gamelibrary.databases.Database;
import com.gamelibrary.databases.DatabaseInitializer;
import com.gamelibrary.repositories.ChatRepository;
import com.gamelibrary.repositories.FriendRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.services.AdminService;
import com.gamelibrary.services.DeveloperService;
import com.gamelibrary.services.UserService;
import com.gamelibrary.utils.MenuUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Connection connection = database.getConnection();

        try {
            DatabaseInitializer.initialize(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при инициализации базы данных.");
            return;
        }

        UserRepository userRepository = new UserRepository(connection);
        GameRepository gameRepository = new GameRepository(connection);
        FriendRepository friendRepository = new FriendRepository(database);
        ChatRepository chatRepository = new ChatRepository(connection);

        UserService userService = new UserService(userRepository, gameRepository);
        DeveloperService developerService = new DeveloperService(userRepository, gameRepository);
        AdminService adminService = new AdminService(userRepository, gameRepository);

        UserController userController = new UserController(userService, friendRepository, userRepository);
        DeveloperController developerController = new DeveloperController(developerService);
        AdminController adminController = new AdminController(adminService);

        Scanner scanner = new Scanner(System.in);
        MenuUtils.mainMenu(scanner, userController, userRepository, gameRepository, adminController, developerController, chatRepository);
    }
}