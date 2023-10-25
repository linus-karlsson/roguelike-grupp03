package com.roguelike.dungeon;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.roguelike.Point2D;

public class RoomTest {
    private static final double DEFAULT_WIDTH = 20.0;
    private static final double DEFAULT_HEIGHT = 30.0;

    @Test
    void testConstructorSettingCorrectWidthAndHeight() {
        Room room = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        assertThat(room.getWidth(), is(equalTo(DEFAULT_WIDTH)));
        assertThat(room.getHeight(), is(equalTo(DEFAULT_HEIGHT)));
    }

    @Test
    void testConstructorWithOtherRoomAsArgumentNotTheSame() {
        Room argumentRoom = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Room room = new Room(argumentRoom);
        assertTrue(argumentRoom != room);
    }

    @Test
    void testConstructorWithOtherRoomAsArgumentCorrectWidthAndHeight() {
        Room argumentRoom = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Room room = new Room(argumentRoom);
        assertThat(room.getWidth(), is(equalTo(DEFAULT_WIDTH)));
        assertThat(room.getHeight(), is(equalTo(DEFAULT_HEIGHT)));
    }

    @Test
    void testConstructorThrowsOnWidth() {
        assertThrows(IllegalArgumentException.class, () -> {
            double width = -1.0;
            double height = 1.0;
            new Room(width, height);
        });
    }

    @Test
    void testConstructorThrowsOnHeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            double width = 1.0;
            double height = -1.0;
            new Room(width, height);
        });
    }

    @Test
    void testSetId() {
        int newId = 10;
        Room room = new Room(0, 0);
        room.setId(newId);
        assertThat(room.getId(), is(equalTo(newId)));
    }

    @Test
    void testSetConnectedTrue() {
        boolean expected = true;
        Room room = new Room(0, 0);
        room.setConnected(expected);
        assertTrue(room.isConnected());
    }

    @Test
    void testSetConnectedFalse() {
        boolean expected = false;
        Room room = new Room(0, 0);
        room.setConnected(expected);
        assertFalse(room.isConnected());
    }

    @Test
    void testSetPosition() {
        double expectedX = 10.0;
        double expectedY = 20.0;
        Room room = new Room(0, 0);
        room.setPosition(expectedX, expectedY);
        Point2D position = room.getPosition();
        assertThat(position.getX(), is(equalTo(expectedX)));
        assertThat(position.getY(), is(equalTo(expectedY)));
    }

    @Test
    void testSetWidth() {
        Room room = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        double newWidth = 22.0;
        room.setWidth(newWidth);
        assertThat(room.getWidth(), is(equalTo(newWidth)));
    }

    @Test
    void testSetWidthThrows() {
        Room room = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        assertThrows(IllegalArgumentException.class, () -> {
            double width = -1.0;
            room.setWidth(width);
        });
    }

    @Test
    void testSetHeight() {
        Room room = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        double newHeight = 44.0;
        room.setHeight(newHeight);
        assertThat(room.getHeight(), is(equalTo(newHeight)));
    }

    @Test
    void testSetHeightThrows() {
        Room room = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        assertThrows(IllegalArgumentException.class, () -> {
            double height = -1.0;
            room.setHeight(height);
        });
    }

    @Test
    void testEquals() {
        Room room = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Room room2 = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        assertTrue(room.equals(room2));
    }

    @Test
    void testNotEquals() {
        Room room = new Room(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Room room2 = new Room(DEFAULT_WIDTH + 1, DEFAULT_HEIGHT + 1);
        assertFalse(room.equals(room2));
    }
}
