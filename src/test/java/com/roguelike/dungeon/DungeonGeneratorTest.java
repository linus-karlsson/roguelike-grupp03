
package com.roguelike.dungeon;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static com.roguelike.OnlyOneElementMatcher.containsOnly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.roguelike.Point2D;
import com.roguelike.RandomInternal;

public class DungeonGeneratorTest {

    private static final double MIN_WIDTH = DungeonGenerator.MIN_ROOM_WIDTH_OR_HEIGHT;
    private static final double MIN_HEIGHT = DungeonGenerator.MIN_ROOM_WIDTH_OR_HEIGHT;
    private static final double MAX_WIDTH = DungeonGenerator.MAX_ROOM_WIDTH_OR_HEIGHT;
    private static final double MAX_HEIGHT = DungeonGenerator.MAX_ROOM_WIDTH_OR_HEIGHT;
    private static final double DEFAULT_MIN_WIDTH = MIN_WIDTH;
    private static final double DEFAULT_MIN_HEIGHT = MIN_HEIGHT;
    private static final double DEFAULT_MAX_WIDTH = DEFAULT_MIN_WIDTH + 20.0;
    private static final double DEFAULT_MAX_HEIGHT = DEFAULT_MIN_HEIGHT + 20.0;
    private static final int DEFAULT_ROW_COUNT = 80;
    private static final int DEFAULT_COLUMN_COUNT = 80;
    private static final int DEFAULT_ROOM_COUNT = 10;
    private static final int DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD = 10;
    private static final double ERROR_ACCEPTANCE = 0.0001;

    private final DungeonGenerator dungeonGenerator = new DungeonGenerator();

    @Test
    void testGenerateRoomWithinBounds() {
        Room room = dungeonGenerator.generateRoom(MIN_WIDTH, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);

        boolean expected = isWithinBounds(room.getWidth(), room.getHeight(), MIN_WIDTH,
                MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);

        assertTrue(expected);
    }

    private boolean isWithinBounds(double actualRoomWidth, double actualRoomHeight, double minWidth, double maxWidth,
            double minHeight,
            double maxHeight) {
        return closedInterval(minWidth, actualRoomWidth, maxWidth)
                && closedInterval(minHeight, actualRoomHeight, maxHeight);
    }

