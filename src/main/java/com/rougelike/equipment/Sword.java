package com.rougelike.equipment;

public class Sword extends Weapon {
    private static final String SWORD_NAME = "Sword";
    private static final int SWORD_PRICE = 100;
    private static final EquipmentType TYPE = EquipmentType.SWORD;
    private static final int SWORD_DAMAGE = 8;
    private static final int SWORD_ELEMENTAL_DAMAGE = 0;
    private static final WeaponElementType ELEMENT = WeaponElementType.NONE;

    public Sword() {
        super(SWORD_NAME, SWORD_PRICE, TYPE, SWORD_DAMAGE, SWORD_ELEMENTAL_DAMAGE, ELEMENT);
    }

}
