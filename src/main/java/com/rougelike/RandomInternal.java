package com.rougelike;

import java.util.Random;

public class RandomInternal extends Random {

    private final double randomMultiplier;

    public RandomInternal(double randomMultiplier) {
        this.randomMultiplier = randomMultiplier;
    }

    @Override
    public double nextDouble() {
        return randomMultiplier;
    }

}
