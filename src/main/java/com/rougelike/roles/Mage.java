package com.rougelike.roles;

import com.rougelike.equipment.EquipmentType;

public class Mage extends Role {
    private static final double MAGE_HEALTH_MULTIPLIER = 1.0;
    private static final double MAGE_MANA_MULTIPLIER = 1.5;
    private static final double MAGE_STRENGTH_MULTIPLIER = 0.8;
    private static final double MAGE_DEXTERITY_MULTIPLIER = 1.0;
    private static final double MAGE_INTELLIGENCE_MULTIPLIER = 2.0;

    public Mage(){
        super(MAGE_HEALTH_MULTIPLIER, MAGE_MANA_MULTIPLIER, MAGE_STRENGTH_MULTIPLIER, MAGE_DEXTERITY_MULTIPLIER, MAGE_INTELLIGENCE_MULTIPLIER, EquipmentType.LIGHT_ARMOR, EquipmentType.WAND);

    }

    @Override
    public int roleSpecialAttack() {
        return 30;
    }
}
