package com.rougelike.roles;


import com.rougelike.equipment.EquipmentType;

public class Knight extends Role {

    private static final double KNIGHT_HEALTH_MULTIPLIER = 1.3;
    private static final double KNIGHT_MANA_MULTIPLIER = 1.0;
    private static final double KNIGHT_STRENGTH_MULTIPLIER = 1.5;
    private static final double KNIGHT_DEXTERITY_MULTIPLIER = 1.0;
    private static final double KNIGHT_INTELLIGENCE_MULTIPLIER = 1.0;

    public Knight(){
       super(KNIGHT_HEALTH_MULTIPLIER, KNIGHT_MANA_MULTIPLIER, KNIGHT_STRENGTH_MULTIPLIER, KNIGHT_DEXTERITY_MULTIPLIER, KNIGHT_INTELLIGENCE_MULTIPLIER, EquipmentType.SWORD, EquipmentType.SHIELD, EquipmentType.HEAVY_ARMOR, EquipmentType.LIGHT_ARMOR);

    }


}
