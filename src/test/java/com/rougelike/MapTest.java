package com.rougelike;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MapTest {

    @Test
    public void testGenerateRoomWithinBounds() {
        Map map = new Map();

        double minWidth = 10.0;
        double maxWidth = 30.0;
        double minHeight = 10.0;
        double maxHeight = 30.0;
        Map.Room room = map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);

        double roomWidth = room.getWidth();
        double roomHeight = room.getHeight();
        boolean expected = closedInterval(minWidth, roomWidth, maxWidth)
                && closedInterval(minHeight, roomHeight, maxHeight);

        assertTrue(expected);
    }

    @ParameterizedTest
    @CsvSource(value = { "0.0, 100.0, 10.0, 100.0", "10.0, 100.0, 0.0, 100.0", "20.0, 401.0, 20.0, 140.0",
            "20.0, 140.0, 20.0, 401.0" })
    public void testGenerateRoomWithinBoundsThrows(double minWidth, double maxWidth, double minHeight,
            double maxHeight) {
        Map map = new Map();
        assertThrows(IllegalArgumentException.class, () -> {
            map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);
        });
    }

    private boolean closedInterval(double min, double value, double max) {
        return value >= min && value <= max;
    }

    @Test
    public void testGenerateRoomDependencyInjection() {
        double randomMultiplier = 0.3;
        Map map = createMapDependencyInjection(randomMultiplier);

        double minWidth = 10.0;
        double maxWidth = 30.0;
        double minHeight = 10.0;
        double maxHeight = 30.0;
        Map.Room room = map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);

        boolean expected = room.getWidth() == randomDoubleInBounds(randomMultiplier, minWidth, maxWidth) &&
                room.getHeight() == randomDoubleInBounds(randomMultiplier, minHeight, maxHeight);

        assertTrue(expected);
    }

    private double randomDoubleInBounds(double multiplier, double low, double high) {
        return (multiplier * (high - low)) + low;
    }

    @Test
    public void testGenerateMultipleRooms() {
        double randomMultiplier = 0.3;
        Map map = createMapDependencyInjection(randomMultiplier);

        double minWidth = 10.0;
        double maxWidth = 30.0;
        double minHeight = 10.0;
        double maxHeight = 30.0;
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth, minHeight, maxHeight);

        assertEquals(roomCount, rooms.size());
    }

    private Map createMapDependencyInjection(double randomMultiplier) {
        RandomInternal randomInternal = new RandomInternal(randomMultiplier);
        return new Map(randomInternal);
    }

    @Test
    public void testPlaceRoomsInAreaThrows() {
        Map map = new Map();

        ArrayList<Map.Room> rooms = map.generateListOfRooms(10, 10.0, 30.0, 10.0,
                30.0);

        int rows = 0;
        int columns = 80;
        double cellSize = 10.0;
        assertThrows(IllegalArgumentException.class, () -> {
            map.placeRoomsInArea(rooms, rows, columns, cellSize);
        });
    }

    @Test
    public void testPlaceRoomsInArea() {
        Map map = new Map();

        ArrayList<Map.Room> rooms = map.generateListOfRooms(10, 10.0, 30.0, 10.0,
                30.0);
        int rows = 80;
        int columns = 80;
        double cellSize = 10.0;
        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, rows, columns, cellSize);

        ArrayList<Integer> gridd = map.getCopyOfGridd();

        for (Map.Room room : placedRooms) {
            assertTrue(gridd.get(map.getCanonicalPostion(room.getPosition(), columns, cellSize)) == 1);
        }
    }

    @Test
    public void testGetCanonicalPosition() {
        Map map = new Map();
        int columns = 80;
        double cellSize = 10.0;
        Point point = new Point();
        int cellsInX = 3;
        int cellsInY = 2;
        point.setX(cellSize * cellsInX);
        point.setY(cellSize * cellsInY);
        int expected = (columns * cellsInY) + cellsInX;
        assertEquals(expected, map.getCanonicalPostion(point, columns, cellSize));
    }

    @Test
    public void testGetCopyGridd() {
        Map map = new Map();

        ArrayList<Map.Room> rooms = map.generateListOfRooms(10, 10.0, 30.0, 10.0,
                30.0);

        int rows = 80;
        int columns = 80;
        double cellSize = 10.0;
        map.placeRoomsInArea(rooms, rows, columns, cellSize);

        ArrayList<Integer> gridd = map.getCopyOfGridd();

        assertTrue(gridd.size() == rows * columns);
    }

}
