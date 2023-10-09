package com.rougelike;

public class Player {
    private static final int MAX_LEVEL = 60;

    private String name;
    // private Race race;

    private double health;

    private int level;
    private double xp;
    private double xpToNextLevel;

    public Player(String name) {
        this.name = name;
        xpToNextLevel = 200.0;
    }

    // ??????
    public void nextLevel() {
        xpToNextLevel *= 3.5;
    }

    public void increaseXp(double gainedXp) {
        double xpOverspill = (xp + gainedXp) - xpToNextLevel;
        if (xpOverspill > 0) {
            // TODO: MAX_LEVEL
            assert level < MAX_LEVEL;
            level += 1;
            nextLevel();
        }
        xp += gainedXp;
    }
}
