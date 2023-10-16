package com.rougelike;

import java.util.ArrayList;

import com.rougelike.equipment.EquipmentType;
import com.rougelike.equipment.Weapon;
import com.rougelike.races.Race;
import com.rougelike.roles.Role;

public class Player {

    private Race race;
    private Role role;
    private static final int MAX_LEVEL = 60;
    private static final double LEVEL_MULTIPLIER = 3.5;
    private static final int MAX_INVENTORY_CAPACITY = 5;
    private static final int STARTING_MONEY = 100;

    private String name;
    // private Race race;
    private double health;
    private double mana;
    private double strength;
    private double dexterity;
    private double intelligence;
    private int level;
    private double xp;
    private double xpToNextLevel;
    private int wallet = STARTING_MONEY;

    private Weapon equippedWeapon;
    private ArrayList<Weapon> weaponInventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.health = 100;
        xpToNextLevel = 200.0;
    }
    public Player(String name, Race race, Role role) {
        this.name = name;
        this.race = race;
        this.role = role;
        this.health = race.getStartingHealth() * role.getHealthMultiplier();
        this.mana = race.getStartingMana() * role.getManaMultiplier();
        this.strength = race.getStartingStrength() * role.getStrengthMultiplier();
        this.dexterity = race.getStartingDexterity() * role.getDexterityMultiplier();
        this.intelligence = race.getStartingIntelligence() * role.getIntelligenceMultiplier();
        this.equippedWeapon = role.getStartingWeapon();
        weaponInventory.add(role.getStartingWeapon());
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

    public double attackWithWeapon(){
        //Vapnens skada påverkas av spelarens strength, dexterity eller intelligence beroende på typ.
        //Detta är fett ineffektivt så borde göras på annat sätt
        EquipmentType weaponType = equippedWeapon.getType();
        double damageMultiplier = equippedWeapon.getDamage();
        double totalDamage = 0;
        switch (weaponType){
            case SWORD:
            case CLUB:
                totalDamage = damageMultiplier * strength;
                break;
            case WAND:
                totalDamage = damageMultiplier * intelligence;
                break;
            case DAGGER:
                totalDamage = damageMultiplier * dexterity;
                break;

        }
        return totalDamage;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public double getMana(){return mana;}

    public double getStrength() {return strength;}

    public double getDexterity() {return dexterity;}

    public double getIntelligence() {return intelligence;}

    public int getLevel() {
        return level;
    }

    //Vill kunna sätta level för testning
    public int setLevel(int level) {
        return this.level = level;
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

    public void equipNextWeapon(){

    }

    public void equipPreviousWeapon(){

    }

}
