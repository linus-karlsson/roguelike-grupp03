package com.roguelike.enemies;

import com.roguelike.Entity;
import com.roguelike.equipment.ElementType;

public class Bandit extends Entity {

    private static final double BANDIT_HEALTH = 50;
    private static final double BANDIT_DAMAGE = 15;
    private static final ElementType BANDIT_ELEMENT = ElementType.NONE;

    public Bandit() {
        super(BANDIT_HEALTH, BANDIT_DAMAGE, BANDIT_ELEMENT);
    }

}
