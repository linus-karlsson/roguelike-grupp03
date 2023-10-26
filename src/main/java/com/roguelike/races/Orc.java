package com.roguelike.races;

public class Orc extends Race {
    private static final double ORC_STARTING_HEALTH = 100.0;
    private static final double ORC_STARTING_MANA = 20.0;
    private static final double ORC_STARTING_STRENGTH = 15.0;
    private static final double ORC_STARTING_DEXTERITY = 5.0;
    private static final double ORC_STARTING_INTELLIGENCE = 3.0;

    public Orc() {
        super(ORC_STARTING_HEALTH, ORC_STARTING_MANA, ORC_STARTING_STRENGTH, ORC_STARTING_DEXTERITY,
                ORC_STARTING_INTELLIGENCE);
    }
}
