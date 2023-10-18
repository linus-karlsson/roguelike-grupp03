
package com.rougelike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DungeonGeneratorTest {

    private static final double DEFAULT_MIN_WIDTH = 10.0;
    private static final double DEFAULT_MIN_HEIGHT = 10.0;
    private static final double DEFAULT_MAX_WIDTH = 30.0;
    private static final double DEFAULT_MAX_HEIGHT = 30.0;
    private static final int DEFAULT_ROW_COUNT = 80;
    private static final int DEFAULT_COLUMN_COUNT = 80;
    private static final int DEFAULT_ROOM_COUNT = 10;
    private static final int DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD = 10;

    private DungeonGenerator map = new DungeonGenerator();

    @Test
    public void testGenerateRoomWithinBounds() {
        Room room = map.generateRoom(DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        boolean expected = isWithinBounds(room.getWidth(), room.getHeight(), DEFAULT_MIN_WIDTH,
                DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

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
        Room room = map.generateRoom(DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        boolean expected = room.getWidth() == randomDoubleInBounds(randomMultiplier[0], DEFAULT_MIN_WIDTH,
                DEFAULT_MAX_WIDTH) &&
                room.getHeight() == randomDoubleInBounds(randomMultiplier[1], DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

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
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        int index = 0;
        for (Room room : rooms) {
            boolean expected = room.getWidth() == randomDoubleInBounds(randomMultiplier[index++], DEFAULT_MIN_WIDTH,
                    DEFAULT_MAX_WIDTH) &&
                    room.getHeight() == randomDoubleInBounds(randomMultiplier[index++], DEFAULT_MIN_HEIGHT,
                            DEFAULT_MAX_HEIGHT);
            assertTrue(expected);
        }

    }

    @Test
    public void testGenerateMultipleRoomsVariablitiy() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        boolean variablitiyInWidth = false;
        boolean variabilityInHeight = false;

        Room lastRoom = rooms.get(rooms.size() - 1);
        for (Room room : rooms) {
            variablitiyInWidth |= lastRoom.getWidth() != room.getWidth();
            variabilityInHeight |= lastRoom.getHeight() != room.getHeight();
            lastRoom = room;
        }
        assertTrue(variablitiyInWidth && variabilityInHeight);
    }

    @Test
    public void testGenerateMultipleRoomsWithinBounds() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        for (Room room : rooms) {
            boolean expected = isWithinBounds(room.getWidth(), room.getHeight(), DEFAULT_MIN_WIDTH,
                    DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

            assertTrue(expected);
        }
    }

    @Test
    public void testGenerateMultipleRoomsSize() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        assertEquals(DEFAULT_ROOM_COUNT, rooms.size());
    }

    @Test
    public void testPlaceRoomsInAreaThrows() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        int rows = 0;
        int columns = 80;
        assertThrows(IllegalArgumentException.class, () -> {
            map.placeRoomsInArea(rooms, 1, rows, columns);
        });
    }

    private void checkIfRoomsHaveCorrectId(ArrayList<Room> rooms, Gridd gridd) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            gridd.getRoomParser().setRoom(room);
            ArrayList<Integer> roomTileList = gridd.getRoomParser().roomAreaToList();
            for (int tile : roomTileList) {
                assertEquals(i, tile, roomTileList.toString());
            }
        }
    }

    @Test
    public void testPlaceRoomsInArea() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        Gridd gridd = map.getCopyOfGridd();

        checkIfRoomsHaveCorrectId(placedRooms, gridd);
    }

    @Test
    public void testPlaceRoomsInAreaDependencyInjection() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        double randomMultiplier = 0.3;
        map.setRandom(new RandomInternal(randomMultiplier));

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        Gridd gridd = map.getCopyOfGridd();

        for (Room room : placedRooms) {
            gridd.getRoomParser().setRoom(room);
            ArrayList<Integer> roomTileList = gridd.getRoomParser().roomAreaToList();
            for (int tile : roomTileList) {
                assertEquals(0, tile, roomTileList.toString());
            }
        }
    }

    @ParameterizedTest
    @CsvSource(value = { "42, 42", "82, 82" })
    public void testPlaceRoomsInAreaIfAreaIsFilled(int rows, int columns) {
        // Rooms kommer vara 10 X 10
        // De tar alltså upp 4 X 4 tiles
        // 2 extra rows och colummer för border
        // Det får då plats 10 X 10 rum i utrymmet som har 42 X 42 tiles med storlek
        // 5.0(Map.TILE_SIZE)
        int extraForBorder = 2;
        int roomsInX = rows / ((int) (DEFAULT_MIN_WIDTH / DungeonGenerator.TILE_SIZE) + extraForBorder);
        int roomsInY = columns / ((int) (DEFAULT_MIN_HEIGHT / DungeonGenerator.TILE_SIZE) + extraForBorder);

        double randomMultiplier = 0.0;
        map.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = roomsInX * roomsInY;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
        map.setRandom(new RandomInternal(randomMultipliersForAreaIsFilled(roomCount, roomsInX, roomsInY)));

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, 1, rows, columns);
        int expectedSize = roomCount;
        assertEquals(expectedSize, placedRooms.size());
    }

    private double[] randomMultipliersForAreaIsFilled(int roomCount, int roomsInX, int roomsInY) {
        double[] randomMultipliers = new double[roomCount * 2]; // (x, y) till varje rum därför * 2
        int index = 0;
        for (int y = 0; y < roomsInY; y++) {
            for (int x = 0; x < roomsInX; x++) {
                // roomsIn* - 1.0 så det går från 0.0 till 1.0 inclusive
                randomMultipliers[index++] = (double) x / ((double) roomsInX - 1.0);
                randomMultipliers[index++] = (double) y / ((double) roomsInY - 1.0);
            }
        }
        return randomMultipliers;
    }

    @Test
    public void testPlaceRoomsInAreaMultipleTriesIfRoomNotFit() {
        double randomMultiplier = 0.0;
        map.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = 2;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        map.setRandom(new RandomInternal(
                randomMultipliersForMultipleTries(roomCount, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD)));

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROOM_COUNT, DEFAULT_COLUMN_COUNT);

        assertEquals(roomCount, placedRooms.size());

    }

    private double[] randomMultipliersForMultipleTries(int roomCount, int numberOfTriesBeforeDiscard) {
        double[] randomMultipliers = new double[(roomCount * 2) * numberOfTriesBeforeDiscard];
        int index = 0;
        randomMultipliers[index++] = 0.0;
        randomMultipliers[index++] = 0.0;
        for (int x = 1; x < roomCount; x++) {
            // Gör så att rummen kommer bli platserad rakt på varandra
            // numberOfTriesBeforeDiscard - 1 gånger och den sista gången blir den
            // platserad brevid den första
            int lastIndex = index - 2;
            for (int i = 0; i < numberOfTriesBeforeDiscard - 1; i++) {
                randomMultipliers[index++] = randomMultipliers[lastIndex];
                randomMultipliers[index++] = 0.0;
            }
            randomMultipliers[index++] = (double) x / ((double) roomCount - 1.0);
            randomMultipliers[index++] = 0.0;
        }
        return randomMultipliers;
    }

    @Test
    public void testPlaceRoomsInAreaEmptyTilesAroundRoom() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        Gridd gridd = map.getCopyOfGridd();

        for (Room room : placedRooms) {
            gridd.getRoomParser().setRoom(room);
            Gridd.Index startIndex = gridd.getRoomParser().getRoomStartIndex();
            Gridd.Index endIndex = gridd.getRoomParser().getRoomEndIndex();
            checkTilesAboveRoom(gridd, startIndex, endIndex.column);
            checkTilesBelowRoom(gridd, endIndex, startIndex.column);
            checkTilesToLeftOfRoom(gridd, startIndex, endIndex.row);
            checkTilesToRightOfRoom(gridd, endIndex, startIndex.row);
        }

    }

    private void checkTilesAboveRoom(Gridd gridd, Gridd.Index startIndex, int endColumn) {
        Gridd.Index i = gridd.new Index(startIndex);
        for (i.row -= 1, i.column -= 1; i.column <= endColumn; i.column++) {
            assertEquals(Gridd.BORDER_VALUE, gridd.getTile(i));
        }
    }

    private void checkTilesBelowRoom(Gridd gridd, Gridd.Index endIndex, int startColumn) {
        Gridd.Index i = gridd.new Index(endIndex);
        for (i.row += 1, i.column += 1; i.column >= startColumn; i.column--) {
            assertEquals(Gridd.BORDER_VALUE, gridd.getTile(i));
        }
    }

    private void checkTilesToLeftOfRoom(Gridd gridd, Gridd.Index startIndex, int endRow) {
        Gridd.Index i = gridd.new Index(startIndex);
        for (i.column -= 1; i.row <= endRow + 1; i.row++) {
            assertEquals(Gridd.BORDER_VALUE, gridd.getTile(i));
        }
    }

    private void checkTilesToRightOfRoom(Gridd gridd, Gridd.Index endIndex, int startRow) {
        Gridd.Index i = gridd.new Index(endIndex);
        for (i.column += 1; i.row >= startRow + 1; i.row--) {
            assertEquals(Gridd.BORDER_VALUE, gridd.getTile(i));
        }
    }

    @Test
    public void testGriddHasBorder() {
        int roomCount = 1;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD, DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        Gridd gridd = map.getCopyOfGridd();

        int expected = Gridd.BORDER_VALUE;
        int lastRow = DEFAULT_ROW_COUNT - 1;
        for (int column = 0; column < DEFAULT_COLUMN_COUNT; column++) {
            assertEquals(expected, gridd.getTile(0, column));
            assertEquals(expected, gridd.getTile(lastRow, column));
        }
        int lastColumn = DEFAULT_COLUMN_COUNT - 1;
        for (int row = 0; row < DEFAULT_ROW_COUNT; row++) {
            assertEquals(expected, gridd.getTile(row, 0));
            assertEquals(expected, gridd.getTile(row, lastColumn));
        }
    }

    @Test
    public void testPlaceRoomsInAreaOutOfBounds() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        Gridd gridd = map.getCopyOfGridd();

        for (Room room : placedRooms) {
            boolean expected = (room.getPosition().getX() + room.getWidth()) < gridd.getWidth() &&
                    (room.getPosition().getY() + room.getHeight()) < gridd.getHeight();

            assertTrue(expected);
        }

    }

    @Test
    public void testPlaceRoomsInAreaReturnMultipleRooms() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);

        assertTrue(placedRooms.size() > 1);
    }

    @Test
    public void testConnectRoomsAllConnected() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);

        map.connectRooms(placedRooms);
        for (Room room : placedRooms) {
            assertTrue(room.isConnected());
        }
    }

    @Test
    public void testConnectRoomsDepthFirst() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);

        map.connectRooms(placedRooms);
        int[] connectedRooms = Graph.getConnectedRooms(placedRooms, map.getCopyOfGridd());
        for (int expected = 0; expected < connectedRooms.length; expected++) {
            assertEquals(expected, connectedRooms[expected]);
        }
    }

    @Test
    public void testConnectRooms() {
        double randomMultiplier = 0.0;
        map.setRandom(new RandomInternal(randomMultiplier));

        int roomCount = 3;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        double[] randomMultipliers = { 0.0, 0.0, 0.5, 0.0, 0.0, 0.5 };
        map.setRandom(new RandomInternal(randomMultipliers));
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        map.connectRooms(placedRooms);
        Gridd gridd = map.getCopyOfGridd();

        Gridd.Index firstRoomGriddIndex = gridd.getGriddIndexBasedOnPosition(placedRooms.get(0).getPosition());
        Gridd.Index secondRoomGriddIndex = gridd.getGriddIndexBasedOnPosition(placedRooms.get(1).getPosition());
        Gridd.Index thirdRoomGriddIndex = gridd.getGriddIndexBasedOnPosition(placedRooms.get(2).getPosition());

        for (; firstRoomGriddIndex.column <= secondRoomGriddIndex.column; firstRoomGriddIndex.column++) {
            assertTrue(gridd.getTile(firstRoomGriddIndex) >= 0);
        }
        for (; secondRoomGriddIndex.row <= thirdRoomGriddIndex.row; secondRoomGriddIndex.row++) {
            assertTrue(gridd.getTile(secondRoomGriddIndex) >= 0);
        }
        for (secondRoomGriddIndex.row--; secondRoomGriddIndex.column >= thirdRoomGriddIndex.column; secondRoomGriddIndex.column--) {
            assertTrue(gridd.getTile(secondRoomGriddIndex) >= 0);
        }
    }

    @Test
    public void testConnectRoomsAllRoomsStillHaveId() {
        ArrayList<Room> rooms = map.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        map.connectRooms(placedRooms);

        Gridd gridd = map.getCopyOfGridd();
        checkIfRoomsHaveCorrectId(placedRooms, gridd);
    }

}
