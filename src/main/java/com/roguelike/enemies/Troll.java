package com.roguelike.enemies;

import com.roguelike.Entity;
import com.roguelike.equipment.ElementType;

public class Troll extends Entity {

    private static final double TROLL_HEALTH = 700;
    private static final double TROLL_DAMAGE = 15;
    private static final ElementType TROLL_ELEMENT = ElementType.EARTH;
    private static final double TROLL_XP = 50;

    public Troll() {
        super(TROLL_HEALTH, TROLL_DAMAGE, TROLL_ELEMENT);
    }

    public double getTrollXp() {
        return TROLL_XP;
    }

}
