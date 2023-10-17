package com.rougelike;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class GriddTest {

    @Test
    public void testGetGriddIndexBasedOnPosition() {
        double tileSize = 10.0;
        Point point = new Point();
        int cellsInX = 3;
        int cellsInY = 2;
        point.setX(tileSize * cellsInX);
        point.setY(tileSize * cellsInY);
        int expectedRow = cellsInY;
        int expectedColumn = cellsInX;
        Gridd gridd = new Gridd(10, 10, tileSize);
        Gridd.Index griddIndex = gridd.getGriddIndexBasedOnPosition(point);
        assertEquals(expectedRow, griddIndex.row);
        assertEquals(expectedColumn, griddIndex.column);
    }

    @Test
    public void testGriddParserGetRoomCellCountInX() {
        Room room = new Room(10.0, 10.0);

        double tileSize = 5.0;
        int columns = 80;
        Gridd gridd = new Gridd(columns, columns, tileSize);
        gridd.getRoomParser().setRoom(room);
        int cellCountInX = gridd.getRoomParser().getRoomTileCountInX();

        assertEquals(3, cellCountInX);
    }

    @Test
    public void testGriddParserGetRoomCellCountInY() {
        Room room = new Room(10.0, 10.0);

        double tileSize = 5.0;
        int columns = 80;
        Gridd gridd = new Gridd(columns, columns, tileSize);
        gridd.getRoomParser().setRoom(room);
        int cellCountInY = gridd.getRoomParser().getRoomTileCountInY();

        assertEquals(3, cellCountInY);

    }
}
