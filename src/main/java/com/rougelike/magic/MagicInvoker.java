package com.rougelike.magic;

import com.rougelike.Player;

public class MagicInvoker {

    protected String name;

    public MagicInvoker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double MagicValue(Magic magic, Player player) {
        double actualStrenght = magic.getBaseStrength()*Math.pow(1.2, player.getLevel());
        double roundedValue = Math.round(actualStrenght*100.0)/100.0;
        return roundedValue;
    }
}
