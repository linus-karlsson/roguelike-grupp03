package com.rougelike.dungeon;

import org.junit.jupiter.api.*;
import com.rougelike.Point2D;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {
    double tileSize = 5.0;
    Grid grid = new Grid(10, 10, tileSize);

    @Test
    void testGetGriddIndexBasedOnPosition() {
        int cellsInX = 3;
        int cellsInY = 2;
        Point2D point = new Point2D(tileSize * cellsInX, tileSize * cellsInY);
        GridIndex expected = new GridIndex(cellsInY, cellsInX);
        GridIndex griddIndex = grid.getGriddIndexBasedOnPosition(point);
        assertTrue(expected.equals(griddIndex));
    }

    @Test
    void testSetBorder() {
        grid.setBorder();
        int expected = Grid.BORDER_VALUE;
        int lastRow = grid.getRowCount() - 1;
        for (int column = 0; column < grid.getColumnCount(); column++) {
            assertEquals(expected, grid.getTile(0, column));
            assertEquals(expected, grid.getTile(lastRow, column));
        }
        int lastColumn = grid.getColumnCount() - 1;
        for (int row = 0; row < grid.getRowCount(); row++) {
            assertEquals(expected, grid.getTile(row, 0));
            assertEquals(expected, grid.getTile(row, lastColumn));
        }
    }

    @Test
    void testHasBorder() {
        grid.setBorder();
        assertTrue(grid.hasBorder());
    }

    @Test
    void testHasNotBorder() {
        assertFalse(grid.hasBorder());
    }
}
