package com.rougelike;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    @Test
    public void testGenerateRoom() {
        Map map = new Map();

        double minWidth = 10.0f;
        double maxWidth = 30.0f;
        double minHeight = 10.0f;
        double maxHeight = 30.0f;
        Map.Room room = map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);

        double roomWidth = room.getWidth();
        double roomHeight = room.getHeight();
        boolean expected = closedInterval(minWidth, roomWidth, maxWidth)
                && closedInterval(minHeight, roomHeight, maxHeight);

        assertTrue(expected);
    }

    private boolean closedInterval(double min, double value, double max) {
        return value >= min && value <= max;
    }
}
