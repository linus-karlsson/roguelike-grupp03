package com.rougelike.dungeon;

public class GridIndex implements Comparable<GridIndex> {
    public int row;
    public int column;

    public GridIndex() {
        row = 0;
        column = 0;
    }

    public GridIndex(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public GridIndex(GridIndex otherIndex) {
        row = otherIndex.row;
        column = otherIndex.column;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GridIndex) {
            GridIndex other = (GridIndex) obj;
            return row == other.row && column == other.column;
        }
        return false;
    }

    @Override
    public int compareTo(GridIndex other) {
        int result = row - other.row;
        if (result == 0) {
            result = column - other.column;
        }
        return result;
    }
}
