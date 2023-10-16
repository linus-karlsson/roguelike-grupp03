package com.rougelike.races;

public abstract class Race {
    private double startingHealth;
    private double startingMana;
    private double startingStrength;
    private double startingDexterity;
    private double startingIntelligence;



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