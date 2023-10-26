package com.roguelike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.roguelike.dungeon.Grid;
import com.roguelike.dungeon.GridIndex;
import com.roguelike.equipment.*;
import com.roguelike.magic.*;
import com.roguelike.races.Race;
import com.roguelike.roles.Knight;
import com.roguelike.roles.Mage;
import com.roguelike.roles.Role;
import com.roguelike.roles.Thief;

import java.util.List;

public class Player extends Entity {

    private PlayerStartingValues startingValues;

    private Race race;
    private Role role;
    private static final int MAX_LEVEL = 60;
    private static final double LEVEL_MULTIPLIER = 3.5;
    private static final int MAX_INVENTORY_CAPACITY = 5;
    private static final int STARTING_MONEY = 100;
    private String name;
    private double mana;
    private double strength;
    private double dexterity;
    private double intelligence;
    private double armorValue;

    private double totalWeaponDamage;
    private double xp;
    private double xpToNextLevel;
    private int wallet = STARTING_MONEY;

    private Weapon equippedWeapon;
    private Equipment equippedOffhand;
    private Armor equippedArmor;
    private ArrayList<Weapon> weaponInventory = new ArrayList<>();
    private ArrayList<Armor> armorInventory = new ArrayList<>();
    private Map<String, Magic> magicInventory = new HashMap<>();

    private Point2D position;
    private Vector2D velocity;

    public Player(String name, Point2D position) {
        super(100.0, 0.0, ElementType.AIR);
        this.name = name;
        xpToNextLevel = 200.0;
        this.position = new Point2D(position);
        this.velocity = new Vector2D();
    }

    public Player(String name, Race race, Role role, Point2D position) {
        this(name, position);
        setLevel(1);
        this.race = race;
        this.role = role;
        setHealth(race.getStartingHealth() * role.getHealthMultiplier());
        this.mana = race.getStartingMana() * role.getManaMultiplier();
        this.strength = race.getStartingStrength() * role.getStrengthMultiplier();
        this.dexterity = race.getStartingDexterity() * role.getDexterityMultiplier();
        this.intelligence = race.getStartingIntelligence() *
                role.getIntelligenceMultiplier();
        this.equippedWeapon = role.getStartingWeapon();
        weaponInventory.add(role.getStartingWeapon());
        setTotalWeaponDamage();
        startingValues = new PlayerStartingValues(name, race, role, position);
    }

    private void nextLevel() {
        xpToNextLevel *= LEVEL_MULTIPLIER;
    }

