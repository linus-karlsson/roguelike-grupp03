package com.rougelike.magic;

import com.rougelike.Player;
import com.rougelike.roles.*;
import com.rougelike.races.*;

abstract public class MagicInvoker {

    private static final double MAGE_MAGIC_MULTIPLIER = 1.1;
    private static final double KNIGHT_MAGIC_MULTIPLIER = 0.9;

    private static final double LEVEL_MULTIPLIER = 1.2;

    protected String name;

    public MagicInvoker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private double checkImpactFromRole(double value, Player player) {
        if (isRoleNull(player)) {
            return value;
        }
        switch (player.getRole().getClass().getSimpleName()) {
            case "Knight":
                return calculateValueForKnight(value);
            case "Mage":
                return calculateValueForMage(value);
            default:
                return value;
        }
    }

    private double checkImpactFromRace(double value, Player player, MagicElementType elementType) {
        if (elementType.getName().equals("Air") && player.getRace() instanceof Elf) {
            return value * 1.05;
        }
        return value;
        }
    

    private Boolean isRoleNull(Player player) {
        return player.getRole() == null;
    }

    private double calculateValueForMage(double actualStrenght) {
        return actualStrenght * MAGE_MAGIC_MULTIPLIER;
    }

    private double calculateValueForKnight(double actualStrenght) {
        return actualStrenght * KNIGHT_MAGIC_MULTIPLIER;
    }

    public double MagicValue(Magic magic, Player player) {
        double actualStrenght = magic.getBaseStrength() * Math.pow(LEVEL_MULTIPLIER, player.getLevel());
        double roundedValue = Math.round(actualStrenght * 100.0) / 100.0;
        roundedValue = checkImpactFromRace(roundedValue, player, magic.getElement());
        return checkImpactFromRole(roundedValue, player);

    }

    abstract public void throwMagic(Magic magic, Player player);

}
