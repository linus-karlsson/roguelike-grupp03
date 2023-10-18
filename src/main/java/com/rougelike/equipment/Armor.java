package com.rougelike.equipment;

public class Armor extends Equipment {

    private int health;
    private int mana;
    private int armor;
    private ElementType elementType;

    public Armor(String name, int price, EquipmentType type, int strength, int dexterity, int intelligence, int health,
            int mana, int armor, ElementType elementType) {
        super(name, price, type, strength, dexterity, intelligence);
        this.health = health;
        this.mana = mana;
        this.armor = armor;
        this.elementType = elementType;
    }

    public int getArmor() {
        return armor;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }
}
