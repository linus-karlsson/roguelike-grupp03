package com.rougelike.roles;

import com.rougelike.enemies.Entity;
import com.rougelike.equipment.EquipmentType;
import com.rougelike.equipment.Sword;
import com.rougelike.equipment.AirWand;
import com.rougelike.equipment.Weapon;

public class Mage extends Role {
    private static final double MAGE_HEALTH_MULTIPLIER = 1.0;
    private static final double MAGE_MANA_MULTIPLIER = 1.5;
    private static final double MAGE_STRENGTH_MULTIPLIER = 0.8;
    private static final double MAGE_DEXTERITY_MULTIPLIER = 1.0;
    private static final double MAGE_INTELLIGENCE_MULTIPLIER = 2.0;

    private static final Weapon MAGE_STARTING_WEAPON = new AirWand();

    public Mage() {
        super(MAGE_HEALTH_MULTIPLIER, MAGE_MANA_MULTIPLIER, MAGE_STRENGTH_MULTIPLIER, MAGE_DEXTERITY_MULTIPLIER,
                MAGE_INTELLIGENCE_MULTIPLIER, MAGE_STARTING_WEAPON, EquipmentType.LIGHT_ARMOR, EquipmentType.WAND);

    }

    //Tar ner fiendens skada x mycket beroende på spelarens level
    public void debuff(Entity enemy, int playerLevel) {
        if(playerLevel < 20 ){
            enemy.setDamage(enemy.getDamage() * 0.9);
            enemy.setHealth(enemy.getHealth() * 0.9);
            return;
        }
        if(playerLevel < 50){
            enemy.setDamage(enemy.getDamage() * 0.8);
            enemy.setHealth(enemy.getHealth() * 0.8);
            return;
        }
        if(playerLevel >= 50){
            enemy.setDamage(enemy.getDamage() * 0.7);
            enemy.setHealth(enemy.getHealth() * 0.7);
        }
    }

}
