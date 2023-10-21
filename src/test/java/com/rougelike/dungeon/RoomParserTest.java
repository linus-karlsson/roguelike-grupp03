package com.rougelike.dungeon;

import org.junit.jupiter.api.*;

import com.rougelike.Point2D;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class RoomParserTest {
    double tileSize = 10.0;
    Grid grid = new Grid(10, 10, tileSize);
    Room room = new Room(10.0, 10.0); // 3 x 3 room
    RoomParser roomParser = new RoomParser(grid);

    @Test
    void testResetRoom() {
        roomParser.setRoom(room);
        roomParser.resetRoom();
        GridIndex expected = roomParser.getRoomStartIndex();
        GridIndex index = roomParser.nextIndex();
        assertEquals(expected.column, index.column);
        assertEquals(expected.row, index.row);
    }

    @Test
    void testSetRoomStartIndex() {
        roomParser.setRoom(room);
        GridIndex startIndex = roomParser.getRoomStartIndex();
        GridIndex expected = new GridIndex(grid.getGriddIndexBasedOnPosition(room.getPosition()));
        assertEquals(expected.column, startIndex.column);
        assertEquals(expected.row, startIndex.row);
    }

    @Test
    void testSetRoomEndIndex() {
        roomParser.setRoom(room);
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
        roomParser.setRoom(room);
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
        roomParser.setRoom(room);
        int cellCountInX = roomParser.getRoomTileCountInX();
        int expected = (int) (room.getWidth() / tileSize) + 1;
        assertEquals(expected, cellCountInX);
    }

    @Test
    void testGriddParserGetRoomCellCountInY() {
        roomParser.setRoom(room);
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
    void testRoomAreaToList() {
        List<Integer> expected = setUpExpectedIntegerList();
        List<Integer> roomTileList = roomParser.roomAreaToList();
        assertTrue(roomTileList.equals(expected), roomTileList.toString());
    }

    @Test
    void testRoomAreaToListSize() {
        List<Integer> expected = setUpExpectedIntegerList();
        List<Integer> roomTileList = roomParser.roomAreaToList();
        assertTrue(roomTileList.size() == expected.size());
    }

    private List<Integer> setUpExpectedIntegerList() {
        int roomId = 3;
        room.setId(roomId);
        roomParser.setRoom(room);
        roomParser.placeRoomInGridd();
        List<Integer> expected = new ArrayList<>(roomParser.getRoomTileCountInY() * roomParser.getRoomTileCountInX());
        for (int row = 0; row < roomParser.getRoomTileCountInY(); row++) {
            for (int column = 0; column < roomParser.getRoomTileCountInX(); column++) {
                expected.add(roomId);
            }
        }
        return expected;
    }
}
