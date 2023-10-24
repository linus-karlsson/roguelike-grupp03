package com.rougelike.equipment;

public abstract class Equipment {
    private String name;
    private int price;
    private EquipmentType type;
    private int strength;
    private int dexterity;
    private int intelligence;

    public Equipment(String name, int price, EquipmentType type, int strength, int dexterity, int intelligence) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.price = price;
        this.type = type;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public EquipmentType getType() {
        return type;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getStrength() {
        return strength;
    }

}
