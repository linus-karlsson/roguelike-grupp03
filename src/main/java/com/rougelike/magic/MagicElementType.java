package com.rougelike.magic;

import com.rougelike.*;

public class MagicElementType {
    private final String NAME;

    public MagicElementType(String name) {
        this.NAME = name;
    }

    public MagicElementType() {
        this.NAME = "Neutral";
    }

    public String getName() {
        return NAME;
    }

    double getMultiplier(Player player) {
        return 1.0;
    }
}
