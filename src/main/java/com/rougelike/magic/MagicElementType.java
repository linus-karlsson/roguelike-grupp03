package com.rougelike.magic;

import com.rougelike.*;

public class MagicElementType {
    private final String NAME;
    protected static final double BASE_MULTIPLIER = 1.0;

    public MagicElementType(String name) {
        NAME = name;
    }

    public MagicElementType() {
        NAME = "Neutral";
    }

    public String getName() {
        return NAME;
    }

    double getMultiplier(Player player) {
        return BASE_MULTIPLIER;
    }
}
