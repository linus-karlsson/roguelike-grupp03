package com.rougelike.equipment;

public abstract class Weapon extends Equipment {

    int damage;
    int elementalDamage;
    WeaponElementType element;

    public Weapon(String name, int price, EquipmentType type, int damage, int elementalDamage,
            WeaponElementType element) {
        super(name, price, type);
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

    public WeaponElementType getWeaponElementType() {
        return element;
    }

}
// Ska elemental damage vara en grej? Göra bonusskada/reducerad skada mot vissa?