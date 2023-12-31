package com.roguelike.magic;

import com.roguelike.Player;

public abstract class MagicInvoker {

    private static final double MAGE_MAGIC_MULTIPLIER = 1.1;
    private static final double KNIGHT_MAGIC_MULTIPLIER = 0.9;
    private static final double LEVEL_MULTIPLIER = 1.2;

    protected final String name;

    MagicInvoker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double magicValue(Magic magic, Player player) {
        double playerMultipel = countMultipel(player);
        double actualStrength = magic.getBaseStrength() * playerMultipel;
        double roundedValue = Math.round(actualStrength * 100.0) / 100.0;
        roundedValue = impactFromRace(roundedValue, player, magic.getElement());
        return impactFromRole(roundedValue, player);
    }

    private double countMultipel(Player player) {
        int playerLevel = player.getLevel();
        double playerMultipel = 1.0;
        for (int i = 1; i < playerLevel; i++) {
            playerMultipel *= LEVEL_MULTIPLIER;
        }
        return playerMultipel;
    }

    private double impactFromRace(double value, Player player, MagicElementType elementType) {
        double valueToReturn = value;
        if (isElementTypeAir(elementType) && RaceImpactChecker.isPlayerImpact(player.getRace(), elementType)) {
            valueToReturn = value * elementType.getMultiplier(player);
        }
        return valueToReturn;
    }

    private double impactFromRole(double value, Player player) {
        if (isRoleNull(player)) {
            return value;
        }
        final String roleOne = "Knight";
        final String roleTwo = "Mage";
        String role = player.getRole().getClass().getSimpleName();
        switch (role) {
            case roleOne:
                return calculateValueForKnight(value);
            case roleTwo:
                return calculateValueForMage(value);
            default:
                return value;
        }
    }

    private static boolean isElementTypeAir(MagicElementType elementType) {
        return ("Air".equals(elementType.getName()));
    }

    private static boolean isRoleNull(Player player) {
        return null == player.getRole();
    }

    private static double calculateValueForMage(double actualStrength) {
        return actualStrength * MAGE_MAGIC_MULTIPLIER;
    }

    private static double calculateValueForKnight(double actualStrength) {
        return actualStrength * KNIGHT_MAGIC_MULTIPLIER;
    }

    public abstract double throwMagic(Magic magic, Player player);

}
