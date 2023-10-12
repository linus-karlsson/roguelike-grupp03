package com.rougelike.magic;


public class Magic {
    private static final int DEFAULT_BASE_STRENGHT = 5;

    private int baseStrenght;

    private String name;

    public Magic(String name, int baseStrenght) {
        if (name == null) {
            throw new IllegalArgumentException("Magic name cannot be null");
        }
        if (baseStrenght < 0) {
            throw new IllegalArgumentException("Base strength cannot be negative");
        }
        this.baseStrenght = baseStrenght;
        this.name = name;
    }

    public Magic(String name) {
        this(name, DEFAULT_BASE_STRENGHT);
    }

    public String getName() {
        return name;
    }

    // public double getStrength(Player player) {
    //     return baseStrenght * Math.pow(1.2, player.getLevel());
    // }

}
