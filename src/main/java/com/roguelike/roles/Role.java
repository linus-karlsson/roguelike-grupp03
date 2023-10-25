package com.roguelike.roles;

import java.util.*;

import com.roguelike.equipment.EquipmentType;
import com.roguelike.equipment.Weapon;

public abstract class Role {

    private final double healthMultiplier;
    private final double manaMultiplier;
    private final double strengthMultiplier;
    private final double dexterityMultiplier;
    private final double intelligenceMultiplier;

    private final Set<EquipmentType> eligibleEquipment = new HashSet<>();

    private Weapon startingWeapon;

    public Role(double healthMultiplier, double manaMultiplier, double strengthMultiplier, double dexterityMultiplier,
            double intelligenceMultiplier, Weapon startingWeapon, EquipmentType... eligibleEquipment) {
        this.healthMultiplier = healthMultiplier;
        this.manaMultiplier = manaMultiplier;
        this.strengthMultiplier = strengthMultiplier;
        this.dexterityMultiplier = dexterityMultiplier;
        this.intelligenceMultiplier = intelligenceMultiplier;
        this.startingWeapon = startingWeapon;
        this.eligibleEquipment.addAll(Arrays.asList(eligibleEquipment));
    }

    public double getHealthMultiplier() {
        return healthMultiplier;
    }

    public double getManaMultiplier() {
        return manaMultiplier;
    }

    public double getStrengthMultiplier() {
        return strengthMultiplier;
    }

    public double getDexterityMultiplier() {
        return dexterityMultiplier;
    }

    public double getIntelligenceMultiplier() {
        return intelligenceMultiplier;
    }

    public Weapon getStartingWeapon() {
        return startingWeapon;
    }

    public Set<EquipmentType> getEligibleEquipment() {
        return Collections.unmodifiableSet(eligibleEquipment);
    }

}
