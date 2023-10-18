package com.rougelike.equipment;

public class Dagger extends Weapon {
    private static final String NAME = "Dagger";
    private static final int PRICE = 60;
    private static final EquipmentType TYPE = EquipmentType.DAGGER;
    private static final int STRENGTH = 0;
    private static final int DEXTERITY = 9;
    private static final int INTELLIGENCE = 0;
    private static final int DAMAGE = 6;
    private static final int ELEMENTAL_DAMAGE = 0;
    private static final ElementType ELEMENT = ElementType.NONE;

    public Dagger() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }
}
