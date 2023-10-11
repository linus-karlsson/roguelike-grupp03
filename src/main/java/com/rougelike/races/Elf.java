package com.rougelike.races;

public class Elf extends Race {
    private static final double ELF_STARTING_HEALTH = 100.0;
    private static final double ELF_STARTING_MANA = 20.0;
    private static final double ELF_STARTING_STRENGTH = 150.0;
    private static final double ELF_STARTING_DEXTERITY = 50.0;
    private static final double ELF_STARTING_INTELLIGENCE = 30.0;



    public Elf() {
        super(ELF_STARTING_HEALTH, ELF_STARTING_MANA, ELF_STARTING_STRENGTH, ELF_STARTING_DEXTERITY, ELF_STARTING_INTELLIGENCE);
    }

}