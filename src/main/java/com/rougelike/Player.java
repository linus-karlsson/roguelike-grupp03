package com.rougelike;

import java.util.ArrayList;

import com.rougelike.equipment.EquipmentType;
import com.rougelike.equipment.Weapon;
import com.rougelike.equipment.Armor;
import com.rougelike.equipment.Equipment;
import com.rougelike.races.Race;
import com.rougelike.roles.Knight;
import com.rougelike.roles.Role;
import com.rougelike.roles.Thief;

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

    private double totalWeaponDamage;
    private int level;
    private double xp;
    private double xpToNextLevel;
    private int wallet = STARTING_MONEY;

    private Weapon equippedWeapon;
    private Equipment equippedOffhand;
    private ArrayList<Weapon> weaponInventory = new ArrayList<>();
    private ArrayList<Armor> armorInventory = new ArrayList<>();

    private Point position;
    private Vector velocity;

    public Player(String name, Point position) {
        this.name = name;
        this.health = 100;
        xpToNextLevel = 200.0;
        this.position = new Point(position);
        this.velocity = new Vector();
    }

    public Player(String name, Race race, Role role, Point position) {
        this(name, position);
        this.race = race;
        this.role = role;
        this.health = race.getStartingHealth() * role.getHealthMultiplier();
        this.mana = race.getStartingMana() * role.getManaMultiplier();
        this.strength = race.getStartingStrength() * role.getStrengthMultiplier();
        this.dexterity = race.getStartingDexterity() * role.getDexterityMultiplier();
        this.intelligence = race.getStartingIntelligence() *
                role.getIntelligenceMultiplier();
        this.equippedWeapon = role.getStartingWeapon();
        weaponInventory.add(role.getStartingWeapon());
        setTotalWeaponDamage();
    }

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

    public void setVelocity(Vector newVelocity) {
        velocity.clone(newVelocity);
    }

    public void updatePlayerMovement(Gridd gridd, double deltaTime) {
        Point newPostion = position.plus(velocity.scalarMulti(deltaTime));
        Gridd.Index index = gridd.getGriddIndexBasedOnPosition(newPostion);
        if (gridd.getTile(index) == 1) {
            position = newPostion;
        }
    }

    public void setTotalWeaponDamage() {
        EquipmentType weaponType = equippedWeapon.getType();
        double damageMultiplier = equippedWeapon.getDamage();
        switch (weaponType) {
            case SWORD:
            case CLUB:
                totalWeaponDamage = damageMultiplier * strength;
                break;
            case WAND:
                totalWeaponDamage = damageMultiplier * intelligence;
                break;
            case DAGGER:
                totalWeaponDamage = damageMultiplier * dexterity;
                break;
        }
    }


    public double attackWithWeapon() {
        return totalWeaponDamage;
    }

    public void takeDamage(){

    }

    public String getName() {
        return name;
    }

    // kunna sätta health för testning
    public double setHealth(double health) {
        return this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    public double getStrength() {
        return strength;
    }

    public double getDexterity() {
        return dexterity;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public int getLevel() {
        return level;
    }

    public Equipment getEquippedOffhand() {
        return equippedOffhand;
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

    public ArrayList<Armor> getArmorInventory() {
        return armorInventory;
    }

    public Role getRole() {
        return role;
    }

    // Vill kunna sätta level för testning
    public int setLevel(int level) {
        return this.level = level;
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

    public void equipWeapon(Weapon weapon) {
        if (!getWeaponInventory().contains(weapon)) {
            return;
        }
        if (role instanceof Knight && (weapon.getType() == EquipmentType.SWORD ||
                weapon.getType() == EquipmentType.CLUB)) {
            equippedWeapon = weapon;
            setTotalWeaponDamage();
            // } else if (role instanceof Mage && (weapon.getType() == EquipmentType.WAND))
            // {
            // equippedWeapon = weapon;
        } else if (role instanceof Thief && (weapon.getType() == EquipmentType.DAGGER)) {
            equippedWeapon = weapon;
            setTotalWeaponDamage();
        }

    }

    public void addArmorToInventory(Armor armor) {
        if (armorInventory.size() == MAX_INVENTORY_CAPACITY) {
            wallet += armorInventory.get(0).getPrice();
            armorInventory.remove(0);
        }
        if (armorInventory.contains(armor)) {
            System.err.println("Kan inte ha dubletter av armor");
            return;
        }
        armorInventory.add(armor);
    }

    public void removeArmorFromInventory(Armor armor) {
        wallet += armor.getPrice();
        armorInventory.remove(armor);
    }

    public void equipOffhand(Equipment offhand) {
        if (!getWeaponInventory().contains(offhand) && !getArmorInventory().contains(offhand)) {
            System.err.println("Måste finnas i weapon inventory eller armor inventory för att kunna equippa");
            return;
        }
        if ((role instanceof Knight) && (offhand.getType() == EquipmentType.SHIELD)) {
            equippedOffhand = offhand;
            // } else if ((role instanceof Mage) && (offhand.getType() ==
            // EquipmentType.BOOK)) {
            // equippedOffhand = offhand;
        } else if ((role instanceof Thief) && (offhand.getType() == EquipmentType.DAGGER)) {
            equippedOffhand = offhand;
        } else {
            return;
        }
    }

    public void equipNextWeapon() {

    }

    public void equipPreviousWeapon() {

    }

}
