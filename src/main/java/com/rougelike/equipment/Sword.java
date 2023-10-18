package com.rougelike.equipment;

public class Sword extends Weapon {
    private static final String NAME = "Sword";
    private static final int PRICE = 100;
    private static final EquipmentType TYPE = EquipmentType.SWORD;
    private static final int STRENGTH = 11;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 0;
    private static final int DAMAGE = 7;
    private static final int ELEMENTAL_DAMAGE = 0;
    private static final ElementType ELEMENT = ElementType.NONE;

    public Sword() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }

}
