package com.rougelike.equipment;

public class HeavyArmor extends Armor {
    private static final String HEAVY_ARMOR_NAME = "Heavy Armor";
    private static final EquipmentType TYPE = EquipmentType.HEAVY_ARMOR;

    public HeavyArmor() {
        super(HEAVY_ARMOR_NAME, TYPE);
    }
}
