package com.rougelike;

public class Player {
    private static final int MAX_LEVEL = 60;
    private static final double LEVEL_MULTIPLIER = 3.5;

    private String name;
    // private Race race;
    private double health;

    private int level;
    private double xp;
    private double xpToNextLevel;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        xpToNextLevel = 200.0;
    }

    // ??????
    private void nextLevel() {
        xpToNextLevel *= LEVEL_MULTIPLIER;
    }

    public void increaseXp(double gainedXp) {
        if (level == MAX_LEVEL) {
            return;
        }
        double xpOverspill = (xp + gainedXp) - xpToNextLevel;
        if (xpOverspill >= 0) {
            level += 1;
            nextLevel();
        }
        xp += gainedXp;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public double getLevelMultiplier() {
        return LEVEL_MULTIPLIER;
    }

    public double getXp() {
        return xp;
    }

    public double getXpToNextLevel() {
        return xpToNextLevel;
    }

}
