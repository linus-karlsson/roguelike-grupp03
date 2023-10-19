package com.rougelike.roles;

import com.rougelike.equipment.Dagger;
import com.rougelike.equipment.EquipmentType;
import com.rougelike.equipment.Sword;
import com.rougelike.equipment.Weapon;

public class Thief extends Role {

    private static final double THIEF_HEALTH_MULTIPLIER = 1.0;
    private static final double THIEF_MANA_MULTIPLIER = 1.0;
    private static final double THIEF_STRENGTH_MULTIPLIER = 1.0;
    private static final double THIEF_DEXTERITY_MULTIPLIER = 1.8;
    private static final double THIEF_INTELLIGENCE_MULTIPLIER = 1.0;

    private static final Weapon THIEF_STARTING_WEAPON = new Dagger();

    public Thief() {
        super(THIEF_HEALTH_MULTIPLIER, THIEF_MANA_MULTIPLIER, THIEF_STRENGTH_MULTIPLIER, THIEF_DEXTERITY_MULTIPLIER, THIEF_INTELLIGENCE_MULTIPLIER, THIEF_STARTING_WEAPON, EquipmentType.LIGHT_ARMOR, EquipmentType.MEDIUM_ARMOR, EquipmentType.DAGGER);
    }


}
