package com.roguelike;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Point2DTest {

    private Point2D point = new Point2D();
    private static final double EXPECTED_X = 3.0;
    private static final double EXPECTED_Y = 5.0;

    @Test
    void testConstructorCopyOtherPoint() {
        Point2D pointToBeCopied = new Point2D();
        Point2D point = new Point2D(pointToBeCopied);
        assertTrue(point != pointToBeCopied);
    }

    @Test
    void testSetXAndY() {
        point.setX(EXPECTED_X);
        point.setY(EXPECTED_Y);
        assertThat(point.getX(), is(equalTo(EXPECTED_X)));
        assertThat(point.getY(), is(equalTo(EXPECTED_Y)));
    }

    @Test
    void testClone() {
        point.clone(new Point2D(EXPECTED_X, EXPECTED_Y));
        assertThat(point.getX(), is(equalTo(EXPECTED_X)));
        assertThat(point.getY(), is(equalTo(EXPECTED_Y)));
    }

    @Test
    void testCloneNotSameInstance() {
        Point2D otherVector = new Point2D(EXPECTED_X, EXPECTED_Y);
        point.clone(otherVector);
        assertTrue(point != otherVector);
    }

    @Test
    void testPlus() {
        Vector2D vector = new Vector2D(EXPECTED_X, EXPECTED_Y);
        point = point.add(vector);
        assertThat(point.getX(), is(equalTo(EXPECTED_X)));
        assertThat(point.getY(), is(equalTo(EXPECTED_Y)));
    }

    @Test
    void testEquals() {
        Point2D pointToCompare = new Point2D(EXPECTED_X, EXPECTED_Y);
        point.setX(EXPECTED_X);
        point.setY(EXPECTED_Y);
        assertTrue(point.equals(pointToCompare));
    }

    @Test
    void testNotEqualsX() {
        Point2D pointToCompare = new Point2D(EXPECTED_X, 0.0);
        assertFalse(point.equals(pointToCompare));
    }

    @Test
    void testNotEqualsY() {
        Point2D pointToCompare = new Point2D(0.0, EXPECTED_Y);
        assertFalse(point.equals(pointToCompare));
    }

    @Test
    void testNotEqualsInstanceOf() {
        int test = 3;
        assertFalse(point.equals(test));
    }
}
