package com.rougelike.magic;

import com.rougelike.*;

public abstract class MagicElementType {
    private final String name;
    protected static final double BASE_MULTIPLIER = 1.0;

    public MagicElementType(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    double getMultiplier(Player player) {
        return BASE_MULTIPLIER;
    }
}
