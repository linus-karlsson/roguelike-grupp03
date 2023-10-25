package com.roguelike.equipment;

public class SuperiorHeavyArmor extends Armor {
    private static final String NAME = "Superior Heavy Armor";
    private static final int PRICE = 90;
    private static final EquipmentType TYPE = EquipmentType.HEAVY_ARMOR;
    private static final int STRENGTH = 8;
    private static final int DEXTERITY = 1;
    private static final int INTELLIGENCE = 2;
    private static final int HEALTH = 150;
    private static final int MANA = 30;
    private static final int ARMOR = 14;
    private static final ElementType ELEMENT = ElementType.EARTH;

    public SuperiorHeavyArmor() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, HEALTH, MANA, ARMOR, ELEMENT);
    }

}
