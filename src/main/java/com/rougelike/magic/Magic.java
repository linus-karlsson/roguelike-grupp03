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

    public final String getName() {
        return NAME;
    }

    public final double getBaseStrength() {
        return BASE_STRENGTH;
    }

    public final MagicInvoker getType() {
        return TYPE;
    }

    public final MagicElementType getElement() {
        return ELEMENT;
    }

}
