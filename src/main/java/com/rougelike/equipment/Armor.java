package com.rougelike.equipment;

public class Armor {
    String name;
    EquipmentType type;

    public Armor(String name, EquipmentType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }
}
