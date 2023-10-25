package com.roguelike.equipment;

public class WaterDagger extends Weapon {
    private static final String NAME = "Water Dagger";
    private static final int PRICE = 60;
    private static final EquipmentType TYPE = EquipmentType.DAGGER;
    private static final int STRENGTH = 0;
    private static final int DEXTERITY = 13;
    private static final int INTELLIGENCE = 0;
    private static final int DAMAGE = 2;
    private static final int ELEMENTAL_DAMAGE = 6;
    private static final ElementType ELEMENT = ElementType.WATER;

    public WaterDagger() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }
}
