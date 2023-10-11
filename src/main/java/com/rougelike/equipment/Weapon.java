package com.rougelike.equipment;

public abstract class Weapon {
    String name;
    int damage;
    int elementalDamage;
    int price;
    EquipmentType type;
    WeaponElementType element;

    public Weapon(String name, int damage, int elementalDamage, int price, EquipmentType type,
            WeaponElementType element) {
        this.name = name;
        this.damage = damage;
        this.elementalDamage = elementalDamage;
        this.price = price;
        this.type = type;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getElementalDamage() {
        return elementalDamage;
    }

    public int getPrice() {
        return price;
    }

    public EquipmentType getType() {
        return type;
    }

    public WeaponElementType getWeaponElementType() {
        return element;
    }

}
// Ska elemental damage vara en grej? Göra bonusskada/reducerad skada mot vissa?