package com.gamelibrary.utils;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.controllers.UserController;
import com.gamelibrary.menus.MainMenu;
import com.gamelibrary.repositories.ChatRepository;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;

import java.util.Scanner;

public class MenuUtils {

    public static void mainMenu(Scanner scanner, UserController userController, UserRepository userRepository,
                                GameRepository gameRepository, AdminController adminController,
                                DeveloperController developerController, ChatRepository chatRepository) {
        MainMenu.mainMenu(scanner, userController, userRepository, gameRepository, adminController, developerController, chatRepository);
    }
}