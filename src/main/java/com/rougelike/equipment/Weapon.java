package com.rougelike.equipment;

public abstract class Weapon {
    String name;
    int damage;
    int elementalDamage;
    int price;
    EquipmentType type;

    public Weapon(String name, int damage, int elementalDamage, int price, EquipmentType type) {
        this.name = name;
        this.damage = damage;
        this.elementalDamage = elementalDamage;
        this.price = price;
        this.type = type;
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

}
// Ska elemental damage vara en grej? Göra bonusskada/reducerad skada mot vissa?