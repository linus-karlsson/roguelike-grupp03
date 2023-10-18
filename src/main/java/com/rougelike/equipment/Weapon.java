package com.rougelike.equipment;

public abstract class Weapon extends Equipment {

    int damage;
    int elementalDamage;
    ElementType element;

    public Weapon(String name, int price, EquipmentType type, int strength, int dexterity, int intelligence, int damage,
            int elementalDamage,
            ElementType element) {
        super(name, price, type, strength, dexterity, intelligence);
        this.damage = damage;
        this.elementalDamage = elementalDamage;
        this.element = element;
    }

    public int getDamage() {
        return damage;
    }

    public int getElementalDamage() {
        return elementalDamage;
    }

    public ElementType getWeaponElementType() {
        return element;
    }

}