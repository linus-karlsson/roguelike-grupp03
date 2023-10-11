package com.rougelike;

import java.util.Random;

public class RandomInternal extends Random {

    private double[] randomMultipliers;
    private int currentIndex;

    public RandomInternal(double... randomMultipliers) {
        if (randomMultipliers == null) {
            throw new IllegalArgumentException("");
        }
        this.randomMultipliers = randomMultipliers;
        currentIndex = 0;
    }

    @Override
    public double nextDouble() {
        double result = randomMultipliers[currentIndex++];
        currentIndex %= randomMultipliers.length;
        return result;
    }

}
