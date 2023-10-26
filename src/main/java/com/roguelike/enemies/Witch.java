package com.roguelike.enemies;

import com.roguelike.Entity;
import com.roguelike.equipment.ElementType;

public class Witch extends Entity {

    private static final double WITCH_HEALTH = 400;
    private static final double WITCH_DAMAGE = 15;
    private static final ElementType WITCH_ELEMENT = ElementType.FIRE;

    private static final double WITCH_XP = 80;

    public Witch() {
        super(WITCH_HEALTH, WITCH_DAMAGE, WITCH_ELEMENT);
    }

    public double getWitchXp() {
        return WITCH_XP;
    }
}
