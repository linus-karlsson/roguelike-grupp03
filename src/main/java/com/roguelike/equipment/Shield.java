package com.roguelike.equipment;

public class Shield extends Armor {

    private static final String NAME = "Shield";
    private static final int PRICE = 50;
    private static final EquipmentType TYPE = EquipmentType.SHIELD;
    private static final int STRENGTH = 3;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 0;
    private static final int HEALTH = 30;
    private static final int MANA = 10;
    private static final int ARMOR = 4;
    private static final ElementType ELEMENT = ElementType.NONE;

    public Shield() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, HEALTH, MANA, ARMOR, ELEMENT);
    }

}
