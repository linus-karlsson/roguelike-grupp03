package com.roguelike.roles;

import com.roguelike.equipment.Dagger;
import com.roguelike.equipment.EquipmentType;
import com.roguelike.equipment.Weapon;

public class Thief extends Role {

    private static final double THIEF_HEALTH_MULTIPLIER = 1.0;
    private static final double THIEF_MANA_MULTIPLIER = 1.0;
    private static final double THIEF_STRENGTH_MULTIPLIER = 1.0;
    private static final double THIEF_DEXTERITY_MULTIPLIER = 1.8;
    private static final double THIEF_INTELLIGENCE_MULTIPLIER = 1.0;
    private boolean invisible = false;

    public Thief() {
        super(THIEF_HEALTH_MULTIPLIER, THIEF_MANA_MULTIPLIER, THIEF_STRENGTH_MULTIPLIER, THIEF_DEXTERITY_MULTIPLIER,
                THIEF_INTELLIGENCE_MULTIPLIER, new Dagger(), EquipmentType.LIGHT_ARMOR,
                EquipmentType.MEDIUM_ARMOR, EquipmentType.DAGGER);
    }

    public void becomeInvisible() {
        invisible = true;
    }

    public void reEmerge() {
        invisible = false;
    }

    public boolean isInvisible() {
        return invisible;
    }

}
