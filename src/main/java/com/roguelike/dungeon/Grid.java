package com.roguelike.dungeon;

import com.roguelike.Point2D;

public class Grid {

    public static final int BORDER_VALUE = -2;

    private int[][] tiles;
    private double tileSize;

    public Grid(int rowCount, int columnCount, double tileSize) {
        tiles = new int[rowCount][columnCount];
        this.tileSize = tileSize;
    }

    public int getRowCount() {
        return tiles.length;
    }

    public int getColumnCount() {
        return tiles[0].length;
    }

    public int getTile(int row, int column) {
        return tiles[row][column];
    }

    public int getTile(GridIndex index) {
        return getTile(index.row, index.column);
    }

    public void setTile(int row, int column, int value) {
        tiles[row][column] = value;
    }

    public void setTile(GridIndex index, int value) {
        setTile(index.row, index.column, value);
    }

    public double getTileSize() {
        return tileSize;
    }

    public double getWidth() {
        return getColumnCount() * getTileSize();
    }

    public double getHeight() {
        return getRowCount() * getTileSize();
    }

    public GridIndex getGriddIndexBasedOnPosition(Point2D position) {
        return new GridIndex(getPositionTileCountInY(position), getPositionTileCountInX(position));
    }

    private int getPositionTileCountInY(Point2D position) {
        return (int) (position.getY() / tileSize);
    }

    private int getPositionTileCountInX(Point2D position) {
        return (int) (position.getX() / tileSize);
    }

    public void fillWithValue(int value) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                setTile(row, column, value);
            }
        }
    }

    public void setBorder() {
        int lastRow = getRowCount() - 1;
        for (int column = 0; column < getColumnCount(); column++) {
            setTile(0, column, Grid.BORDER_VALUE);
            setTile(lastRow, column, Grid.BORDER_VALUE);
        }
        int lastColumn = getColumnCount() - 1;
        for (int row = 0; row < getRowCount(); row++) {
            setTile(row, 0, Grid.BORDER_VALUE);
            setTile(row, lastColumn, Grid.BORDER_VALUE);
        }
    }

    public boolean hasBorder() {
        int lastRow = getRowCount() - 1;
        for (int column = 0; column < getColumnCount(); column++) {
            if (getTile(0, column) != Grid.BORDER_VALUE
                    || getTile(lastRow, column) != Grid.BORDER_VALUE) {
                return false;
            }

        }
        int lastColumn = getColumnCount() - 1;
        for (int row = 0; row < getRowCount(); row++) {
            if (getTile(row, 0) != Grid.BORDER_VALUE
                    || getTile(row, lastColumn) != Grid.BORDER_VALUE) {
                return false;
            }

        }
        return true;
    }

}
