package com.gamelibrary.controllers;

import com.gamelibrary.models.Game;
import com.gamelibrary.services.DeveloperService;
import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    public void createGame(int id, String name, int developerId, double price) {
        developerService.createGame(id, name, developerId, price);
    }

    public void listMyGames(int developerId) {
        List<Game> games = developerService.viewGamesByDeveloper(developerId);
        if (games.isEmpty()) {
            System.out.println("No games found for developer ID: " + developerId);
        } else {
            for (Game g : games) {
                System.out.println("ID: " + g.getId() + ", Name: " + g.getName() +
                        ", Price: $" + g.getPrice() + ", Approved: " + g.isApproved() +
                        ", Category: " + g.getCategory());
            }
        }
    }
}
