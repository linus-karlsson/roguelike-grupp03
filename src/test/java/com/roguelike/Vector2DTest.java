package com.roguelike;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Vector2DTest {

    private Vector2D vector = new Vector2D();
    private static final double EXPECTED_X = 3.0;
    private static final double EXPECTED_Y = 5.0;

    @Test
    void testConstructorWithNoArguments() {
        vector = new Vector2D();
        double expected = 0;
        assertThat(vector.getX(), is(equalTo(expected)));
        assertThat(vector.getY(), is(equalTo(expected)));
    }

    @Test
    void testConstructorSetValues() {
        vector = new Vector2D(EXPECTED_X, EXPECTED_Y);
        assertThat(vector.getX(), is(equalTo(EXPECTED_X)));
        assertThat(vector.getY(), is(equalTo(EXPECTED_Y)));
    }

    @Test
    void testSetXAndY() {
        vector.setX(EXPECTED_X);
        vector.setY(EXPECTED_Y);
        assertThat(vector.getX(), is(equalTo(EXPECTED_X)));
        assertThat(vector.getY(), is(equalTo(EXPECTED_Y)));
    }

    @Test
    void testClone() {
        vector.clone(new Vector2D(EXPECTED_X, EXPECTED_Y));
        assertThat(vector.getX(), is(equalTo(EXPECTED_X)));
        assertThat(vector.getY(), is(equalTo(EXPECTED_Y)));
    }

    @Test
    void testCloneNotSameInstance() {
        Vector2D otherVector = new Vector2D(EXPECTED_X, EXPECTED_Y);
        vector.clone(otherVector);
        assertTrue(vector != otherVector);
    }

    @Test
    void testScalarMulti() {
        double multiplier = 3.4;
        Vector2D otherVector = vector.scalarMulti(multiplier);
        assertThat(otherVector.getX(), is(equalTo(vector.getX() * multiplier)));
        assertThat(otherVector.getY(), is(equalTo(vector.getY() * multiplier)));
    }

}
