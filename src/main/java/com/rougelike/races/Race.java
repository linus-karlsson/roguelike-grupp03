package com.rougelike.races;

public abstract class Race {
    private final double startingHealth;
    private final double startingMana;
    private final double startingStrength;
    private final double startingDexterity;
    private final double startingIntelligence;



    public Race(double health, double mana, double startingStrength, double dexterity, double intelligence) {
        this.startingHealth = health;
        this.startingMana = mana;
        this.startingStrength = startingStrength;
        this.startingDexterity = dexterity;
        this.startingIntelligence = intelligence;

    }

    public double getStartingHealth() {
        return startingHealth;
    }

    public double getStartingMana() {
        return startingMana;
    }

    public double getStartingStrength() {
        return startingStrength;
    }

    public double getStartingIntelligence() {
        return startingIntelligence;
    }

    public double getStartingDexterity() {
        return startingDexterity;
    }


}


//Git test