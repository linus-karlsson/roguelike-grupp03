package com.roguelike.magic;

import com.roguelike.*;

public class ElementWater extends MagicElementType {

    private static final String ELEMENT_TYPE = "Water";

    public ElementWater() {
        super(ELEMENT_TYPE);
    }

    @Override
    public double getMultiplier(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player is null");
        }
        return BASE_MULTIPLIER;
    }
}
