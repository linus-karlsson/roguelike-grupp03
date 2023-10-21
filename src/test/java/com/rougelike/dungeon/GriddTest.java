package com.rougelike.dungeon;

import org.junit.jupiter.api.*;

import com.rougelike.Point2D;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class GriddTest {
    double tileSize = 5.0;
    Grid gridd = new Grid(10, 10, tileSize);
    Room room = new Room(10.0, 10.0); // 3 x 3 room

    @Test
    void testHasNextIndex() {
        int tilesInX = 3;
        int tilesInY = 4;
        room = new Room(tileSize * (tilesInX - 1), tileSize * (tilesInY - 1));
        gridd.getRoomParser().setRoom(room);
        for (int row = 0; row < tilesInY; row++) {
            for (int column = 0; column < tilesInX; column++) {
                assertTrue(gridd.getRoomParser().hasNextIndex());
                gridd.getRoomParser().nextIndex();
            }
        }
        assertFalse(gridd.getRoomParser().hasNextIndex());
    }

    @Test
    void testNextIndex() {
        int tilesInX = 3;
        int tilesInY = 4;
        room = new Room(tileSize * (tilesInX - 1), tileSize * (tilesInY - 1));
        gridd.getRoomParser().setRoom(room);
        for (int row = 0; row < tilesInY; row++) {
            for (int column = 0; column < tilesInX; column++) {
                Grid.Index index = gridd.getRoomParser().nextIndex();
                assertEquals(index.row, row);
                assertEquals(index.column, column);
            }
        }
    }

    @Test
    void testRoomAreaToList() {
        room.setId(3);
        gridd.getRoomParser().setRoom(room);
        gridd.getRoomParser().placeRoomInGridd();
        ArrayList<Integer> roomTileList = gridd.getRoomParser().roomAreaToList();
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3));
        assertTrue(roomTileList.equals(expected), roomTileList.toString());
    }

    @Test
    void testRoomAreaToListSize() {
        Room room = new Room(10.0, 10.0); // 3 x 3 room
        room.setId(3);
        gridd.getRoomParser().setRoom(room);
        gridd.getRoomParser().placeRoomInGridd();
        ArrayList<Integer> roomTileList = gridd.getRoomParser().roomAreaToList();
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3));
        assertTrue(roomTileList.size() == expected.size());
    }

    @Test
    void testGetGriddIndexBasedOnPosition() {
        int cellsInX = 3;
        int cellsInY = 2;
        Point2D point = new Point2D(tileSize * cellsInX, tileSize * cellsInY);
        Grid.Index expected = gridd.new Index(cellsInY, cellsInX);
        Grid.Index griddIndex = gridd.getGriddIndexBasedOnPosition(point);
        assertTrue(expected.equals(griddIndex));
    }

    @Test
    void testGriddParserGetRoomCellCountInX() {
        Room room = new Room(10.0, 10.0);
        gridd.getRoomParser().setRoom(room);
        int cellCountInX = gridd.getRoomParser().getRoomTileCountInX();
        assertEquals(3, cellCountInX);
    }

    @Test
    void testGriddParserGetRoomCellCountInY() {
        Room room = new Room(10.0, 10.0);
        gridd.getRoomParser().setRoom(room);
        int cellCountInY = gridd.getRoomParser().getRoomTileCountInY();
        assertEquals(3, cellCountInY);

    }

    @Test
    void testSetBorder() {
        gridd.setBorder();
        int expected = Grid.BORDER_VALUE;
        int lastRow = gridd.getRowCount() - 1;
        for (int column = 0; column < gridd.getColumnCount(); column++) {
            assertEquals(expected, gridd.getTile(0, column));
            assertEquals(expected, gridd.getTile(lastRow, column));
        }
        int lastColumn = gridd.getColumnCount() - 1;
        for (int row = 0; row < gridd.getRowCount(); row++) {
            assertEquals(expected, gridd.getTile(row, 0));
            assertEquals(expected, gridd.getTile(row, lastColumn));
        }
    }

    @Test
    void testHasBorder() {
        gridd.setBorder();
        assertTrue(gridd.hasBorder());
    }

    @Test
    void testHasNotBorder() {
        assertFalse(gridd.hasBorder());
    }
}
