package com.rougelike.magic;

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
}
