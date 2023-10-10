package com.rougelike.races;

public abstract class Race {
    private double health;
    private double mana;
    private double strength;
    private double dexterity;
    private double intelligence;



    public Race(double health, double mana, double strength, double dexterity, double intelligence) {
        this.health = health;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;

    }

    public double getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    public double getStrength() {
        return strength;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public double getDexterity() {
        return dexterity;
    }


}


//Git test