package com.roguelike.equipment;

public class Stick extends Weapon {

    private static final String NAME = "Stick";
    private static final int PRICE = 50;
    private static final EquipmentType TYPE = EquipmentType.CLUB;
    private static final int STRENGTH = 2;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 0;
    private static final int DAMAGE = 3;
    private static final int ELEMENTAL_DAMAGE = 0;
    private static final ElementType ELEMENT = ElementType.NONE;

    public Stick() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }
}
