package com.rougelike.dungeon;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomParserTest {
    double tileSize = 5.0;
    Grid grid = new Grid(10, 10, tileSize);
    Room room = new Room(10.0, 10.0); // 3 x 3 room
    RoomParser roomParser = new RoomParser(grid);

    @Test
    void testGriddParserGetRoomCellCountInX() {
        Room room = new Room(10.0, 10.0);
        roomParser.setRoom(room);
        int cellCountInX = roomParser.getRoomTileCountInX();
        assertEquals(3, cellCountInX);
    }

    @Test
    void testGriddParserGetRoomCellCountInY() {
        Room room = new Room(10.0, 10.0);
        roomParser.setRoom(room);
        int cellCountInY = roomParser.getRoomTileCountInY();
        assertEquals(3, cellCountInY);

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
                Grid.Index index = roomParser.nextIndex();
                assertEquals(index.row, row);
                assertEquals(index.column, column);
            }
        }
    }

    @Test
    void testRoomAreaToList() {
        room.setId(3);
        roomParser.setRoom(room);
        roomParser.placeRoomInGridd();
        List<Integer> roomTileList = roomParser.roomAreaToList();
        List<Integer> expected = new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3));
        assertTrue(roomTileList.equals(expected), roomTileList.toString());
    }

    @Test
    void testRoomAreaToListSize() {
        Room room = new Room(10.0, 10.0); // 3 x 3 room
        room.setId(3);
        roomParser.setRoom(room);
        roomParser.placeRoomInGridd();
        List<Integer> roomTileList = roomParser.roomAreaToList();
        List<Integer> expected = new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3));
        assertTrue(roomTileList.size() == expected.size());
    }

}
