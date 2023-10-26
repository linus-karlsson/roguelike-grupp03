package com.roguelike.enemies;

import com.roguelike.Entity;
import com.roguelike.equipment.ElementType;

public class Bandit extends Entity {

    private static final double BANDIT_HEALTH = 500;
    private static final double BANDIT_DAMAGE = 15;
    private static final ElementType BANDIT_ELEMENT = ElementType.NONE;

    private static final double BANDIT_XP = 30;

    public Bandit() {
        super(BANDIT_HEALTH, BANDIT_DAMAGE, BANDIT_ELEMENT);
    }

    public double getBanditXp() {
        return BANDIT_XP;
    }

}
