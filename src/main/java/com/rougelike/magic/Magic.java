package com.rougelike.magic;

public class Magic {
    private final String NAME;
    private final double BASE_STRENGHT;
    private final MagicInvoker TYPE;
    private final MagicElementType ELEMENT;

    public Magic(Spell spell) {
        this.NAME = spell.getName();
        this.BASE_STRENGHT = spell.getBaseStrength();
        this.TYPE = spell.getType();
        this.ELEMENT = spell.getElement();
    }

    public String getName() {
        return NAME;
    }

    public double getBaseStrength() {
        return BASE_STRENGHT;
    }

    public MagicInvoker getType() {
        return TYPE;
    }

    public MagicElementType getElement() {
        return ELEMENT;
    }

    @Override
    public String toString() {
        return "Name: " + NAME + "\nBase Strength: " + BASE_STRENGHT + "\nType: " + TYPE + "\nElement: " + ELEMENT;
    }

}
