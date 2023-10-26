package com.roguelike.races;

public class Human extends Race {
    private static final double HUMAN_STARTING_HEALTH = 100.0;
    private static final double HUMAN_STARTING_MANA = 20.0;
    private static final double HUMAN_STARTING_STRENGTH = 15.0;
    private static final double HUMAN_STARTING_DEXTERITY = 5.0;
    private static final double HUMAN_STARTING_INTELLIGENCE = 3.0;

    public Human() {
        super(HUMAN_STARTING_HEALTH, HUMAN_STARTING_MANA, HUMAN_STARTING_STRENGTH, HUMAN_STARTING_DEXTERITY,
                HUMAN_STARTING_INTELLIGENCE);
    }
}
