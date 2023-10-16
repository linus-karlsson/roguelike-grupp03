package com.rougelike.magic;

import com.rougelike.Player;

public class Heal extends MagicInvoker{

    private final double MAX_HEALTH = 100;

    public Heal() {
        super("Heal");
    }
  
    public String getName() {
        return super.name;
    }

    @Override
    public void throwMagic(Magic magic, Player player) {
        double healthBeforeControl = player.getHealth() + MagicValue(magic, player);
        player.setHealth(healthBeforeControl > MAX_HEALTH ? MAX_HEALTH : healthBeforeControl);
    }
    
}
