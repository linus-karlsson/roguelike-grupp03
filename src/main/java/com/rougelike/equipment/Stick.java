package com.rougelike.equipment;

public class Stick extends Weapon {

    private static final String STICK_NAME = "Stick";
    private static final int STICK_DAMAGE = 3;
    private static final int STICK_ELEMENTAL_DAMAGE = 0;
    private static final int STICK_PRICE = 50;

    public Stick() {
        super(STICK_NAME, STICK_DAMAGE, STICK_ELEMENTAL_DAMAGE, STICK_PRICE);
    }
}
