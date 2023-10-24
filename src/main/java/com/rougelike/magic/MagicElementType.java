package com.rougelike.magic;

import com.rougelike.*;

public abstract class MagicElementType {
    private final String name;
    protected static final double BASE_MULTIPLIER = 1.0;

    protected MagicElementType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    double getMultiplier(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null!");
        }
        return BASE_MULTIPLIER;
    }
}
