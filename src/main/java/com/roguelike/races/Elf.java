package com.roguelike.races;

public class Elf extends Race {
    private static final double ELF_STARTING_HEALTH = 100.0;
    private static final double ELF_STARTING_MANA = 20.0;
    private static final double ELF_STARTING_STRENGTH = 10.0;
    private static final double ELF_STARTING_DEXTERITY = 15.0;
    private static final double ELF_STARTING_INTELLIGENCE = 3.0;

    public Elf() {
        super(ELF_STARTING_HEALTH, ELF_STARTING_MANA, ELF_STARTING_STRENGTH, ELF_STARTING_DEXTERITY,
                ELF_STARTING_INTELLIGENCE);
    }

}
