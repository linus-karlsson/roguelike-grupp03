package com.roguelike.enemies;

import com.roguelike.Entity;
import com.roguelike.equipment.ElementType;
import com.roguelike.magic.Magic;
import com.roguelike.magic.Spell;

public class Witch extends Entity {

    private static final double WITCH_HEALTH = 30;
    private static final double WITCH_DAMAGE = 15;
    private static final ElementType WITCH_ELEMENT = ElementType.FIRE;

    public Witch() {
        super(WITCH_HEALTH, WITCH_DAMAGE, WITCH_ELEMENT);
    }

}
