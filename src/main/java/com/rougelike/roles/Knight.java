package com.rougelike.roles;

import com.rougelike.Entity;
import com.rougelike.equipment.EquipmentType;
import com.rougelike.equipment.Sword;
import com.rougelike.equipment.Weapon;

public class Knight extends Role {

    private static final double KNIGHT_HEALTH_MULTIPLIER = 1.3;
    private static final double KNIGHT_MANA_MULTIPLIER = 1.0;
    private static final double KNIGHT_STRENGTH_MULTIPLIER = 1.5;
    private static final double KNIGHT_DEXTERITY_MULTIPLIER = 1.0;
    private static final double KNIGHT_INTELLIGENCE_MULTIPLIER = 1.0;

    private static final Weapon KNIGHT_STARTING_WEAPON = new Sword();

    public Knight() {
        super(KNIGHT_HEALTH_MULTIPLIER, KNIGHT_MANA_MULTIPLIER, KNIGHT_STRENGTH_MULTIPLIER, KNIGHT_DEXTERITY_MULTIPLIER,
                KNIGHT_INTELLIGENCE_MULTIPLIER, KNIGHT_STARTING_WEAPON, EquipmentType.SWORD, EquipmentType.CLUB,
                EquipmentType.SHIELD, EquipmentType.HEAVY_ARMOR, EquipmentType.LIGHT_ARMOR);

    }

    public void shieldBash(Entity enemy) {
        enemy.setStunned();
    }
}