    public void increaseXp(double gainedXp) {
        if (getLevel() == MAX_LEVEL) {
            return;
        }
        double xpOverspill = (xp + gainedXp) - xpToNextLevel;
        if (xpOverspill >= 0) {
            setLevel(getLevel() + 1);
            nextLevel();
        }
        xp += gainedXp;
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public Point2D getPosition() {
        return new Point2D(position);
    }

    public void setVelocity(Vector2D newVelocity) {
        velocity.clone(newVelocity);
    }

    public void updateMovement(Grid gridd, double deltaTime) {
        Point2D newPostion = position.add(velocity.scalarMulti(deltaTime));
        GridIndex index = gridd.getGriddIndexBasedOnPosition(newPostion);
        if (gridd.getTile(index) >= 0) {
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
            default:
                break;
        }
    }

    public double getTotalWeaponDamage() {
        return totalWeaponDamage;
    }

    public void attackEnemyWithWeapon(Entity enemy) {
        if (role instanceof Thief) {
            if (((Thief) role).isInvisible()) {
                return;
            }
        }
        double damage = totalWeaponDamage;
        ElementType weaponElement = equippedWeapon.getWeaponElementType();
        ElementType enemyElement = enemy.getElement();
        if (ElementType.elementIsEffective(weaponElement, enemyElement)) {
            damage += equippedWeapon.getElementalDamage();
        }
        enemy.takeDamage(damage);
    }

    public void takeDamage(double damageTaken) {
        if (role instanceof Thief) {
            if (((Thief) role).isInvisible()) {
                return;
            }
        }
        super.takeDamage(damageTaken);
    }

    public boolean weaponIsEffective(ElementType enemyElement) {
        ElementType weaponElement = equippedWeapon.getWeaponElementType();
        if (weaponElement == ElementType.WATER && enemyElement == ElementType.FIRE) {
            return true;
        }
        if (weaponElement == ElementType.EARTH && enemyElement == ElementType.FIRE) {
            return true;
        }
        if (weaponElement == ElementType.AIR && enemyElement == ElementType.EARTH) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
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

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Equipment getEquippedOffhand() {
        return equippedOffhand;
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

    public List<Weapon> getWeaponInventory() {
        return Collections.unmodifiableList(weaponInventory);
    }

    public int getMaxInventoryCapacity() {
        return MAX_INVENTORY_CAPACITY;
    }

    public List<Armor> getArmorInventory() {
        return Collections.unmodifiableList(armorInventory);
    }

    public Role getRole() {
        return role;
    }

    public double getArmorValue() {
        return armorValue;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public Player reset() {
        String name = startingValues.getName();
        Race race = startingValues.getRace();
        Role role = startingValues.getRole();
        Point2D position = startingValues.getPosition();
        return new Player(name, race, role, position);
    }

    // Equipping/Unequipping
    // MAX_INVENTORY_CAPACITY will always be larger than 2.

    private void setStatsWhenEquippingWeapon(Weapon weapon) {
        setStatsWhenEquipping(weapon);
        setTotalWeaponDamage();
    }

    private void setStatsWhenEquippingArmor(Armor armor) {
        setStatsWhenEquipping(armor);
        setHealth(getHealth() + armor.getHealth());
        mana += armor.getMana();
        armorValue += armor.getArmorValue();
    }

    private void setStatsWhenEquipping(Equipment equipment) {
        strength += equipment.getStrength() * role.getStrengthMultiplier();
        dexterity += equipment.getDexterity() * role.getDexterityMultiplier();
        intelligence += equipment.getIntelligence() * role.getIntelligenceMultiplier();
    }

    // Weapon

    public void addWeaponToInventory(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException();
        }
        if (weaponInventory.contains(weapon)) {
            System.err.println();
            return;
        }
        if (weaponInventory.size() == MAX_INVENTORY_CAPACITY) {
            Weapon weaponToRemove = null;
            for (Weapon w : weaponInventory) {
                if (w != getEquippedWeapon() && w != getEquippedOffhand()) {
                    weaponToRemove = w;
                    break;
                }
            }
            if (weaponToRemove != null) {
                weaponInventory.remove(weaponToRemove);
                wallet += weaponToRemove.getPrice();
            }
        }
        weaponInventory.add(weapon);
    }

    public void equipWeapon(Weapon weapon) {
        if (!getWeaponInventory().contains(weapon)) {
            return;
        }
        if (role instanceof Knight && (weapon.getType() == EquipmentType.SWORD ||
                weapon.getType() == EquipmentType.CLUB)) {
            setStatsWhenEquippingWeapon(weapon);
            equippedWeapon = weapon;
        } else if (role instanceof Mage && (weapon.getType() == EquipmentType.WAND)) {
            setStatsWhenEquippingWeapon(weapon);
            equippedWeapon = weapon;
        } else if (role instanceof Thief && (weapon.getType() == EquipmentType.DAGGER)) {
            setStatsWhenEquippingWeapon(weapon);
            equippedWeapon = weapon;
        } else {
            return;
        }
    }

    public void removeWeaponFromInventory(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException();
        }
        if (weapon == getEquippedWeapon() || weapon == getEquippedOffhand()) {
            return;
        }
        wallet += weapon.getPrice();
        weaponInventory.remove(weapon);
    }

    public void unequipWeapon() {
        setStatsWhenUnequippingWeapon();
        equippedWeapon = null;
        totalWeaponDamage = 0;
    }

    public void setStatsWhenUnequippingWeapon() {
        strength -= equippedWeapon.getStrength() * role.getStrengthMultiplier();
        dexterity -= equippedWeapon.getDexterity() * role.getDexterityMultiplier();
        intelligence -= equippedWeapon.getIntelligence() + role.getIntelligenceMultiplier();
    }

    // Armor

    public void addArmorToInventory(Armor armor) {
        if (armor == null) {
            throw new IllegalArgumentException();
        }
        if (armorInventory.contains(armor)) {
            return;
        }
        if (armorInventory.size() == MAX_INVENTORY_CAPACITY) {
            Armor armorToRemove = null;
            for (Armor a : armorInventory) {
                if (a != getEquippedArmor() && a != getEquippedOffhand()) {
                    armorToRemove = a;
                    break;
                }
            }
            if (armorToRemove != null) {
                armorInventory.remove(armorToRemove);
                wallet += armorToRemove.getPrice();
            }
        }
        armorInventory.add(armor);
    }

    public void removeArmorFromInventory(Armor armor) {
        if (armor == null) {
            throw new IllegalArgumentException();
        }
        if (armor == getEquippedArmor() || armor == getEquippedOffhand()) {
            return;
        }
        wallet += armor.getPrice();
        armorInventory.remove(armor);
    }

    public void equipArmor(Armor armor) {
        if (!getArmorInventory().contains(armor)) {
            return;
        }
        if (role instanceof Knight && (armor.getType() == EquipmentType.HEAVY_ARMOR)) {
            setStatsWhenEquippingArmor(armor);
            equippedArmor = armor;
        } else if (role instanceof Mage && (armor.getType() == EquipmentType.LIGHT_ARMOR)) {
            setStatsWhenEquippingArmor(armor);
            equippedArmor = armor;
        } else if (role instanceof Thief && (armor.getType() == EquipmentType.MEDIUM_ARMOR)) {
            setStatsWhenEquippingArmor(armor);
            equippedArmor = armor;
        } else {
            return;
        }
    }

    public void unequipArmor() {
        setStatswhenUnequippingArmor();
        equippedArmor = null;
    }

    private void setStatswhenUnequippingArmor() {
        strength -= equippedArmor.getStrength() * role.getStrengthMultiplier();
        dexterity -= equippedArmor.getDexterity() * role.getDexterityMultiplier();
        intelligence -= equippedArmor.getIntelligence() * role.getIntelligenceMultiplier();
        setHealth(getHealth() - equippedArmor.getHealth());
        mana -= equippedArmor.getMana();
        armorValue -= equippedArmor.getArmorValue();
    }

    public void equipOffhand(Equipment offhand) {
        if (!getWeaponInventory().contains(offhand) && !getArmorInventory().contains(offhand)) {
            return;
        }
        if ((role instanceof Knight) && (offhand instanceof Armor) && (offhand.getType() == EquipmentType.SHIELD)) {
            equippedOffhand = offhand;
            setStatsWhenEquippingArmor((Armor) offhand);
        } else if ((role instanceof Mage) && (offhand instanceof Armor) && (offhand.getType() == EquipmentType.BOOK)) {
            equippedOffhand = offhand;
            setStatsWhenEquippingArmor((Armor) offhand);
        } else if ((role instanceof Thief) && (offhand instanceof Weapon)
                && (offhand.getType() == EquipmentType.DAGGER)) {
            equippedOffhand = offhand;
            setStatsWhenEquippingWeapon((Weapon) offhand);
        } else {
            return;
        }
    }

    // ----------------

    public void addMagicToInventory(Magic magic) {
        if (hasMagicKnowledge(magic.getName())) {
            return;
        }
        magicInventory.put(magic.getName(), magic);
    }

    private boolean hasMagicKnowledge(String magic) {
        return magicInventory.containsKey(magic);
    }

    public Map<String, Magic> getMagicInventory() {
        return Collections.unmodifiableMap(magicInventory);
    }

    public void useMagic(Spell spell) {
        if (spell == null) {
            throw new IllegalArgumentException("Magic must have a name");
        } else if (!hasMagicKnowledge(spell.getName())) {
            throw new IllegalArgumentException("Player does not have knowledge of that magic");
        }
        switch (spell.getType().getName()) {
            case "Attack":
                break;
            case "Defence":
                // to be implemented
                break;
            case "Heal":
                setHealth(spell.getType().throwMagic(magicInventory.get(spell.getName()), this));
                break;
        }
    }

    public void useMagic(Spell spell, Entity enemy) {
        if (spell == null) {
            throw new IllegalArgumentException("Magic must have a name");
        } else if (!hasMagicKnowledge(spell.getName())) {
            throw new IllegalArgumentException("Player does not have knowledge of that magic");
        } else if (enemy == null) {
            throw new IllegalArgumentException("Enemy must not be null");
        } else if (enemy.isDead()) {
            throw new IllegalArgumentException("Enemy must not be dead");
        }
        if (!(spell.getType().getName().equals("Attack"))) {
            throw new IllegalArgumentException("Magic must be of type MagicAttack");
        }
        enemy.takeDamage(spell.getType().throwMagic(magicInventory.get(spell.getName()), this));
    }

    public void debuff(Entity enemy) {
        if (role instanceof Mage) {
            ((Mage) role).debuff(enemy, getLevel());
        }
    }

    public void shieldBash(Entity enemy) {
        if (role instanceof Knight) {
            ((Knight) role).shieldBash(enemy);
        }
    }

    public void becomeInvisible() {
        if (role instanceof Thief) {
            ((Thief) role).becomeInvisible();
        }
    }

    public void reEmerge() {
        if (role instanceof Thief) {
            ((Thief) role).reEmerge();
        }
    }
}
