package com.rougelike.equipment;

public abstract class Equipment {
    private String name;
    private int price;
    private EquipmentType type;

    public Equipment(String name, int price, EquipmentType type) {
        this.name = name;
        this.price = price;
        this.type = type;
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

}
