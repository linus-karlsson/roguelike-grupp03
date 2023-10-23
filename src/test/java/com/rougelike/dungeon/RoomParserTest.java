package com.rougelike.dungeon;

import org.junit.jupiter.api.*;

import com.rougelike.Point2D;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class RoomParserTest {
    double tileSize = 10.0;
    Grid grid = new Grid(10, 10, tileSize);
    Room room = new Room(10.0, 10.0); // 3 x 3 room
    RoomParser roomParser = new RoomParser(grid, room);

    @Test
    void testConstructorOnlyPassingGrid() {
        RoomParser roomParser1 = new RoomParser(grid);
        Room expected = null;
        assertEquals(expected, roomParser1.getCurrentRoom());
    }

    @Test
    void testResetRoom() {
        roomParser.resetRoom();
        GridIndex expected = roomParser.getRoomStartIndex();
        GridIndex index = roomParser.nextIndex();
        assertEquals(expected.column, index.column);
        assertEquals(expected.row, index.row);
    }

    @Test
    void testSetRoomStartIndex() {
        GridIndex startIndex = roomParser.getRoomStartIndex();
        GridIndex expected = new GridIndex(grid.getGriddIndexBasedOnPosition(room.getPosition()));
        assertEquals(expected.column, startIndex.column);
        assertEquals(expected.row, startIndex.row);
    }

    @Test
    void testSetRoomEndIndex() {
        GridIndex endIndex = roomParser.getRoomEndIndex();
        Point2D endPosition = room.getPosition();
        endPosition.setX(endPosition.getX() + room.getWidth());
        endPosition.setY(endPosition.getY() + room.getHeight());
        GridIndex expected = new GridIndex(grid.getGriddIndexBasedOnPosition(endPosition));
        assertEquals(expected.column, endIndex.column);
        assertEquals(expected.row, endIndex.row);
    }

    @Test
    void testSetRoomIndex() {
        GridIndex index = roomParser.nextIndex();
        GridIndex expected = roomParser.getRoomStartIndex();
        assertEquals(expected.column, index.column);
        assertEquals(expected.row, index.row);
    }

    @Test
    void testSetRoomThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            roomParser.setRoom(null);
        });
    }

    @Test
    void testGriddParserGetRoomCellCountInX() {
        int cellCountInX = roomParser.getRoomTileCountInX();
        int expected = (int) (room.getWidth() / tileSize) + 1;
        assertEquals(expected, cellCountInX);
    }

    @Test
    void testGriddParserGetRoomCellCountInY() {
        int cellCountInY = roomParser.getRoomTileCountInY();
        int expected = (int) (room.getHeight() / tileSize) + 1;
        assertEquals(expected, cellCountInY);

    }

    @Test
    void testHasNextIndex() {
        int tilesInX = 3;
        int tilesInY = 4;
        setUpRoomParser(tilesInX, tilesInY);
        for (int row = 0; row < tilesInY; row++) {
            for (int column = 0; column < tilesInX; column++) {
                assertTrue(roomParser.hasNextIndex());
                roomParser.nextIndex();
            }
        }
        assertFalse(roomParser.hasNextIndex());
    }

    private void setUpRoomParser(int tilesInX, int tilesInY) {
        room = new Room(tileSize * (tilesInX - 1), tileSize * (tilesInY - 1));
        roomParser.setRoom(room);
    }

    @Test
    void testNextIndex() {
        int tilesInX = 3;
        int tilesInY = 4;
        setUpRoomParser(tilesInX, tilesInY);
        for (int row = 0; row < tilesInY; row++) {
            for (int column = 0; column < tilesInX; column++) {
                GridIndex index = roomParser.nextIndex();
                assertEquals(index.row, row);
                assertEquals(index.column, column);
            }
        }
    }

    @Test
    void testNextIndexThrows() {
        while (roomParser.hasNextIndex()) {
            roomParser.nextIndex();
        }
        assertThrows(IllegalAccessError.class, () -> {
            roomParser.nextIndex();
        });
    }

    @Test
    void testRoomAreaToList() {
        List<Integer> expected = setUpExpectedIntegerList();
        List<Integer> roomTileList = roomParser.roomAreaToList();
        assertArrayEquals(expected.toArray(), roomTileList.toArray());
    }

    private List<Integer> setUpExpectedIntegerList() {
        int roomId = 3;
        room.setId(roomId);
        roomParser.placeRoomInGridd();
        List<Integer> expected = new ArrayList<>(roomParser.getRoomTileCountInY() * roomParser.getRoomTileCountInX());
        for (int row = 0; row < roomParser.getRoomTileCountInY(); row++) {
            for (int column = 0; column < roomParser.getRoomTileCountInX(); column++) {
                expected.add(roomId);
            }
        }
        return expected;
    }

    @Test
    void testPlaceRoomsInAreaEmptyTilesAroundRoom() {
        room.setPosition(tileSize, tileSize);
        roomParser.setRoom(room);
        roomParser.setRoomBorder();
        GridIndex startIndex = roomParser.getRoomStartIndex();
        GridIndex endIndex = roomParser.getRoomEndIndex();
        checkTilesAboveRoom(grid, startIndex, endIndex.column);
        checkTilesBelowRoom(grid, endIndex, startIndex.column);
        checkTilesToLeftOfRoom(grid, startIndex, endIndex.row);
        checkTilesToRightOfRoom(grid, endIndex, startIndex.row);

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
}
