package com.roguelike.equipment;

public class Torch extends Weapon {

    private static final String NAME = "Torch";
    private static final int PRICE = 50;
    private static final EquipmentType TYPE = EquipmentType.CLUB;
    private static final int STRENGTH = 5;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 0;
    private static final int DAMAGE = 2;
    private static final int ELEMENTAL_DAMAGE = 3;
    private static final ElementType ELEMENT = ElementType.FIRE;

    public Torch() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }
}
