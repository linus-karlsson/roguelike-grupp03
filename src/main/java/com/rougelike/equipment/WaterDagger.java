package com.rougelike.equipment;

public class WaterDagger extends Weapon {
    private static final String WATERDAGGER_NAME = "Water Dagger";
    private static final int WATERDAGGER_PRICE = 60;
    private static final EquipmentType TYPE = EquipmentType.DAGGER;
    private static final int WATERDAGGER_DAMAGE = 2;
    private static final int WATERDAGGER_ELEMENTAL_DAMAGE = 6;
    private static final WeaponElementType ELEMENT = WeaponElementType.NONE;

    public WaterDagger() {
        super(WATERDAGGER_NAME, WATERDAGGER_PRICE, TYPE, WATERDAGGER_DAMAGE, WATERDAGGER_ELEMENTAL_DAMAGE, ELEMENT);
    }
}
