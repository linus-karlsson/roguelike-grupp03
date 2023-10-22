package com.rougelike.magic;
import com.rougelike.Player;

public abstract class MagicInvoker {

    private static final double MAGE_MAGIC_MULTIPLIER = 1.1;
    private static final double KNIGHT_MAGIC_MULTIPLIER = 0.9;
    private static final double LEVEL_MULTIPLIER = 1.2;
    private static final RaceImpactChecker raceImpactChecker = new RaceImpactChecker();

    protected String name;

    protected MagicInvoker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    private double impactFromRace(double value, Player player, MagicElementType elementType) {
        double valueToReturn = value;
        if (isElementTypeAir(elementType) && raceImpactChecker.isPlayerImpactByAir(player)) {
           valueToReturn = value * elementType.getMultiplier(player);
        }
        return valueToReturn;
    }

    private boolean isElementTypeAir(MagicElementType elementType) {
        return ("Air".equals(elementType.getName()));
    }

    private boolean isRoleNull(Player player) {
        return null == player.getRole();
    }

    private double calculateValueForMage(double actualStrength) {
        return actualStrength * MAGE_MAGIC_MULTIPLIER;
    }

    private double calculateValueForKnight(double actualStrength) {
        return actualStrength * KNIGHT_MAGIC_MULTIPLIER;
    }

    public double magicValue(Magic magic, Player player) {
        double playerLevel = adjustPlayerLevel(player);
        double actualStrength = magic.getBaseStrength() * Math.pow(LEVEL_MULTIPLIER, playerLevel);
        double roundedValue = Math.round(actualStrength * 100.0) / 100.0;
        roundedValue = impactFromRace(roundedValue, player, magic.getElement());
        return impactFromRole(roundedValue, player);

    }

    private int adjustPlayerLevel(Player player) {
        return player.getLevel() == 1 ? 0 : player.getLevel();
    }

    public abstract double throwMagic(Magic magic, Player player);

}
