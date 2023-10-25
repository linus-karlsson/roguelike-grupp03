package com.roguelike.dungeon;

import org.junit.jupiter.api.*;

import com.roguelike.Point2D;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {
    double tileSize = 5.0;
    Grid grid = new Grid(20, 10, tileSize);

    @Test
    void testGetRowCount() {
        int columnCount = 20;
        int rowCount = 10;
        grid = new Grid(rowCount, columnCount, tileSize);
        assertEquals(rowCount, grid.getRowCount());
    }

    @Test
    void testGetColumnCount() {
        int columnCount = 20;
        int rowCount = 10;
        grid = new Grid(rowCount, columnCount, tileSize);
        assertEquals(columnCount, grid.getColumnCount());
    }

    @Test
    void testSetTileUsingGriddIndex() {
        int row = 3;
        int column = 6;
        GridIndex index = new GridIndex(row, column);
        int expected = 30;
        grid.setTile(row, column, expected);
        expected = grid.getTile(row, column);
        grid.setTile(index, expected);
        assertEquals(expected, grid.getTile(row, column));
    }

    @Test
    void testGetTileUsingGriddIndex() {
        int row = 3;
        int column = 6;
        GridIndex index = new GridIndex(row, column);
        int expected = 30;
        grid.setTile(index, expected);
        assertEquals(expected, grid.getTile(index));
    }

    @Test
    void testGetTileSize() {
        double expected = tileSize;
        assertEquals(expected, grid.getTileSize());
    }

    @Test
    void testGetWidth() {
        double expected = tileSize * grid.getColumnCount();
        assertEquals(expected, grid.getWidth());
    }

    @Test
    void testGetHeight() {
        double expected = tileSize * grid.getRowCount();
        assertEquals(expected, grid.getHeight());
    }

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
    void testFillWithValue() {
        int expected = -1;
        grid.fillWithValue(expected);
        for (int row = 0; row < grid.getRowCount(); row++) {
            for (int column = 0; column < grid.getColumnCount(); column++) {
                assertEquals(expected, grid.getTile(row, column));
            }
        }
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
    void testHasBorderMissingFirstColumn() {
        grid.setBorder();
        setWholeColumn(0);
        assertFalse(grid.hasBorder());
    }

    @Test
    void testHasBorderMissingLastColumn() {
        grid.setBorder();
        int lastColumn = grid.getColumnCount() - 1;
        setWholeColumn(lastColumn);
        assertFalse(grid.hasBorder());
    }

    private void setWholeColumn(int column) {
        for (int row = 1; row < grid.getRowCount() - 1; row++) {
            grid.setTile(row, column, 0);
        }
    }

    @Test
    void testHasBorderMissingFirstRow() {
        grid.setBorder();
        setWholeRow(0);
        assertFalse(grid.hasBorder());
    }

    @Test
    void testHasBorderMissingLastRow() {
        grid.setBorder();
        int lastRow = grid.getRowCount() - 1;
        setWholeRow(lastRow);
        assertFalse(grid.hasBorder());
    }

    private void setWholeRow(int row) {
        for (int column = 1; column < grid.getColumnCount() - 1; column++) {
            grid.setTile(row, column, 0);
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
