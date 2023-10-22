package com.rougelike.magic;

import com.rougelike.Player;

public class Heal extends MagicInvoker {

    private static final double MAX_HEALTH = 100.0;
    private static final String TYPE_OF_MAGIC = "Heal";

    public Heal() {
        super(TYPE_OF_MAGIC);
    }

    // Kod ska gås igenom
    public double throwMagic(Magic magic, Player player) {
        double healthBeforeControl = player.getHealth() + magicValue(magic, player);
        return (healthBeforeControl > MAX_HEALTH ? MAX_HEALTH : healthBeforeControl);
    }

}
