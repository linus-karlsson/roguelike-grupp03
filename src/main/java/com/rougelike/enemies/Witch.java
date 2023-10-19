package com.rougelike.enemies;

import com.rougelike.equipment.ElementType;
import com.rougelike.magic.Magic;
import com.rougelike.magic.Spell;


public class Witch extends Entity {

    private static final double WITCH_HEALTH = 30;
    private static final double WITCH_DAMAGE = 15;
    private static final ElementType WITCH_ELEMENT = ElementType.FIRE;

    private final Magic magic = new Magic(Spell.FIREBALL);


    public Witch(){
        super(WITCH_HEALTH, WITCH_DAMAGE, WITCH_ELEMENT);
    }



}
