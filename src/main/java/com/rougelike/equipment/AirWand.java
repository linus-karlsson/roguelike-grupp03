package com.rougelike.equipment;

public class AirWand extends Weapon {

    private static final String NAME = "Air Wand";
    private static final int PRICE = 80;
    private static final EquipmentType TYPE = EquipmentType.WAND;
    private static final int STRENGTH = 0;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 15;
    private static final int DAMAGE = 0;
    private static final int ELEMENTAL_DAMAGE = 10;
    private static final ElementType ELEMENT = ElementType.AIR;

    public AirWand() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }

}
