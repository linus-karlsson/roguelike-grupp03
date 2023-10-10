package com.rougelike.roles;

public class Thief extends Role {

    private static final double THIEF_HEALTH_MULTIPLIER = 1.0;
    private static final double THIEF_MANA_MULTIPLIER = 1.0;
    private static final double THIEF_STRENGTH_MULTIPLIER = 1.0;
    private static final double THIEF_DEXTERITY_MULTIPLIER = 1.8;
    private static final double THIEF_INTELLIGENCE_MULTIPLIER = 1.0;

    public Thief(){
        super(THIEF_HEALTH_MULTIPLIER, THIEF_MANA_MULTIPLIER,THIEF_STRENGTH_MULTIPLIER,THIEF_DEXTERITY_MULTIPLIER,THIEF_INTELLIGENCE_MULTIPLIER);
    }
}
