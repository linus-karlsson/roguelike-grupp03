package com.rougelike.equipment;

public class Stick extends Weapon {

    private static final String STICK_NAME = "Stick";
    private static final int STICK_PRICE = 50;
    private static final EquipmentType TYPE = EquipmentType.CLUB;
    private static final int STICK_DAMAGE = 3;
    private static final int STICK_ELEMENTAL_DAMAGE = 0;
    private static final WeaponElementType ELEMENT = WeaponElementType.NONE;

    public Stick() {
        super(STICK_NAME, STICK_PRICE, TYPE, STICK_DAMAGE, STICK_ELEMENTAL_DAMAGE, ELEMENT);
    }
}
