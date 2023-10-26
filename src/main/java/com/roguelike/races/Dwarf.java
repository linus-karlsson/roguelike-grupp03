package com.roguelike.races;

public class Dwarf extends Race {

    private static final double DWARF_STARTING_HEALTH = 100.0;
    private static final double DWARF_STARTING_MANA = 20.0;
    private static final double DWARF_STARTING_STRENGTH = 15.0;
    private static final double DWARF_STARTING_DEXTERITY = 5.0;
    private static final double DWARF_STARTING_INTELLIGENCE = 3.0;

    public Dwarf() {
        super(DWARF_STARTING_HEALTH, DWARF_STARTING_MANA, DWARF_STARTING_STRENGTH, DWARF_STARTING_DEXTERITY,
                DWARF_STARTING_INTELLIGENCE);
    }

}
