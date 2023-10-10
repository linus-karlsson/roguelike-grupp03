package com.rougelike;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MapTest {

    private Map map = new Map();
    private double minWidth = 10.0;
    private double maxWidth = 30.0;
    private double minHeight = 10.0;
    private double maxHeight = 30.0;

    @Test
    public void testGenerateRoomWithinBounds() {
        Map.Room room = map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);

        boolean expected = isWithinBounds(room.getWidth(), room.getHeight(), minWidth,
                maxWidth, minHeight, maxHeight);

        assertTrue(expected);
    }

    private boolean isWithinBounds(double actualRoomWidth, double actualRoomHeight, double minWidth, double maxWidth,
            double minHeight,
            double maxHeight) {
        return closedInterval(minWidth, actualRoomWidth, maxWidth)
                && closedInterval(minHeight, actualRoomHeight, maxHeight);
    }

    @ParameterizedTest
    @CsvSource(value = { "0.0, 100.0, 10.0, 100.0", "10.0, 100.0, 0.0, 100.0", "20.0, 401.0, 20.0, 140.0",
            "20.0, 140.0, 20.0, 401.0" })
    public void testGenerateRoomWithinBoundsThrows(double minWidth, double maxWidth, double minHeight,
            double maxHeight) {
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
        map = createMapDependencyInjection(randomMultiplier);
        Map.Room room = map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);

        boolean expected = room.getWidth() == randomDoubleInBounds(randomMultiplier, minWidth, maxWidth) &&
                room.getHeight() == randomDoubleInBounds(randomMultiplier, minHeight, maxHeight);

        assertTrue(expected);
    }

    private double randomDoubleInBounds(double multiplier, double low, double high) {
        return (multiplier * (high - low)) + low;
    }

    @Test
    public void testGenerateMultipleRoomsVariablitiy() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        boolean variablitiyInWidth = false;
        boolean variabilityInHeight = false;

        double savedWidth = rooms.get(0).getWidth();
        double savedHeight = rooms.get(0).getHeight();

        for (Map.Room room : rooms) {
            if (savedWidth != room.getWidth()) {
                variablitiyInWidth = true;
            }
            if (savedHeight != room.getHeight()) {
                variabilityInHeight = true;
            }
        }
        assertTrue(variablitiyInWidth && variabilityInHeight);
    }

    @Test
    public void testGenerateMultipleRoomsWithinBounds() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        for (Map.Room room : rooms) {
            boolean expected = isWithinBounds(room.getWidth(), room.getHeight(), minWidth,
                    maxWidth, minHeight, maxHeight);

            assertTrue(expected);
        }
    }

    @Test
    public void testGenerateMultipleRoomsSize() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        assertEquals(roomCount, rooms.size());
    }

    private Map createMapDependencyInjection(double randomMultiplier) {
        RandomInternal randomInternal = new RandomInternal(randomMultiplier);
        return new Map(randomInternal);
    }

    @Test
    public void testPlaceRoomsInAreaThrows() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int rows = 0;
        int columns = 80;
        double cellSize = 10.0;
        assertThrows(IllegalArgumentException.class, () -> {
            map.placeRoomsInArea(rooms, rows, columns, cellSize);
        });
    }

    @Test
    public void testPlaceRoomsInArea() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 160;
        int columns = 160;
        double cellSize = 5.0;
        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, rows, columns, cellSize);

        ArrayList<Integer> gridd = map.getCopyOfGridd();

        for (Map.Room room : placedRooms) {
            double xStart = room.getPosition().getX();
            double xSpand = xStart + room.getWidth();
            double yStart = room.getPosition().getY();
            double ySpand = yStart + room.getHeight();

            int startIndex = map.getCanonicalPostion(room.getPosition(), columns, cellSize);
            Point xEndPosition = new Point(xSpand, yStart);
            int endIndexInX = map.getCanonicalPostion(xEndPosition, columns, cellSize);
            Point lastPoistion = new Point(xSpand, ySpand);
            int endIndex = map.getCanonicalPostion(lastPoistion, columns, cellSize);

            int index = startIndex;
            int rowCount = 1;
            while (index <= endIndex) {
                if (index == endIndexInX) {
                    index = startIndex + (columns * rowCount);
                    endIndexInX += (columns * rowCount);
                    rowCount++;
                }
                assertEquals(1, gridd.get(index));
                index++;
            }
        }
    }

    @Test
    public void testGetCanonicalPosition() {
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
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int rows = 80;
        int columns = 80;
        double cellSize = 10.0;
        map.placeRoomsInArea(rooms, rows, columns, cellSize);

        ArrayList<Integer> gridd = map.getCopyOfGridd();

        assertTrue(gridd.size() == rows * columns);
    }

}
