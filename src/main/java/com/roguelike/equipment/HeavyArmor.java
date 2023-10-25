package com.roguelike.equipment;

public class HeavyArmor extends Armor {
    private static final String NAME = "Heavy Armor";
    private static final int PRICE = 60;
    private static final EquipmentType TYPE = EquipmentType.HEAVY_ARMOR;
    private static final int STRENGTH = 5;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 0;
    private static final int HEALTH = 100;
    private static final int MANA = 20;
    private static final int ARMOR = 10;
    private static final ElementType ELEMENT = ElementType.EARTH;

    public HeavyArmor() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, HEALTH, MANA, ARMOR, ELEMENT);
    }
}
