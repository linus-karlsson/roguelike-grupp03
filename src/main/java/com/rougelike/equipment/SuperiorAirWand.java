package com.rougelike.equipment;

public class SuperiorAirWand extends Weapon {

    private static final String NAME = "Superior Air Wand";
    private static final int PRICE = 120;
    private static final EquipmentType TYPE = EquipmentType.WAND;
    private static final int STRENGTH = 0;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 25;
    private static final int DAMAGE = 0;
    private static final int ELEMENTAL_DAMAGE = 20;
    private static final ElementType ELEMENT = ElementType.AIR;

    public SuperiorAirWand() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }
}
