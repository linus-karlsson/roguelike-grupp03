
package com.rougelike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
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

    private DungeonGenerator dungeonGenerator = new DungeonGenerator();

    @Test
    public void testGenerateRoomWithinBounds() {
        Room room = dungeonGenerator.generateRoom(DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT,
                DEFAULT_MAX_HEIGHT);

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
    @CsvSource(value = { "4.0, 100.0, 10.0, 100.0", "10.0, 100.0, 4.0, 100.0", "20.0, 401.0, 20.0, 140.0",
            "20.0, 140.0, 20.0, 401.0" })
    public void testGenerateRoomWithinBoundsThrows(double minWidth, double maxWidth, double minHeight,
            double maxHeight) {
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.generateRoom(minWidth, maxWidth, minHeight, maxHeight);
        });
    }

    private boolean closedInterval(double min, double value, double max) {
        return value >= min && value <= max;
    }

    @Test
    public void testGenerateRoomDependencyInjection() {
        double[] randomMultiplier = { 0.3, 0.6 };
        RandomInternal randomInternal = new RandomInternal(randomMultiplier);
        dungeonGenerator.setRandom(randomInternal);
        Room room = dungeonGenerator.generateRoom(DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT,
                DEFAULT_MAX_HEIGHT);

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
        dungeonGenerator.setRandom(randomInternal);

        int roomCount = 5;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
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
        List<Room> rooms = getDefaultRooms();

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

    private List<Room> getDefaultRooms() {
        return dungeonGenerator.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH,
                DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
    }

    @Test
    public void testGenerateMultipleRoomsWithinBounds() {
        List<Room> rooms = getDefaultRooms();
        for (Room room : rooms) {
            boolean expected = isWithinBounds(room.getWidth(), room.getHeight(), DEFAULT_MIN_WIDTH,
                    DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

            assertTrue(expected);
        }
    }

    @Test
    public void testGenerateMultipleRoomsSize() {
        assertEquals(DEFAULT_ROOM_COUNT, getDefaultRooms().size());
    }

    @Test
    public void testPlaceRoomsInAreaThrows() {
        int rows = 0;
        int columns = 80;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.placeRoomsInArea(getDefaultRooms(), 1, rows, columns);
        });
    }

    @Test
    public void testPlaceRoomsInArea() {
        checkIfRoomsHaveCorrectId(getDefaultPlacedRooms(getDefaultRooms()),
                dungeonGenerator.getCopyOfGridd());
    }

    private List<Room> getDefaultPlacedRooms(List<Room> rooms) {
        return dungeonGenerator.placeRoomsInArea(rooms,
                DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
    }

    private void checkIfRoomsHaveCorrectId(List<Room> rooms, Grid grid) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            grid.getRoomParser().setRoom(room);
            ArrayList<Integer> roomTileList = grid.getRoomParser().roomAreaToList();
            for (int tile : roomTileList) {
                assertEquals(i, tile, roomTileList.toString());
            }
        }
    }

    @Test
    public void testPlaceRoomsInAreaDependencyInjection() {
        List<Room> rooms = getDefaultRooms();
        double randomMultiplier = 0.3;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));
        int expected = 1;
        assertEquals(expected, getDefaultPlacedRooms(rooms).size());
    }

    @ParameterizedTest
    @CsvSource(value = { "32, 32", "62, 62" })
    public void testPlaceRoomsInAreaIfAreaIsFilled(int rows, int columns) {
        // Rooms kommer vara 10 X 10
        // De tar alltså upp 3 X 3 tiles
        // 2 extra rows och colummer för border
        // Det får då plats 10 X 10 rum i utrymmet som har 32 X 32 tiles med storlek
        // 10.0(Map.TILE_SIZE)
        int extraForBorder = 2;
        int roomsInX = rows / ((int) (DEFAULT_MIN_WIDTH / DungeonGenerator.TILE_SIZE) + extraForBorder);
        int roomsInY = columns / ((int) (DEFAULT_MIN_HEIGHT / DungeonGenerator.TILE_SIZE) + extraForBorder);

        double randomMultiplier = 0.0;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = roomsInX * roomsInY;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
        dungeonGenerator.setRandom(new RandomInternal(randomMultipliersForAreaIsFilled(roomCount, roomsInX, roomsInY)));

        List<Room> placedRooms = dungeonGenerator.placeRoomsInArea(rooms, 1, rows, columns);
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
    public void testPlaceRoomsInAreaReturnsUnModifiedList() {
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        assertThrows(UnsupportedOperationException.class, () -> {
            placedRooms.set(0, placedRooms.get(1));
        });
    }

    @Test
    public void testPlaceRoomsInAreaMultipleTriesIfRoomNotFit() {
        double randomMultiplier = 0.0;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = 2;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        dungeonGenerator.setRandom(new RandomInternal(
                randomMultipliersForMultipleTries(roomCount, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD)));

        assertEquals(roomCount, getDefaultPlacedRooms(rooms).size());
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
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        Grid grid = dungeonGenerator.getCopyOfGridd();
        for (Room room : placedRooms) {
            grid.getRoomParser().setRoom(room);
            Grid.Index startIndex = grid.getRoomParser().getRoomStartIndex();
            Grid.Index endIndex = grid.getRoomParser().getRoomEndIndex();
            checkTilesAboveRoom(grid, startIndex, endIndex.column);
            checkTilesBelowRoom(grid, endIndex, startIndex.column);
            checkTilesToLeftOfRoom(grid, startIndex, endIndex.row);
            checkTilesToRightOfRoom(grid, endIndex, startIndex.row);
        }

    }

    private void checkTilesAboveRoom(Grid grid, Grid.Index startIndex, int endColumn) {
        Grid.Index i = grid.new Index(startIndex);
        for (i.row -= 1, i.column -= 1; i.column <= endColumn; i.column++) {
            assertEquals(Grid.BORDER_VALUE, grid.getTile(i));
        }
    }

    private void checkTilesBelowRoom(Grid grid, Grid.Index endIndex, int startColumn) {
        Grid.Index i = grid.new Index(endIndex);
        for (i.row += 1, i.column += 1; i.column >= startColumn; i.column--) {
            assertEquals(Grid.BORDER_VALUE, grid.getTile(i));
        }
    }

    private void checkTilesToLeftOfRoom(Grid grid, Grid.Index startIndex, int endRow) {
        Grid.Index i = grid.new Index(startIndex);
        for (i.column -= 1; i.row <= endRow + 1; i.row++) {
            assertEquals(Grid.BORDER_VALUE, grid.getTile(i));
        }
    }

    private void checkTilesToRightOfRoom(Grid grid, Grid.Index endIndex, int startRow) {
        Grid.Index i = grid.new Index(endIndex);
        for (i.column += 1; i.row >= startRow + 1; i.row--) {
            assertEquals(Grid.BORDER_VALUE, grid.getTile(i));
        }
    }

    @Test
    public void testGriddHasBorder() {
        int roomCount = 1;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
        getDefaultPlacedRooms(rooms);

        assertTrue(dungeonGenerator.getCopyOfGridd().hasBorder());
    }

    @Test
    public void testPlaceRoomsInAreaOutOfBounds() {
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        Grid grid = dungeonGenerator.getCopyOfGridd();
        for (Room room : placedRooms) {
            boolean expected = (room.getPosition().getX() + room.getWidth()) < grid.getWidth() &&
                    (room.getPosition().getY() + room.getHeight()) < grid.getHeight();

            assertTrue(expected);
        }

    }

    @Test
    public void testConnectRoomsAllConnected() {
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        dungeonGenerator.connectRooms(placedRooms);
        for (Room room : placedRooms) {
            assertTrue(room.isConnected());
        }
    }

    // För att minimera chansen att något rum kopplar samman något annat så görs
    // gridden större samt att testet körs flera gånger
    @RepeatedTest(value = 10)
    public void testConnectRoomsDepthFirst() {
        int columns = 160;
        int rows = 160;
        List<Room> placedRooms = dungeonGenerator.placeRoomsInArea(getDefaultRooms(),
                DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD, rows, columns);
        dungeonGenerator.connectRooms(placedRooms);

        int[] connectedRooms = dungeonGenerator.getConnectedRooms(placedRooms, dungeonGenerator.getCopyOfGridd());
        for (int expected = 0; expected < placedRooms.size(); expected++) {
            assertEquals(expected, connectedRooms[expected]);
        }
    }

    @Test
    public void testConnectRooms() {
        double randomMultiplier = 0.0;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));

        int roomCount = 3;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        double[] randomMultipliers = { 0.0, 0.0, 0.5, 0.0, 0.0, 0.5 };
        dungeonGenerator.setRandom(new RandomInternal(randomMultipliers));
        List<Room> placedRooms = dungeonGenerator.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        dungeonGenerator.connectRooms(placedRooms);
        Grid grid = dungeonGenerator.getCopyOfGridd();

        Grid.Index firstRoomGriddIndex = grid.getGriddIndexBasedOnPosition(placedRooms.get(0).getPosition());
        Grid.Index secondRoomGriddIndex = grid.getGriddIndexBasedOnPosition(placedRooms.get(1).getPosition());
        Grid.Index thirdRoomGriddIndex = grid.getGriddIndexBasedOnPosition(placedRooms.get(2).getPosition());

        for (; firstRoomGriddIndex.column <= secondRoomGriddIndex.column; firstRoomGriddIndex.column++) {
            assertTrue(grid.getTile(firstRoomGriddIndex) >= 0);
        }
        for (; secondRoomGriddIndex.row <= thirdRoomGriddIndex.row; secondRoomGriddIndex.row++) {
            assertTrue(grid.getTile(secondRoomGriddIndex) >= 0);
        }
        for (secondRoomGriddIndex.row--; secondRoomGriddIndex.column >= thirdRoomGriddIndex.column; secondRoomGriddIndex.column--) {
            assertTrue(grid.getTile(secondRoomGriddIndex) >= 0);
        }
    }

    @Test
    public void testConnectRoomsAllRoomsStillHaveId() {
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        dungeonGenerator.connectRooms(placedRooms);
        Grid grid = dungeonGenerator.getCopyOfGridd();
        checkIfRoomsHaveCorrectId(placedRooms, grid);
    }

    @Test
    public void testConnectRoomsNoUnnessisaryCorridors() {
        double randomMultiplier = 1.0;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));

        int roomCount = 3;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        double[] randomMultipliers = { 0.5, 0.0, 0.5, 1.0, 0.5, 0.5 };
        dungeonGenerator.setRandom(new RandomInternal(randomMultipliers));
        List<Room> placedRooms = dungeonGenerator.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        int expected = 1;
        assertEquals(expected, dungeonGenerator.connectRooms(placedRooms));
    }

    // Platserar ett rum för varje tile i gridden och ser att getConnectedRooms
    // kan hitta alla rum
    @Test
    public void testGetConnectedRooms() {
        // - 1 för att gridden ska ha en border
        int rowCount = DEFAULT_ROW_COUNT - 1;
        int columnCount = DEFAULT_COLUMN_COUNT - 1;
        ArrayList<Room> placedRooms = new ArrayList<>(rowCount * columnCount);
        double tileSize = 1.0;
        Grid grid = new Grid(DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT, tileSize);
        grid.setBorder();
        Grid.Index i = grid.new Index();
        // = 1 för bordern också
        for (i.row = 1; i.row < rowCount; i.row++) {
            for (i.column = 1; i.column < columnCount; i.column++) {
                grid.setTile(i, placedRooms.size());
                Room room = new Room(tileSize, tileSize);
                room.setPosition((double) i.column * tileSize, (double) i.row * tileSize);
                placedRooms.add(room);
            }
        }
        int[] connectedRooms = dungeonGenerator.getConnectedRooms(placedRooms, grid);
        for (int expected = 0; expected < placedRooms.size(); expected++) {
            assertEquals(expected, connectedRooms[expected]);
        }
    }

    @Test
    public void testGetConnectedRoomsThrowsWithBorderlessGridd() {
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.getConnectedRooms(new ArrayList<Room>(),
                    new Grid(DEFAULT_ROOM_COUNT, DEFAULT_COLUMN_COUNT, DungeonGenerator.TILE_SIZE));
        });
    }

}