    @Test
    void testGenerateRoomDependencyInjection() {
        double[] randomMultiplier = { 0.3, 0.6 };
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));
        Room room = dungeonGenerator.generateRoom(MIN_WIDTH, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);
        checkRoomWidthAndHeight(room, randomMultiplier[0], randomMultiplier[1], MAX_WIDTH, MAX_HEIGHT);
    }

    private void checkRoomWidthAndHeight(Room room, double widthRandomMultiplier, double heightRandomMultiplier,
            double maxWidth, double maxHeight) {
        double expectedRandomWidth = randomDoubleInBounds(widthRandomMultiplier, DEFAULT_MIN_WIDTH, maxWidth);
        double expectedRandomHeight = randomDoubleInBounds(heightRandomMultiplier, DEFAULT_MIN_HEIGHT,
                maxHeight);

        assertThat(room.getWidth(), is(equalTo(expectedRandomWidth)));
        assertThat(room.getHeight(), is(equalTo(expectedRandomHeight)));
    }

    // Tagen från java docs
    private double randomDoubleInBounds(double multiplier, double low, double high) {
        return (multiplier * (high - low)) + low;
    }

    @Test
    void testGenerateRoomThrowsOnMinWidth() {
        double minWidth = MIN_WIDTH - 1.0;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.generateRoom(minWidth, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);
        });
    }

    @Test
    void testGenerateRoomThrowsOnMaxWidth() {
        double maxWidth = MAX_WIDTH + 1.0;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.generateRoom(MIN_WIDTH, maxWidth, MIN_HEIGHT, MAX_HEIGHT);
        });
    }

    @Test
    void testGenerateRoomThrowsOnMinHeight() {
        double minHeight = MIN_HEIGHT - 1.0;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.generateRoom(MIN_WIDTH, MAX_WIDTH, minHeight, MAX_HEIGHT);
        });
    }

    @Test
    void testGenerateRoomThrowsOnMaxHeight() {
        double maxHeight = MAX_HEIGHT + 1.0;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.generateRoom(MIN_WIDTH, MAX_WIDTH, MIN_HEIGHT, maxHeight);
        });
    }

    @Test
    void testGenerateRoomThrowsOnMinWidthSameAsMaxWidth() {
        double minWidth = MAX_WIDTH;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.generateRoom(minWidth, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);
        });
    }

    @Test
    void testGenerateRoomThrowsOnMinHeightSameAsMaxHeight() {
        double minHeight = MAX_HEIGHT;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.generateRoom(MIN_WIDTH, MAX_WIDTH, minHeight, MAX_HEIGHT);
        });
    }

    private boolean closedInterval(double min, double value, double max) {
        return value >= min && value <= max;
    }

    @Test
    void testGenerateListOfRoomsThrows() {
        int roomCount = 0;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH,
                    DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
        });

    }

    @Test
    void testGenerateListOfRoomsSize() {
        assertThat(getDefaultRooms(), hasSize(DEFAULT_ROOM_COUNT));
    }

    @Test
    void testGenerateListOfRoomsWithinBounds() {
        List<Room> rooms = getDefaultRooms();
        for (Room room : rooms) {
            boolean expected = isWithinBounds(room.getWidth(), room.getHeight(), DEFAULT_MIN_WIDTH,
                    DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
            assertTrue(expected);
        }
    }

    @Test
    void testGenerateListOfRoomsVariablitiy() {
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

    @Test
    void testGenerateListOfRoomsDependencyInjection() {
        double[] randomMultiplier = { 0.3, 0.4, 0.2, 0.1, 0.99,
                0.6, 0.33, 0.22, 0.25, 0.78 };
        RandomInternal randomInternal = new RandomInternal(randomMultiplier);
        dungeonGenerator.setRandom(randomInternal);

        List<Room> rooms = getDefaultRooms();
        int index = 0;
        for (Room room : rooms) {
            checkRoomWidthAndHeight(room, randomMultiplier[index++ % randomMultiplier.length],
                    randomMultiplier[index++ % randomMultiplier.length], DEFAULT_MAX_WIDTH, DEFAULT_MAX_HEIGHT);
        }
    }

    private List<Room> getDefaultRooms() {
        return dungeonGenerator.generateListOfRooms(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH,
                DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
    }

    @ParameterizedTest
    @CsvSource(value = { "0,80,1", "80,0,1", "80,80,0" })
    void testPlaceRoomsInAreaThrows(int rowCount, int columnCount, int numberOfTriesBeforeDiscard) {
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.placeRoomsInArea(getDefaultRooms(), numberOfTriesBeforeDiscard, rowCount, columnCount,
                    DungeonGenerator.MIN_TILE_SIZE);
        });
    }

    @Test
    void testPlaceRoomsInAreaThrowsTileSize() {
        double tileSize = DungeonGenerator.MIN_TILE_SIZE - 1.0;
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.placeRoomsInArea(getDefaultRooms(), DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                    DEFAULT_ROOM_COUNT, DEFAULT_COLUMN_COUNT, tileSize);
        });
    }

    @Test
    void testPlaceRoomsInAreaThrowsRoomsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
        	List<Room> rooms = null;
            dungeonGenerator.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                    DEFAULT_ROOM_COUNT, DEFAULT_COLUMN_COUNT, DungeonGenerator.MIN_TILE_SIZE);
        });
    }

    @Test
    void testPlaceRoomsInAreaDependencyInjection() {
        List<Room> rooms = getDefaultRooms();
        double randomMultiplier = 0.3;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));
        int expected = 1;
        assertThat(getDefaultPlacedRooms(rooms), hasSize(expected));
    }

    private List<Room> getDefaultPlacedRooms(List<Room> rooms) {
        return dungeonGenerator.placeRoomsInArea(rooms,
                DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT, DungeonGenerator.MIN_TILE_SIZE);
    }

    @ParameterizedTest
    @CsvSource(value = { "31, 31", "61, 61" })
    void testPlaceRoomsInAreaIfAreaIsFilled(int rowCount, int columnCount) {
        // Rooms kommer vara 10 X 10
        // De tar alltså upp 3 X 3 tiles
        // 1 extra rad och column för border
        // Det får då plats 10 X 10 rum i utrymmet som har 31 X 31 tiles med storlek
        // 10.0(Map.TILE_SIZE)
        // Testet fyller ut hela grid:en med rum
        int extraForBorder = 2;
        int roomsInX = rowCount / ((int) (DEFAULT_MIN_WIDTH / DungeonGenerator.MIN_TILE_SIZE) + extraForBorder);
        int roomsInY = columnCount / ((int) (DEFAULT_MIN_HEIGHT / DungeonGenerator.MIN_TILE_SIZE) + extraForBorder);

        double randomMultiplier = 0.0;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = (roomsInX * roomsInY);
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
        dungeonGenerator.setRandom(new RandomInternal(randomMultipliersForAreaIsFilled(roomCount, roomsInX, roomsInY)));

        List<Room> placedRooms = dungeonGenerator.placeRoomsInArea(rooms, 1, rowCount, columnCount,
                DungeonGenerator.MIN_TILE_SIZE);

        assertThat(rooms, is(equalTo(placedRooms)));
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
    void testPlaceRoomsInAreaMultipleTriesIfRoomNotFit() {
        double randomMultiplier = 0.0;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));
        int roomCount = 2;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        dungeonGenerator.setRandom(new RandomInternal(
                randomMultipliersForMultipleTries(roomCount, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD)));

        assertThat(getDefaultPlacedRooms(rooms), hasSize(roomCount));
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
            for (int i = 0; i < numberOfTriesBeforeDiscard - 1; i++) {
                randomMultipliers[index++] = 0.0;
                randomMultipliers[index++] = 0.0;
            }
            randomMultipliers[index++] = (double) x / ((double) roomCount - 1.0);
            randomMultipliers[index++] = 0.0;
        }
        return randomMultipliers;
    }

    @Test
    void testPlaceRoomsInArea() {
        checkIfRoomsHaveCorrectId(getDefaultPlacedRooms(getDefaultRooms()),
                dungeonGenerator.getCopyOfGrid());
    }

    private void checkIfRoomsHaveCorrectId(List<Room> rooms, Grid grid) {
        for (int expected = 0; expected < rooms.size(); expected++) {
            Room room = rooms.get(expected);
            RoomParser roomParser = new RoomParser(grid, room);
            List<Integer> roomTileList = roomParser.roomAreaToList();
            assertThat(roomTileList, containsOnly(expected));
        }
    }

    @Test
    void testPlaceRoomsInAreaEmptyTilesAroundRoom() {
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        Grid grid = dungeonGenerator.getCopyOfGrid();
        RoomParser roomParser = new RoomParser(grid);
        for (Room room : placedRooms) {
            roomParser.setRoom(room);
            GridIndex startIndex = roomParser.getRoomStartIndex();
            GridIndex endIndex = roomParser.getRoomEndIndex();
            checkTilesAboveRoom(grid, startIndex, endIndex.column);
            checkTilesBelowRoom(grid, endIndex, startIndex.column);
            checkTilesToLeftOfRoom(grid, startIndex, endIndex.row);
            checkTilesToRightOfRoom(grid, endIndex, startIndex.row);
        }

    }

    private void checkTilesAboveRoom(Grid grid, GridIndex startIndex, int endColumn) {
        GridIndex i = new GridIndex(startIndex);
        for (i.row -= 1, i.column -= 1; i.column <= endColumn; i.column++) {
            assertEquals(Grid.BORDER_VALUE, grid.getTile(i));
        }
    }

    private void checkTilesBelowRoom(Grid grid, GridIndex endIndex, int startColumn) {
        GridIndex i = new GridIndex(endIndex);
        for (i.row += 1, i.column += 1; i.column >= startColumn; i.column--) {
            assertEquals(Grid.BORDER_VALUE, grid.getTile(i));
        }
    }

    private void checkTilesToLeftOfRoom(Grid grid, GridIndex startIndex, int endRow) {
        GridIndex i = new GridIndex(startIndex);
        for (i.column -= 1; i.row <= endRow + 1; i.row++) {
            assertEquals(Grid.BORDER_VALUE, grid.getTile(i));
        }
    }

    private void checkTilesToRightOfRoom(Grid grid, GridIndex endIndex, int startRow) {
        GridIndex i = new GridIndex(endIndex);
        for (i.column += 1; i.row >= startRow + 1; i.row--) {
            assertEquals(Grid.BORDER_VALUE, grid.getTile(i));
        }
    }

    @Test
    void testPlaceRoomsInAreaOutOfBounds() {
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        Grid grid = dungeonGenerator.getCopyOfGrid();
        for (Room room : placedRooms) {
            boolean expected = (room.getPosition().getX() + room.getWidth()) < grid.getWidth() &&
                    (room.getPosition().getY() + room.getHeight()) < grid.getHeight();

            assertTrue(expected);
        }

    }

    @Test
    void testGridHasBorder() {
        int roomCount = 1;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);
        getDefaultPlacedRooms(rooms);

        assertTrue(dungeonGenerator.getCopyOfGrid().hasBorder());
    }

    @Test
    void testConnectRoomsAllConnected() {
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        dungeonGenerator.connectRooms(placedRooms);
        for (Room room : placedRooms) {
            assertTrue(room.isConnected());
        }
    }

    // För att minimera chansen att något rum kopplar samman något annat så görs
    // gridden större samt att testet körs flera gånger
    @RepeatedTest(value = 10)
    void testConnectRoomsDepthFirst() {
        int columns = 160;
        int rows = 160;
        List<Room> placedRooms = dungeonGenerator.placeRoomsInArea(getDefaultRooms(),
                DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD, rows, columns, DungeonGenerator.MIN_TILE_SIZE);
        dungeonGenerator.connectRooms(placedRooms);

        int[] connectedRooms = dungeonGenerator.getConnectedRooms(placedRooms, dungeonGenerator.getCopyOfGrid());
        assertThat(connectedRooms, is(equalTo(getArrayOfIncrementedIntegers(placedRooms.size()))));
    }

    private static int[] getArrayOfIncrementedIntegers(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = i;
        }
        return result;
    }

    @Test
    void testConnectRooms() {
        double[] randomMultipliers = { 0.0, 0.0, 0.5, 0.0, 0.0, 0.5 };
        List<Room> placedRooms = setupThreePreKnownRooms(randomMultipliers);
        dungeonGenerator.connectRooms(placedRooms);
        Grid grid = dungeonGenerator.getCopyOfGrid();

        GridIndex firstRoomGriddIndex = grid.getGriddIndexBasedOnPosition(placedRooms.get(0).getPosition());
        GridIndex secondRoomGriddIndex = grid.getGriddIndexBasedOnPosition(placedRooms.get(1).getPosition());
        GridIndex thirdRoomGriddIndex = grid.getGriddIndexBasedOnPosition(placedRooms.get(2).getPosition());

        for (; firstRoomGriddIndex.column <= secondRoomGriddIndex.column; firstRoomGriddIndex.column++) {
            assertThat(grid.getTile(firstRoomGriddIndex), greaterThanOrEqualTo(0));
        }
        for (; secondRoomGriddIndex.row <= thirdRoomGriddIndex.row; secondRoomGriddIndex.row++) {
            assertThat(grid.getTile(secondRoomGriddIndex), greaterThanOrEqualTo(0));
        }
        for (secondRoomGriddIndex.row--; secondRoomGriddIndex.column >= thirdRoomGriddIndex.column; secondRoomGriddIndex.column--) {
            assertThat(grid.getTile(secondRoomGriddIndex), greaterThanOrEqualTo(0));
        }
    }

    private List<Room> setupThreePreKnownRooms(double[] randomRoomPlacementMultipliers) {
        double randomMultiplier = 1.0;
        dungeonGenerator.setRandom(new RandomInternal(randomMultiplier));

        int roomCount = 3;
        List<Room> rooms = dungeonGenerator.generateListOfRooms(roomCount, DEFAULT_MIN_WIDTH, DEFAULT_MAX_WIDTH,
                DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT);

        dungeonGenerator.setRandom(new RandomInternal(randomRoomPlacementMultipliers));
        List<Room> placedRooms = dungeonGenerator.placeRoomsInArea(rooms, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT, DungeonGenerator.MIN_TILE_SIZE);
        return placedRooms;
    }

    @Test
    void testConnectRoomsAllRoomsStillHaveId() {
        List<Room> placedRooms = getDefaultPlacedRooms(getDefaultRooms());
        dungeonGenerator.connectRooms(placedRooms);
        Grid grid = dungeonGenerator.getCopyOfGrid();
        checkIfRoomsHaveCorrectId(placedRooms, grid);
    }

    @Test
    void testConnectRoomsNoUnnessisaryCorridors() {
        double[] randomMultipliers = { 0.5, 0.0, 0.5, 1.0, 0.45, 0.5 };
        List<Room> placedRooms = setupThreePreKnownRooms(randomMultipliers);
        int expectedCorridorCount = 1;
        assertEquals(expectedCorridorCount, dungeonGenerator.connectRooms(placedRooms));
    }

    // Platserar ett rum för varje tile i gridden och ser att getConnectedRooms
    // kan hitta alla rum
    @Test
    void testGetConnectedRooms() {
        Grid grid = new Grid(DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT, DungeonGenerator.MIN_TILE_SIZE);
        grid.setBorder();
        List<Room> placedRooms = fillGridWithRooms(grid);
        int[] connectedRooms = dungeonGenerator.getConnectedRooms(placedRooms, grid);
        assertArrayEquals(getArrayOfIncrementedIntegers(placedRooms.size()), connectedRooms);
    }

    private List<Room> fillGridWithRooms(Grid grid) {
        // - 1 för att gridden har en border
        int rowCount = DEFAULT_ROW_COUNT - 1;
        int columnCount = DEFAULT_COLUMN_COUNT - 1;
        ArrayList<Room> placedRooms = new ArrayList<>(rowCount * columnCount);
        GridIndex i = new GridIndex();
        // = 1 för bordern också
        for (i.row = 1; i.row < rowCount; i.row++) {
            for (i.column = 1; i.column < columnCount; i.column++) {
                grid.setTile(i, placedRooms.size());
                Room room = new Room(DungeonGenerator.MIN_TILE_SIZE, DungeonGenerator.MIN_TILE_SIZE);
                room.setPosition((double) i.column * DungeonGenerator.MIN_TILE_SIZE,
                        (double) i.row * DungeonGenerator.MIN_TILE_SIZE);
                placedRooms.add(room);
            }
        }
        return placedRooms;
    }

    @Test
    void testGetConnectedRoomsFail() {
        double[] randomMultipliers = { 0.0, 0.0, 0.5, 0.0, 0.0, 0.5 };
        List<Room> placedRooms = setupThreePreKnownRooms(randomMultipliers);
        Grid grid = dungeonGenerator.getCopyOfGrid();

        GridIndex firstRoomGriddIndex = grid.getGriddIndexBasedOnPosition(placedRooms.get(0).getPosition());
        GridIndex secondRoomGriddIndex = grid.getGriddIndexBasedOnPosition(placedRooms.get(1).getPosition());
        for (; firstRoomGriddIndex.column <= secondRoomGriddIndex.column; firstRoomGriddIndex.column++) {
            grid.setTile(firstRoomGriddIndex, placedRooms.size());
        }
        int[] expected = { 0, 1, -1 };
        assertThat(dungeonGenerator.getConnectedRooms(placedRooms, grid), is(equalTo(expected)));
    }

    @Test
    void testGetConnectedRoomsThrowsWithBorderlessGridd() {
        assertThrows(IllegalArgumentException.class, () -> {
            dungeonGenerator.getConnectedRooms(new ArrayList<Room>(),
                    new Grid(DEFAULT_ROOM_COUNT, DEFAULT_COLUMN_COUNT, DungeonGenerator.MIN_TILE_SIZE));
        });
    }

    @Test
    void testGenerateDungeonReturnsUnModifiedList() {
        List<Room> placedRooms = getDefaultGenerateDungeon();
        assertThrows(UnsupportedOperationException.class, () -> {
            placedRooms.set(0, placedRooms.get(1));
        });
    }

    @Test
    void testGenerateDungeonWidthAndHeightOfRoomsMultipleOfTileSize() {
        List<Room> placedRooms = getDefaultGenerateDungeon();
        double expected = 0.0;
        for (Room room : placedRooms) {
            assertEquals(expected, room.getWidth() % DungeonGenerator.MIN_TILE_SIZE, ERROR_ACCEPTANCE);
            assertEquals(expected, room.getHeight() % DungeonGenerator.MIN_TILE_SIZE, ERROR_ACCEPTANCE);
        }
    }

    @Test
    void testGenerateDungeonPositionInCornerOfTile() {
        List<Room> placedRooms = getDefaultGenerateDungeon();
        double expected = 0.0;
        for (Room room : placedRooms) {
            Point2D position = room.getPosition();
            assertEquals(expected, position.getX() % DungeonGenerator.MIN_TILE_SIZE, ERROR_ACCEPTANCE);
            assertEquals(expected, position.getY() % DungeonGenerator.MIN_TILE_SIZE, ERROR_ACCEPTANCE);
        }
    }

    private List<Room> getDefaultGenerateDungeon() {
        return dungeonGenerator.generateDungeon(DEFAULT_ROOM_COUNT, DEFAULT_MIN_WIDTH,
                DEFAULT_MAX_WIDTH, DEFAULT_MIN_HEIGHT, DEFAULT_MAX_HEIGHT, DEFAULT_NUMBER_OF_TRIES_BEFORE_DISCARD,
                DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT, DungeonGenerator.MIN_TILE_SIZE);
    }

}
