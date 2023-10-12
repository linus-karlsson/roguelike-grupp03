package com.rougelike.magic;

public enum Spell {
    FIREBALL("Fireball", 10, "Attack", new ElementFire()),
    TORNADO("Tornado", 10, "Attack", new ElementAir()),
    POISON("Poison", 10, "Attack", new ElementEarth()),    
    FREEZE("Freeze", 10, "Attack", new ElementWater()),
    FIRESHIELD("Shield", 10, "Defense", new ElementFire()),
    HEAL("Heal", 10, "Heal", new MagicElementType());
    

    private String name;
    private int baseStrength;
    private String type;
    private MagicElementType element;

    private Spell(String name, int baseStrength, String type, MagicElementType element) {
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

    public MagicElementType getElement() {
        return element;
    }
}
