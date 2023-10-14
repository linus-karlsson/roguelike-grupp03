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
        double[] randomMultiplier = { 0.3, 0.6 };
        RandomInternal randomInternal = new RandomInternal(randomMultiplier);
        map.setRandom(randomInternal);
        Map.Room room = map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);

        boolean expected = room.getWidth() == randomDoubleInBounds(randomMultiplier[0], minWidth, maxWidth) &&
                room.getHeight() == randomDoubleInBounds(randomMultiplier[1], minHeight, maxHeight);

        assertTrue(expected);
    }

    // Tagen från java docs
    private double randomDoubleInBounds(double multiplier, double low, double high) {
        return (multiplier * (high - low)) + low;
    }

    @Test
    public void testGenerateMultipleRoomsDependencyInjection() {
        double[] randomMultiplier = { 0.3, 0.4, 0.2, 0.1, 0.99,
                0.6, 0.33, 0.22, 0.25, 0.78 };
        RandomInternal randomInternal = new RandomInternal(randomMultiplier);
        map.setRandom(randomInternal);

        int roomCount = 5;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int index = 0;
        for (Map.Room room : rooms) {
            boolean expected = room.getWidth() == randomDoubleInBounds(randomMultiplier[index++], minWidth, maxWidth) &&
                    room.getHeight() == randomDoubleInBounds(randomMultiplier[index++], minHeight, maxHeight);
            assertTrue(expected);
        }

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

    @Test
    public void testPlaceRoomsInAreaThrows() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int rows = 0;
        int columns = 80;
        double cellSize = 10.0;
        assertThrows(IllegalArgumentException.class, () -> {
            map.placeRoomsInArea(rooms, 1, rows, columns, cellSize);
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
        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns, cellSize);
        ArrayList<Integer> gridd = map.getCopyOfGridd();

        Map.GriddParser griddParser = map.new GriddParser(columns, rows, cellSize);
        for (Map.Room room : placedRooms) {
            griddParser.setRoom(room);
            while (griddParser.hasNextIndex()) {
                int index = griddParser.nextIndex();
                assertEquals(1, gridd.get(index));
            }
        }
    }

    @Test
    public void testPlaceRoomsInAreaDependencyInjection() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        double randomMultiplier = 0.3;
        map.setRandom(new RandomInternal(randomMultiplier));

        int rows = 160;
        int columns = 160;
        double cellSize = 5.0;
        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns, cellSize);
        ArrayList<Integer> gridd = map.getCopyOfGridd();

        Map.GriddParser griddParser = map.new GriddParser(columns, rows, cellSize);
        for (Map.Room room : placedRooms) {
            griddParser.setRoom(room);
            while (griddParser.hasNextIndex()) {
                int index = griddParser.nextIndex();
                assertEquals(1, gridd.get(index));
            }
        }
    }

    @ParameterizedTest
    @CsvSource(value = { "40, 40", "80, 80" })
    public void testPlaceRoomsInAreaIfAreaIsFilled(int rows, int columns) {
        // Rooms kommer vara 10 X 10
        // De tar alltså upp 4 X 4 cells
        // Det får då plats 10 X 10 rum i utrymmet som har 40 X 40 cells med storlek 5.0

        double cellSize = 5.0;
        minWidth = 10.0;
        minHeight = minWidth;

        int roomsInX = rows / ((int) (minWidth / cellSize) + 2);
        int roomsInY = columns / ((int) (minHeight / cellSize) + 2);

        double randomMultiplier = 0.0;
        map.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = roomsInX * roomsInY;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // Två värden per rum, (x and y)
        double[] randomMultipliers = new double[roomCount * 2];
        int index = 0;
        for (int y = 0; y < roomsInY; y++) {
            for (int x = 0; x < roomsInX; x++) {
                // - 1.0 så det går från 0.0 - 1.0 inclusive
                randomMultipliers[index++] = (double) x / ((double) roomsInX - 1.0);
                randomMultipliers[index++] = (double) y / ((double) roomsInY - 1.0);
            }
        }
        map.setRandom(new RandomInternal(randomMultipliers));

        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns, cellSize);
        int expectedSize = roomCount;
        assertEquals(expectedSize, placedRooms.size());
    }

    /*
     * @Test
     * public void testPlaceRoomsInAreaMultipleTriesIfRoomNotFit() {
     * int rows = 30;
     * int columns = 30;
     * double cellSize = 5.0;
     * minWidth = 10.0;
     * minHeight = minWidth;
     * 
     * int roomsInX = rows / ((int) (minWidth / cellSize) + 1);
     * int roomsInY = columns / ((int) (minHeight / cellSize) + 1);
     * 
     * double randomMultiplier = 0.0;
     * map.setRandom(new RandomInternal(randomMultiplier));
     * int roomCount = roomsInX * roomsInY;
     * ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth,
     * maxWidth,
     * minHeight, maxHeight);
     * 
     * int numberOfTriesBeforeDiscard = 10;
     * double[] randomMultipliers = new double[(roomCount * 2) *
     * numberOfTriesBeforeDiscard];
     * int index = 0;
     * for (int y = 0; y < roomsInY; y++) {
     * for (int x = 0; x < roomsInX; x++) {
     * if (index > 0) {
     * int lastIndex = index - 2;
     * for (int i = 0; i < numberOfTriesBeforeDiscard - 1; i++) {
     * randomMultipliers[index++] = randomMultipliers[lastIndex];
     * randomMultipliers[index++] = randomMultipliers[lastIndex + 1];
     * }
     * }
     * randomMultipliers[index++] = (double) x / ((double) roomsInX - 1.0);
     * randomMultipliers[index++] = (double) y / ((double) roomsInY - 1.0);
     * }
     * }
     * map.setRandom(new RandomInternal(randomMultipliers));
     * 
     * ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms,
     * numberOfTriesBeforeDiscard, rows, columns,
     * cellSize);
     * int expectedSize = roomCount;
     * assertEquals(expectedSize, placedRooms.size());
     * 
     * }
     */

    @Test
    public void testPlaceRoomsInAreaMultipleTriesIfRoomNotFit() {
        int rows = 30;
        int columns = 30;
        double cellSize = 5.0;
        minWidth = 10.0;
        minHeight = minWidth;

        double randomMultiplier = 0.0;
        map.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = 2;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int numberOfTriesBeforeDiscard = 10;
        double[] randomMultipliers = new double[(roomCount * 2) * numberOfTriesBeforeDiscard];
        int index = 0;
        for (int x = 0; x < roomCount; x++) {
            if (index > 0) {
                // Gör så att andra rummet kommer bli platserad rakt på det första rummet
                // numberOfTriesBeforeDiscard - 1 gånger och den sista gången så blir den
                // platserad brevid
                int lastIndex = index - 2;
                for (int i = 0; i < numberOfTriesBeforeDiscard - 1; i++) {
                    randomMultipliers[index++] = randomMultipliers[lastIndex];
                    randomMultipliers[index++] = 0.0;
                }
            }
            randomMultipliers[index++] = (double) x / ((double) roomCount - 1.0);
            randomMultipliers[index++] = 0.0;
        }
        map.setRandom(new RandomInternal(randomMultipliers));

        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, numberOfTriesBeforeDiscard, rows, columns,
                cellSize);
        int expectedSize = roomCount;
        assertEquals(expectedSize, placedRooms.size());

    }

    @Test
    public void testPlaceRoomsInAreaEmptyCellsAroundRoom() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 160;
        int columns = 160;
        double cellSize = 5.0;
        int numberOfTriesBeforeDiscard = 10;
        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, numberOfTriesBeforeDiscard, rows, columns,
                cellSize);
        ArrayList<Integer> gridd = map.getCopyOfGridd();

        Map.GriddParser griddParser = map.new GriddParser(columns, rows, cellSize);
        for (Map.Room room : placedRooms) {
            griddParser.setRoom(room);
            int startIndex = griddParser.getStartIndex();
            int endIndex = griddParser.getEndIndex();
            int cellCountInX = griddParser.getCurrentRoomCellCountInX();
            int cellCountInY = griddParser.getCurrentRoomCellCountInY();
            checkCellsAboveRoom(gridd, startIndex, columns, cellCountInX);
            checkCellsBelowRoom(gridd, endIndex, columns, rows, cellCountInX);
            checkCellsToLeftOfRoom(gridd, startIndex, columns, cellCountInY);
            checkCellsToRightOfRoom(gridd, endIndex, columns, cellCountInY);
        }

    }

    private void checkCellsAboveRoom(ArrayList<Integer> gridd, int startIndex, int columns, int cellCountInX) {
        if (startIndex > columns) {
            int offsetIndex = startIndex - columns;
            for (int i = 0; i < cellCountInX; i++) {
                assertEquals(-1, gridd.get(offsetIndex + i));
            }
        }
    }

    private void checkCellsBelowRoom(ArrayList<Integer> gridd, int endIndex, int columns, int rows, int cellCountInX) {
        if (endIndex <= columns * (rows - 1)) {
            int offsetIndex = endIndex + columns;
            for (int i = 0; i < cellCountInX; i++) {
                assertEquals(-1, gridd.get(offsetIndex - i));
            }
        }
    }

    private void checkCellsToLeftOfRoom(ArrayList<Integer> gridd, int startIndex, int columns, int cellCountInY) {
        if (startIndex % columns != 0) {
            for (int i = 0; i < cellCountInY; i++) {
                assertEquals(-1, gridd.get((startIndex - 1) + (columns * i)));
            }
        }
    }

    private void checkCellsToRightOfRoom(ArrayList<Integer> gridd, int endIndex, int columns, int cellCountInY) {
        if ((endIndex + 1) % columns != 0) {
            for (int i = 0; i < cellCountInY; i++) {
                assertEquals(-1, gridd.get((endIndex + 1) - (columns * i)));
            }
        }
    }

    @Test
    public void testPlaceRoomsInAreaOutOfBounds() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 160;
        int columns = 160;
        double cellSize = 5.0;
        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns, cellSize);

        double widthOfArea = columns * cellSize;
        double heightOfArea = rows * cellSize;
        for (Map.Room room : placedRooms) {
            boolean expected = (room.getPosition().getX() + room.getWidth()) < widthOfArea &&
                    (room.getPosition().getY() + room.getHeight()) < heightOfArea;

            assertTrue(expected);
        }

    }

    @Test
    public void testPlaceRoomsInAreaReturnMultipleRooms() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 160;
        int columns = 160;
        double cellSize = 5.0;
        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns, cellSize);
        assertTrue(placedRooms.size() > 1);
    }

    @Test
    public void testGetGriddIndexBasedOnPosition() {
        int columns = 80;
        double cellSize = 10.0;
        Point point = new Point();
        int cellsInX = 3;
        int cellsInY = 2;
        point.setX(cellSize * cellsInX);
        point.setY(cellSize * cellsInY);
        int expected = (columns * cellsInY) + cellsInX;
        assertEquals(expected, map.getGriddIndexBasedOnPosition(point, columns, cellSize));
    }

    @Test
    public void testGetCopyGridd() {
        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int rows = 80;
        int columns = 80;
        double cellSize = 10.0;
        map.placeRoomsInArea(rooms, 1, rows, columns, cellSize);

        ArrayList<Integer> gridd = map.getCopyOfGridd();

        assertTrue(gridd.size() == rows * columns);
    }

    @Test
    public void testGriddParserGetRoomCellCountInX() {
        Map.Room room = map.new Room(10.0, 10.0);

        int columns = 80;
        int rows = 80;
        Map.GriddParser griddParser = map.new GriddParser(columns, rows, 5.0);

        griddParser.setRoom(room);
        int cellCountInX = griddParser.getCurrentRoomCellCountInX();

        assertEquals(3, cellCountInX);
    }

    @Test
    public void testGriddParserGetRoomCellCountInY() {
        Map.Room room = map.new Room(10.0, 10.0);

        int columns = 80;
        int rows = 80;
        Map.GriddParser griddParser = map.new GriddParser(columns, rows, 5.0);

        griddParser.setRoom(room);
        int cellCountInX = griddParser.getCurrentRoomCellCountInY();

        assertEquals(3, cellCountInX);

    }
}
