package com.roguelike;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RandomInternalTest {

    @Test
    void testConstructorThrowsOnNullDouble() {
        assertThrows(IllegalArgumentException.class, () -> {
            double[] doubles = null;
            new RandomInternal(doubles);
        });
    }

    @Test
    void testConstructorThrowsOnNullInt() {
        assertThrows(IllegalArgumentException.class, () -> {
            int[] ints = null;
            new RandomInternal(ints);
        });
    }

    @Test
    void testNextDouble() {
        double[] multipliers = { 0.3, 0.5, 0.7, 0.1 };
        RandomInternal randomInternal = new RandomInternal(multipliers);
        for (int i = 0; i < multipliers.length; i++) {
            assertThat(randomInternal.nextDouble(), is(equalTo(multipliers[i])));
        }
    }

    @Test
    void testNextInt() {
        int[] ints = { 1, 2, 3, 4 };
        RandomInternal randomInternal = new RandomInternal(ints);
        int dummyInt = 4;
        for (int i = 0; i < ints.length; i++) {
            assertThat(randomInternal.nextInt(dummyInt), is(equalTo(ints[i])));
        }
    }
}
