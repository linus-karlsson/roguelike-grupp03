package com.rougelike.equipment;

public class Shield extends Armor {

    private static final String SHIELD_NAME = "Shield";
    private static final int SHIELD_PRICE = 50;
    private static final EquipmentType TYPE = EquipmentType.SHIELD;

    public Shield() {
        super(SHIELD_NAME, SHIELD_PRICE, TYPE);
    }

}
