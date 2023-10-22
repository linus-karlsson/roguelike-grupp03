package com.rougelike.magic;

public class Magic {
    private final String NAME;
    private final double BASE_STRENGTH;
    private final MagicInvoker TYPE;
    private final MagicElementType ELEMENT;

    public Magic(Spell spell) {
        this.NAME = spell.getName();
        this.BASE_STRENGTH = spell.getBaseStrength();
        this.TYPE = spell.getType();
        this.ELEMENT = spell.getElement();
    }

    public String getName() {
        return NAME;
    }

    public double getBaseStrength() {
        return BASE_STRENGTH;
    }

    public MagicInvoker getType() {
        return TYPE;
    }

    public MagicElementType getElement() {
        return ELEMENT;
    }

}
