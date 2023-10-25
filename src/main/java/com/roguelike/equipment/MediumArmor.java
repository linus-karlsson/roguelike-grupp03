package com.roguelike.equipment;

public class MediumArmor extends Armor {
    private static final String NAME = "Medium Armor";
    private static final int PRICE = 55;
    private static final EquipmentType TYPE = EquipmentType.MEDIUM_ARMOR;
    private static final int STRENGTH = 0;
    private static final int DEXTERITY = 7;
    private static final int INTELLIGENCE = 0;
    private static final int HEALTH = 80;
    private static final int MANA = 30;
    private static final int ARMOR = 6;
    private static final ElementType ELEMENT = ElementType.NONE;

    public MediumArmor() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, HEALTH, MANA, ARMOR, ELEMENT);
    }
}
