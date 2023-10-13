package com.rougelike.magic;

public enum Spell {
    FIREBALL("Fireball", 10, new MagicAttack(), new ElementFire()),
    TORNADO("Tornado", 10, new MagicAttack(), new ElementAir()),
    POISON("Poison", 10, new MagicAttack(), new ElementEarth()),    
    FREEZE("Freeze", 10, new MagicAttack(), new ElementWater()),
    FIRESHIELD("Shield", 10, new MagicInvoker(""), new ElementFire()),
    HEAL("Heal", 10, new MagicInvoker(""), new MagicElementType());
    

    private String name;
    private int baseStrength;
    private MagicInvoker type;
    private MagicElementType element;

    private Spell(String name, int baseStrength, MagicInvoker type, MagicElementType element) {
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

    public MagicInvoker getType() {
        return type;
    }

    public MagicElementType getElement() {
        return element;
    }
}
