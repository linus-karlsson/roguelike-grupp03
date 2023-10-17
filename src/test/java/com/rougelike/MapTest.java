
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
        Room room = map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);

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
        Room room = map.generateRoom(minWidth, maxWidth, minHeight, maxHeight);

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
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int index = 0;
        for (Room room : rooms) {
            boolean expected = room.getWidth() == randomDoubleInBounds(randomMultiplier[index++], minWidth, maxWidth) &&
                    room.getHeight() == randomDoubleInBounds(randomMultiplier[index++], minHeight, maxHeight);
            assertTrue(expected);
        }

    }

    @Test
    public void testGenerateMultipleRoomsVariablitiy() {
        int roomCount = 10;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        boolean variablitiyInWidth = false;
        boolean variabilityInHeight = false;

        double savedWidth = rooms.get(0).getWidth();
        double savedHeight = rooms.get(0).getHeight();

        for (Room room : rooms) {
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
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        for (Room room : rooms) {
            boolean expected = isWithinBounds(room.getWidth(), room.getHeight(), minWidth,
                    maxWidth, minHeight, maxHeight);

            assertTrue(expected);
        }
    }

    @Test
    public void testGenerateMultipleRoomsSize() {
        int roomCount = 10;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        assertEquals(roomCount, rooms.size());
    }

    @Test
    public void testPlaceRoomsInAreaThrows() {
        int roomCount = 10;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int rows = 0;
        int columns = 80;
        assertThrows(IllegalArgumentException.class, () -> {
            map.placeRoomsInArea(rooms, 1, rows, columns);
        });
    }

    @Test
    public void testPlaceRoomsInArea() {
        int roomCount = 10;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 160;
        int columns = 160;
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns);
        Gridd gridd = map.getCopyOfGridd();

        for (int i = 0; i < placedRooms.size(); i++) {
            Room room = placedRooms.get(i);
            gridd.getRoomParser().setRoom(room);
            while (gridd.getRoomParser().hasNextIndex()) {
                Gridd.Index index = gridd.getRoomParser().nextIndex();
                assertEquals(i, gridd.getTile(index));
            }
        }
    }

    @Test
    public void testPlaceRoomsInAreaDependencyInjection() {
        int roomCount = 10;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        double randomMultiplier = 0.3;
        map.setRandom(new RandomInternal(randomMultiplier));

        int rows = 160;
        int columns = 160;
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns);
        Gridd gridd = map.getCopyOfGridd();

        for (Room room : placedRooms) {
            gridd.getRoomParser().setRoom(room);
            while (gridd.getRoomParser().hasNextIndex()) {
                Gridd.Index index = gridd.getRoomParser().nextIndex();
                assertEquals(0, gridd.getTile(index));
            }
        }
    }

    @ParameterizedTest
    @CsvSource(value = { "42, 42", "82, 82" })
    public void testPlaceRoomsInAreaIfAreaIsFilled(int rows, int columns) {
        // Rooms kommer vara 10 X 10
        // De tar alltså upp 4 X 4 cells
        // 2 extra rows och colummer för border
        // Det får då plats 10 X 10 rum i utrymmet som har 42 X 42 cells med storlek 5.0

        minWidth = 10.0;
        minHeight = minWidth;

        int roomsInX = rows / ((int) (minWidth / Map.TILE_SIZE) + 2);
        int roomsInY = columns / ((int) (minHeight / Map.TILE_SIZE) + 2);

        double randomMultiplier = 0.0;
        map.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = roomsInX * roomsInY;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
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

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns);
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
        minWidth = 10.0;
        minHeight = minWidth;

        double randomMultiplier = 0.0;
        map.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = 2;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
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

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, numberOfTriesBeforeDiscard, rows, columns);
        int expectedSize = roomCount;
        assertEquals(expectedSize, placedRooms.size());

    }

    @Test
    public void testPlaceRoomsInAreaEmptyCellsAroundRoom() {
        int roomCount = 10;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 160;
        int columns = 160;
        int numberOfTriesBeforeDiscard = 10;
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, numberOfTriesBeforeDiscard, rows, columns);
        Gridd gridd = map.getCopyOfGridd();

        for (Room room : placedRooms) {
            gridd.getRoomParser().setRoom(room);
            Gridd.Index startIndex = gridd.getRoomParser().getRoomStartIndex();
            Gridd.Index endIndex = gridd.getRoomParser().getRoomEndIndex();
            checkCellsAboveRoom(gridd, startIndex, endIndex.column);
            checkCellsBelowRoom(gridd, endIndex, startIndex.column);
            checkCellsToLeftOfRoom(gridd, startIndex, endIndex.row);
            checkCellsToRightOfRoom(gridd, endIndex, startIndex.row);
        }

    }

    private void checkCellsAboveRoom(Gridd gridd, Gridd.Index startIndex, int endColumn) {
        Gridd.Index i = gridd.new Index(startIndex);
        for (i.row -= 1, i.column -= 1; i.column <= endColumn; i.column++) {
            assertTrue(gridd.getTile(i) <= Gridd.ROOM_BORDER_VALUE);
        }
    }

    private void checkCellsBelowRoom(Gridd gridd, Gridd.Index endIndex, int startColumn) {
        Gridd.Index i = gridd.new Index(endIndex);
        for (i.row += 1, i.column += 1; i.column >= startColumn; i.column--) {
            assertTrue(gridd.getTile(i) <= Gridd.ROOM_BORDER_VALUE);
        }
    }

    private void checkCellsToLeftOfRoom(Gridd gridd, Gridd.Index startIndex, int endRow) {
        Gridd.Index i = gridd.new Index(startIndex);
        for (i.column -= 1; i.row <= endRow + 1; i.row++) {
            assertTrue(gridd.getTile(i) <= Gridd.ROOM_BORDER_VALUE);
        }
    }

    private void checkCellsToRightOfRoom(Gridd gridd, Gridd.Index endIndex, int startRow) {
        Gridd.Index i = gridd.new Index(endIndex);
        for (i.column += 1; i.row >= startRow + 1; i.row--) {
            assertTrue(gridd.getTile(i) <= Gridd.ROOM_BORDER_VALUE);
        }
    }

    @Test
    public void testGriddHasBorder() {
        int roomCount = 1;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int rows = 80;
        int columns = 80;
        int numberOfTriesBeforeDiscard = 10;
        map.placeRoomsInArea(rooms, numberOfTriesBeforeDiscard, rows, columns);
        Gridd gridd = map.getCopyOfGridd();

        int expected = -3;
        int lastRow = rows - 1;
        for (int column = 0; column < columns; column++) {
            assertEquals(expected, gridd.getTile(0, column));
            assertEquals(expected, gridd.getTile(lastRow, column));
        }
        int lastColumn = columns - 1;
        for (int row = 0; row < rows; row++) {
            assertEquals(expected, gridd.getTile(row, 0));
            assertEquals(expected, gridd.getTile(row, lastColumn));
        }
    }

    @Test
    public void testPlaceRoomsInAreaOutOfBounds() {
        int roomCount = 10;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 160;
        int columns = 160;
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns);

        double widthOfArea = columns * Map.TILE_SIZE;
        double heightOfArea = rows * Map.TILE_SIZE;
        for (Room room : placedRooms) {
            boolean expected = (room.getPosition().getX() + room.getWidth()) < widthOfArea &&
                    (room.getPosition().getY() + room.getHeight()) < heightOfArea;

            assertTrue(expected);
        }

    }

    @Test
    public void testPlaceRoomsInAreaReturnMultipleRooms() {
        int roomCount = 10;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 160;
        int columns = 160;
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns);
        assertTrue(placedRooms.size() > 1);
    }

}
