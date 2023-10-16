package com.rougelike;

import java.util.ArrayList;
import com.rougelike.equipment.Weapon;

public class Player {
    private static final int MAX_LEVEL = 60;
    private static final double LEVEL_MULTIPLIER = 3.5;
    private static final int MAX_INVENTORY_CAPACITY = 5;
    private static final int STARTING_MONEY = 100;

    private String name;
    // private Race race;
    private double health;

    private int level;
    private double xp;
    private double xpToNextLevel;
    private int wallet = STARTING_MONEY;

    private ArrayList<Weapon> weaponInventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.health = 100;
        xpToNextLevel = 200.0;
    }

    // ??????
    private void nextLevel() {
        xpToNextLevel *= LEVEL_MULTIPLIER;
    }

    public void increaseXp(double gainedXp) {
        if (level == MAX_LEVEL) {
            return;
        }
        double xpOverspill = (xp + gainedXp) - xpToNextLevel;
        if (xpOverspill >= 0) {
            level += 1;
            nextLevel();
        }
        xp += gainedXp;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public double getLevelMultiplier() {
        return LEVEL_MULTIPLIER;
    }

    public double getXp() {
        return xp;
    }

    public double getXpToNextLevel() {
        return xpToNextLevel;
    }

    public int getWallet() {
        return wallet;
    }

    public ArrayList<Weapon> getWeaponInventory() {
        return weaponInventory;
    }

    public int getMaxInventoryCapacity() {
        return MAX_INVENTORY_CAPACITY;
    }

    public void addWeaponToInventory(Weapon weapon) {
        if (weaponInventory.size() == MAX_INVENTORY_CAPACITY) {
            wallet += weaponInventory.get(0).getPrice();
            weaponInventory.remove(0);
        }
        if (weaponInventory.contains(weapon)) {
            System.err.println("Kan inte ha dubletter av vapen");
            return;
        }
        weaponInventory.add(weapon);
    }

    public void removeWeaponFromInventory(Weapon weapon) {
        wallet += weapon.getPrice();
        weaponInventory.remove(weapon);
    }

}
