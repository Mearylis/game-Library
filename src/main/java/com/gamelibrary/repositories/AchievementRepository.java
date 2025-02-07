package com.gamelibrary.repositories;

import com.gamelibrary.models.Achievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementRepository {
    private List<Achievement> achievements = new ArrayList<>();

    public void addAchievement(Achievement achievement) {
        achievements.add(achievement);
    }

    public List<Achievement> getAchievementsByUserIdAndGameId(int userId, int gameId) {
        return achievements.stream()
                .filter(a -> a.getUserId() == userId && a.getGameId() == gameId)
                .toList();
    }

    public List<Achievement> getAllAchievements() {
        return achievements;
    }
}