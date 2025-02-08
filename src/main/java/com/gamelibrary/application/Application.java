package com.gamelibrary.application;

import com.gamelibrary.controllers.AdminController;
import com.gamelibrary.controllers.DeveloperController;
import com.gamelibrary.controllers.UserController;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.services.AdminService;
import com.gamelibrary.services.DeveloperService;
import com.gamelibrary.services.UserService;

public class Application {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final DeveloperService developerService;
    private final UserService userService;
    private final AdminController adminController;
    private final DeveloperController developerController;
    private final UserController userController;
    private final DatabaseInitializer databaseInitializer;
    private final Prepopulator prepopulator;
    private final MenuHandler menuHandler;

    public Application() {
        this.gameRepository = new GameRepository();
        this.userRepository = new UserRepository();
        this.adminService = new AdminService(gameRepository, userRepository);
        this.developerService = new DeveloperService(gameRepository);
        this.userService = new UserService(userRepository, gameRepository);
        this.adminController = new AdminController(adminService);
        this.developerController = new DeveloperController(developerService);
        this.userController = new UserController(userService);
        this.databaseInitializer = new DatabaseInitializer(gameRepository, userRepository);
        this.prepopulator = new Prepopulator(userRepository);
        this.menuHandler = new MenuHandler(adminController, developerController, userController, gameRepository);
        initialize();
    }

    private void initialize() {
        databaseInitializer.initializeDatabase();
        prepopulator.prepopulate();
    }

    public void run() {
        menuHandler.run();
    }
}