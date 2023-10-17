package com.rougelike.roles;

import com.rougelike.equipment.EquipmentType;
import com.rougelike.equipment.Sword;
import com.rougelike.equipment.Wand;
import com.rougelike.equipment.Weapon;

public class Mage extends Role {
    private static final double MAGE_HEALTH_MULTIPLIER = 1.0;
    private static final double MAGE_MANA_MULTIPLIER = 1.5;
    private static final double MAGE_STRENGTH_MULTIPLIER = 0.8;
    private static final double MAGE_DEXTERITY_MULTIPLIER = 1.0;
    private static final double MAGE_INTELLIGENCE_MULTIPLIER = 2.0;

    private static final Weapon MAGE_STARTING_WEAPON = new Wand();

    public Mage(){
        super(MAGE_HEALTH_MULTIPLIER, MAGE_MANA_MULTIPLIER, MAGE_STRENGTH_MULTIPLIER, MAGE_DEXTERITY_MULTIPLIER, MAGE_INTELLIGENCE_MULTIPLIER, MAGE_STARTING_WEAPON, EquipmentType.LIGHT_ARMOR, EquipmentType.WAND);

    }

    @Override
    public int roleSpecialAttack() {
        return 30;
    }
}
