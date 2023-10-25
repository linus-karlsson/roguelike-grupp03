package com.rougelike.magic;

import com.rougelike.*;

public class ElementFire extends MagicElementType   {

    private static final String ELEMENT_TYPE = "Fire";

    public ElementFire() {
        super(ELEMENT_TYPE);
    }

    @Override
    public double getMultiplier(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null!");
        }
        return BASE_MULTIPLIER;
    }
    
}
