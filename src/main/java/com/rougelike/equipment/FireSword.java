package com.rougelike.equipment;

public class FireSword extends Weapon {

    private static final String NAME = "Fire Sword";
    private static final int PRICE = 300;
    private static final EquipmentType TYPE = EquipmentType.SWORD;
    private static final int STRENGTH = 8;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 0;
    private static final int DAMAGE = 4;
    private static final int ELEMENTAL_DAMAGE = 10;
    private static final ElementType ELEMENT = ElementType.FIRE;

    public FireSword() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, DAMAGE, ELEMENTAL_DAMAGE, ELEMENT);
    }

}
