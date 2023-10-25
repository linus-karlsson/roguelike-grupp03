package com.roguelike.dungeon;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GridIndexTest {
    private static final int DEFAULT_ROW = 10;
    private static final int DEFAULT_COLUMN = 20;

    @Test
    void testConstructorWithXAndY() {
        GridIndex index = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN);
        assertThat(index.row, is(equalTo(DEFAULT_ROW)));
        assertThat(index.column, is(equalTo(DEFAULT_COLUMN)));
    }

    @Test
    void testConstructorWithOtherGridIndex() {
        GridIndex index = new GridIndex(new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN));
        assertThat(index.row, is(equalTo(DEFAULT_ROW)));
        assertThat(index.column, is(equalTo(DEFAULT_COLUMN)));
    }

    @Test
    void testConstructorWithOtherGridIndexNotTheSame() {
        GridIndex other = new GridIndex();
        GridIndex index = new GridIndex(other);
        assertTrue(other != index);
    }

    @Test
    void testEquals() {
        GridIndex index = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN);
        GridIndex index2 = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN);
        assertTrue(index.equals(index2));
    }

    @Test
    void testNotEquals() {
        GridIndex index = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN);
        GridIndex index2 = new GridIndex(DEFAULT_ROW + 1, DEFAULT_COLUMN + 1);
        assertFalse(index.equals(index2));
    }

    @Test
    void testCompareToEquals() {
        GridIndex index = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN);
        GridIndex index2 = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN);
        int expected = 0;
        assertThat(index.compareTo(index2), is(equalTo(expected)));
    }

    @Test
    void testCompareSameRow() {
        GridIndex index = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN);
        GridIndex index2 = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN + 1);
        int expected = -1;
        assertThat(index.compareTo(index2), is(equalTo(expected)));
    }

    @Test
    void testCompareDifferentRow() {
        GridIndex index = new GridIndex(DEFAULT_ROW + 1, DEFAULT_COLUMN);
        GridIndex index2 = new GridIndex(DEFAULT_ROW, DEFAULT_COLUMN + 1);
        int expected = 1;
        assertThat(index.compareTo(index2), is(equalTo(expected)));
    }

}
