package com.roguelike;

import java.util.Random;

public class RandomInternal extends Random {

    private double[] randomMultipliers;
    private int currentDoubleIndex;
    private int[] randomInts;
    private int currentIntIndex;

    public RandomInternal(double... randomMultipliers) {
        if (randomMultipliers == null) {
            throw new IllegalArgumentException("");
        }
        this.randomMultipliers = randomMultipliers;
        currentDoubleIndex = 0;
    }

    public RandomInternal(int... randomInts) {
        if (randomInts == null) {
            throw new IllegalArgumentException("");
        }
        this.randomInts = randomInts;
        currentIntIndex = 0;
    }

    @Override
    public double nextDouble() {
        double result = randomMultipliers[currentDoubleIndex++];
        currentDoubleIndex %= randomMultipliers.length;
        return result;
    }

    @Override
    public int nextInt(int bounds) {
        int result = randomInts[currentIntIndex++];
        currentIntIndex %= randomInts.length;
        return result;
    }

}
