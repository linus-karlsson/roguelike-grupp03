package com.rougelike.equipment;

public class Dagger extends Weapon {
    private static final String DAGGER_NAME = "Dagger";
    private static final int DAGGER_DAMAGE = 6;
    private static final int DAGGER_ELEMENTAL_DAMAGE = 0;
    private static final int DAGGER_PRICE = 60;
    private static final EquipmentType TYPE = EquipmentType.DAGGER;
    private static final WeaponElementType ELEMENT = WeaponElementType.NONE;

    public Dagger() {
        super(DAGGER_NAME, DAGGER_DAMAGE, DAGGER_ELEMENTAL_DAMAGE, DAGGER_PRICE, TYPE, ELEMENT);
    }
}
