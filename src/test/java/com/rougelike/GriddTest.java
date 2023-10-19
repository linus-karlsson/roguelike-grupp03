package com.rougelike;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class GriddTest {
    double tileSize = 5.0;
    Gridd gridd = new Gridd(10, 10, tileSize);
    Room room = new Room(10.0, 10.0); // 3 x 3 room

    @Test
    public void testHasNextIndex() {
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
    public void testNextIndex() {
        int tilesInX = 3;
        int tilesInY = 4;
        room = new Room(tileSize * (tilesInX - 1), tileSize * (tilesInY - 1));
        gridd.getRoomParser().setRoom(room);
        for (int row = 0; row < tilesInY; row++) {
            for (int column = 0; column < tilesInX; column++) {
                Gridd.Index index = gridd.getRoomParser().nextIndex();
                assertEquals(index.row, row);
                assertEquals(index.column, column);
            }
        }
    }

    @Test
    public void testRoomAreaToList() {
        room.setId(3);
        gridd.getRoomParser().setRoom(room);
        gridd.getRoomParser().placeRoomInGridd();
        ArrayList<Integer> roomTileList = gridd.getRoomParser().roomAreaToList();
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3));
        assertTrue(roomTileList.equals(expected), roomTileList.toString());
    }

    @Test
    public void testRoomAreaToListSize() {
        Room room = new Room(10.0, 10.0); // 3 x 3 room
        room.setId(3);
        gridd.getRoomParser().setRoom(room);
        gridd.getRoomParser().placeRoomInGridd();
        ArrayList<Integer> roomTileList = gridd.getRoomParser().roomAreaToList();
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3));
        assertTrue(roomTileList.size() == expected.size());
    }

    @Test
    public void testGetGriddIndexBasedOnPosition() {
        int cellsInX = 3;
        int cellsInY = 2;
        Point point = new Point(tileSize * cellsInX, tileSize * cellsInY);
        Gridd.Index expected = gridd.new Index(cellsInY, cellsInX);
        Gridd.Index griddIndex = gridd.getGriddIndexBasedOnPosition(point);
        assertTrue(expected.equals(griddIndex));
    }

    @Test
    public void testGriddParserGetRoomCellCountInX() {
        Room room = new Room(10.0, 10.0);
        gridd.getRoomParser().setRoom(room);
        int cellCountInX = gridd.getRoomParser().getRoomTileCountInX();
        assertEquals(3, cellCountInX);
    }

    @Test
    public void testGriddParserGetRoomCellCountInY() {
        Room room = new Room(10.0, 10.0);
        gridd.getRoomParser().setRoom(room);
        int cellCountInY = gridd.getRoomParser().getRoomTileCountInY();
        assertEquals(3, cellCountInY);

    }

    @Test
    public void testSetBorder() {
        gridd.setBorder();
        int expected = Gridd.BORDER_VALUE;
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
    public void testHasBorder() {
        gridd.setBorder();
        assertTrue(gridd.hasBorder());
    }

    @Test
    public void testHasNotBorder() {
        assertFalse(gridd.hasBorder());
    }
}
