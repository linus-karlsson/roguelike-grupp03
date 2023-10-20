package com.rougelike;

import java.util.ArrayList;

public class Grid {

    public static final int BORDER_VALUE = -2;

    public class Index implements Comparable<Index> {
        public int row;
        public int column;

        public Index() {
            row = 0;
            column = 0;
        }

        public Index(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public Index(Index otherIndex) {
            row = otherIndex.row;
            column = otherIndex.column;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Index) {
                Index other = (Index) obj;
                return row == other.row && column == other.column;
            }
            return false;
        }

        @Override
        public int compareTo(Index other) {
            int result = row - other.row;
            if (result == 0) {
                result = column - other.column;
            }
            return result;
        }
    }

    public class RoomParser {

        private Room currentRoom;
        private Index index;
        private Index startIndex;
        private Index endIndex;

        private RoomParser() {
            currentRoom = new Room(0.0, 0.0);
            index = new Index();
            startIndex = new Index();
            endIndex = new Index();
        }

        public void setRoom(Room room) {
            double xSpand = room.getPosition().getX() + room.getWidth();
            double ySpand = room.getPosition().getY() + room.getHeight();
            Point2D lastPoistion = new Point2D(xSpand, ySpand);

            startIndex = getGriddIndexBasedOnPosition(room.getPosition());
            endIndex = getGriddIndexBasedOnPosition(lastPoistion);
            index = new Index(startIndex);
            currentRoom = room;
        }

        private void resetRoom() {
            setRoom(currentRoom);
        }

        public Index getRoomStartIndex() {
            return new Index(startIndex);
        }

        public Index getRoomEndIndex() {
            return new Index(endIndex);
        }

        public int getRoomTileCountInX() {
            return (endIndex.column - startIndex.column) + 1;
        }

        public int getRoomTileCountInY() {
            return (endIndex.row - startIndex.row) + 1;
        }

        public boolean hasNextIndex() {
            return index.compareTo(endIndex) <= 0;
        }

        public Index nextIndex() {
            Index result = new Index(index);
            if (index.column == endIndex.column) {
                index.column = startIndex.column;
                index.row += 1;
            } else {
                index.column++;
            }
            return result;
        }

        public ArrayList<Integer> roomAreaToList() {
            int rows = getRoomTileCountInY();
            int columns = getRoomTileCountInX();
            ArrayList<Integer> result = new ArrayList<>(rows * columns);
            while (hasNextIndex()) {
                result.add(getTile(nextIndex()));
            }
            resetRoom();
            return result;
        }

        public void placeRoomInGridd() {
            while (hasNextIndex()) {
                setTile(nextIndex(), currentRoom.getId());
            }
            resetRoom();
        }

        public void setRoomBorder() {
            setTilesAboveRoom();
            setTilesBelowRoom();
            setTilesToLeftOfRoom();
            setTilesToRightOfRoom();
            resetRoom();
        }

        private void setTilesAboveRoom() {
            Index i = new Index(startIndex.row - 1, startIndex.column - 1);
            for (; i.column <= endIndex.column; i.column++) {
                setTile(i, BORDER_VALUE);
            }
        }

        private void setTilesBelowRoom() {
            Index i = new Index(endIndex.row + 1, endIndex.column + 1);
            for (; i.column >= startIndex.column; i.column--) {
                setTile(i, BORDER_VALUE);
            }
        }

        private void setTilesToLeftOfRoom() {
            Index i = new Index(startIndex.row, startIndex.column - 1);
            for (; i.row <= endIndex.row + 1; i.row++) {
                setTile(i, BORDER_VALUE);
            }
        }

        private void setTilesToRightOfRoom() {
            Index i = new Index(endIndex.row, endIndex.column + 1);
            for (; i.row >= startIndex.row - 1; i.row--) {
                setTile(i, BORDER_VALUE);
            }
        }

    }

    private int[][] tiles;
    private double tileSize;
    private RoomParser roomParser;

    public Grid(int rowCount, int columnCount, double tileSize) {
        tiles = new int[rowCount][columnCount];
        this.tileSize = tileSize;
        roomParser = new RoomParser();
    }

    public RoomParser getRoomParser() {
        return roomParser;
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

    public int getTile(Index index) {
        return getTile(index.row, index.column);
    }

    public void setTile(int row, int column, int value) {
        tiles[row][column] = value;
    }

    public void setTile(Index index, int value) {
        setTile(index.row, index.column, value);
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

    public double getTileSize() {
        return tileSize;
    }

    public double getWidth() {
        return getColumnCount() * getTileSize();
    }

    public double getHeight() {
        return getRowCount() * getTileSize();
    }

    public Index getGriddIndexBasedOnPosition(Point2D position) {
        return new Index(getPositionTileCountInY(position), getPositionTileCountInX(position));
    }

    private int getPositionTileCountInY(Point2D position) {
        return (int) (position.getY() / tileSize);
    }

    private int getPositionTileCountInX(Point2D position) {
        return (int) (position.getX() / tileSize);
    }
}
