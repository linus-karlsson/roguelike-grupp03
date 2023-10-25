package com.roguelike.enemies;

import com.roguelike.Entity;
import com.roguelike.equipment.ElementType;

public class Troll extends Entity {

    private static final double TROLL_HEALTH = 50;
    private static final double TROLL_DAMAGE = 15;
    private static final ElementType TROLL_ELEMENT = ElementType.EARTH;

    public Troll() {
        super(TROLL_HEALTH, TROLL_DAMAGE, TROLL_ELEMENT);
    }

}
