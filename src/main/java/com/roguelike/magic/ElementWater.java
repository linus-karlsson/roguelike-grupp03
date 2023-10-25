package com.roguelike.magic;

import com.roguelike.*;

public class ElementWater extends MagicElementType {

    private static final String ELEMENT_TYPE = "Water";

    public ElementWater() {
        super(ELEMENT_TYPE);
    }

    @Override
    public double getMultiplier(Player player) {
        return BASE_MULTIPLIER;
    }
}
