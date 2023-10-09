package com.rougelike.equipment;

public abstract class Weapon {
    String name;
    int damage;
    int elementalDamage;
    int price;

    public Weapon(String name, int damage, int elementalDamage, int price) {
        this.name = name;
        this.damage = damage;
        this.elementalDamage = elementalDamage;
        this.price = price;
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

}
// Ska elemental damage vara en grej? Göra bonusskada/reducerad skada mot vissa?