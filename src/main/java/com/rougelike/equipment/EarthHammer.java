package com.rougelike.equipment;

public class EarthHammer extends Weapon {

    private static final String NAME = "Earth Hammer";
    private static final int PRICE = 90;
    private static final EquipmentType TYPE = EquipmentType.CLUB;
    private static final int STRENGTH = 7;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 0;
    private static final int DAMAGE = 4;
    private static final int ELEMENTAL_DAMAGE = 9;
    private static final ElementType ELEMENT = ElementType.EARTH;

    public EarthHammer() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }

}
