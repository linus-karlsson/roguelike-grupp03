package com.rougelike.magic;

public enum Spell {
    FIREBALL("Fireball", 10, "Attack", "Fire"),
    TORNADO("Tornado", 10, "Attack", "Air"),
    POISON("Poison", 10, "Attack", "Earth"),    
    FREEZE("Freeze", 10, "Attack", "Water"),
    SHIELD("Shield", 10, "Defense", "None"),
    HEAL("Heal", 10, "Heal", "None");
    

    private String name;
    private int baseStrength;
    private String type;
    private String element;

    private Spell(String name, int baseStrength, String type, String element) {
        this.name = name;
        this.baseStrength = baseStrength;
        this.type = type;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public String getType() {
        return type;
    }

    public String getElement() {
        return element;
    }
}
