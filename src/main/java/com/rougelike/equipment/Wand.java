package com.rougelike.equipment;

public class Wand extends Weapon {

    private static final String WAND_NAME = "Wand";
    private static final int WAND_DAMAGE = 0;
    private static final int WAND_ELEMENTAL_DAMAGE = 10;
    private static final int WAND_PRICE = 80;
    private static final EquipmentType TYPE = EquipmentType.WAND;

    public Wand() {
        super(WAND_NAME, WAND_DAMAGE, WAND_ELEMENTAL_DAMAGE, WAND_PRICE, TYPE);
    }

}
