package com.roguelike.roles;

import com.roguelike.Entity;
import com.roguelike.equipment.AirWand;
import com.roguelike.equipment.EquipmentType;
import com.roguelike.equipment.Weapon;

public class Mage extends Role {
    private static final double MAGE_HEALTH_MULTIPLIER = 1.0;
    private static final double MAGE_MANA_MULTIPLIER = 1.5;
    private static final double MAGE_STRENGTH_MULTIPLIER = 0.8;
    private static final double MAGE_DEXTERITY_MULTIPLIER = 1.0;
    private static final double MAGE_INTELLIGENCE_MULTIPLIER = 2.0;



    public Mage() {
        super(MAGE_HEALTH_MULTIPLIER, MAGE_MANA_MULTIPLIER, MAGE_STRENGTH_MULTIPLIER, MAGE_DEXTERITY_MULTIPLIER,
                MAGE_INTELLIGENCE_MULTIPLIER, new AirWand(), EquipmentType.LIGHT_ARMOR, EquipmentType.WAND);

    }

    // Tar ner fiendens skada x mycket beroende på spelarens level
    public void debuff(Entity enemy, int playerLevel) {
        double debuffMultiplier = playerLevel < 20 ? 0.9 : playerLevel < 50 ? 0.8 : 0.7;
        enemy.setDamage(enemy.getDamage() * debuffMultiplier);
        enemy.setHealth(enemy.getHealth() * debuffMultiplier);
    }

}
