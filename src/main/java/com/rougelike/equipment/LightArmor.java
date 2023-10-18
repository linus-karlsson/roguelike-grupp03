package com.rougelike.equipment;

public class LightArmor extends Armor {
    private static final String NAME = "Light Armor";
    private static final int PRICE = 65;
    private static final EquipmentType TYPE = EquipmentType.LIGHT_ARMOR;
    private static final int STRENGTH = 0;
    private static final int DEXTERITY = 0;
    private static final int INTELLIGENCE = 11;
    private static final int HEALTH = 70;
    private static final int MANA = 80;
    private static final int ARMOR = 3;
    private static final ElementType ELEMENT = ElementType.WATER;

    public LightArmor() {
        super(NAME, PRICE, TYPE, STRENGTH, DEXTERITY, INTELLIGENCE, HEALTH, MANA, ARMOR, ELEMENT);
    }
}
