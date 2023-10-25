package com.roguelike.equipment;

public class Tome extends Armor {

    private static final String NAME = "Tome";
    private static final int PRICE = 30;
    private static final EquipmentType TYPE = EquipmentType.BOOK;
    private static final int STRENGTH = 0;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 8;
    private static final int HEALTH = 10;
    private static final int MANA = 40;
    private static final int ARMOR = 0;
    private static final ElementType ELEMENT = ElementType.NONE;

    public Tome() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, HEALTH, MANA, ARMOR, ELEMENT);
    }

}
