package com.rougelike.magic;

public enum Spell {

    FIREBALL("Fireball", 10.0, new MagicAttack(), new ElementFire()),
    TORNADO("Tornado", 10.0, new MagicAttack(), new ElementAir()),
    POISON("Poison", 10.0, new MagicAttack(), new ElementEarth()),    
    FREEZE("Freeze", 10.0, new MagicAttack(), new ElementWater()),
    FIRESHIELD("Shield", 10.0, new MagicDefence(), new ElementFire()),
    HEAL("Heal", 10.0, new Heal(), new MagicElementType());
    

    private final String name;
    private final double baseStrength;
    private final MagicInvoker type;
    private final MagicElementType element;

    private Spell(String name, double baseStrength, MagicInvoker type, MagicElementType element) {
        this.name = name;
        this.baseStrength = baseStrength;
        this.type = type;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public double getBaseStrength() {
        return baseStrength;
    }

    public MagicInvoker getType() {
        return type;
    }

    public MagicElementType getElement() {
        return element;
    }
}
