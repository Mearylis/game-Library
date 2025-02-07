package com.gamelibrary.services;
import com.gamelibrary.models.Game;
import com.gamelibrary.repositories.GameRepository;
import com.gamelibrary.repositories.UserRepository;
import com.gamelibrary.validators.GameValidator;
import java.util.List;

public class DeveloperService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public DeveloperService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public int createGame(String name, int developerId, double price, double sizeGB, int ageRestriction, String genre, String description) {
        if (!GameValidator.validateGame(name, price, sizeGB, ageRestriction)) {
            throw new IllegalArgumentException("Invalid game data.");
        }

        int gameId = gameRepository.getAllGames().size() + 1;
        Game game = new Game(gameId, name, developerId, price, sizeGB, ageRestriction, genre, description);
        gameRepository.addGame(game);
        return gameId;
    }

    public List<Game> getGamesByDeveloper(int developerId) {
        return gameRepository.getAllGames().stream()
                .filter(game -> game.getDeveloperId() == developerId)
                .toList();
    }

    public void deleteGame(int gameId, int developerId) {
        Game game = gameRepository.getGameById(gameId);
        if (game != null && game.getDeveloperId() == developerId) {
            gameRepository.deleteGame(gameId);
        }
    }

    public double getEarnings(int developerId) {
        return gameRepository.getAllGames().stream()
                .filter(game -> game.getDeveloperId() == developerId && game.isApproved())
                .mapToDouble(Game::getPrice)
                .sum();
    }
}