package com.rougelike.roles;

import com.rougelike.equipment.EquipmentType;

import java.util.*;


public abstract class Role {

    private double healthMultiplier;
    private double manaMultiplier;
    private double strengthMultiplier;
    private double dexterityMultiplier;
    private double intelligenceMultiplier;

    private final Set<EquipmentType> eligibleEquipment = new HashSet();


    public Role(double healthMultiplier, double manaMultiplier, double strengthMultiplier, double dexterityMultiplier, double intelligenceMultiplier, EquipmentType... eligibleEquipment) {
        this.healthMultiplier = healthMultiplier;
        this.manaMultiplier = manaMultiplier;
        this.strengthMultiplier = strengthMultiplier;
        this.dexterityMultiplier = dexterityMultiplier;
        this.intelligenceMultiplier = intelligenceMultiplier;
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

    public Set getEligibleEquipment(){
        return eligibleEquipment;
    }



}
