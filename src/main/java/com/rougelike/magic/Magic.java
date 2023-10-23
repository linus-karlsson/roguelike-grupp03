package com.rougelike.magic;

public class Magic {
    private final String name;
    private final double baseStrength;
    private final MagicInvoker type;
    private final MagicElementType element;

    public Magic(Spell spell) {
        this.name = spell.getName();
        this.baseStrength = spell.getBaseStrength();
        this.type = spell.getType();
        this.element = spell.getElement();
    }

    public final String getName() {
        return name;
    }

    public final double getBaseStrength() {
        return baseStrength;
    }

    public final MagicInvoker getType() {
        return type;
    }

    public final MagicElementType getElement() {
        return element;
    }

}
