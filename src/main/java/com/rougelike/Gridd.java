package com.rougelike;

import java.util.ArrayList;

public class Gridd {

    public static final int BORDER_VALUE = -2;

    public class Index implements Comparable<Index> {
        int row;
        int column;

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

        public RoomParser() {
            index = new Index();
            startIndex = new Index();
            endIndex = new Index();
        }

        public void setRoom(Room room) {
            double xSpand = room.getPosition().getX() + room.getWidth();
            double ySpand = room.getPosition().getY() + room.getHeight();
            Point lastPoistion = new Point(xSpand, ySpand);

            startIndex = getGriddIndexBasedOnPosition(room.getPosition());
            endIndex = getGriddIndexBasedOnPosition(lastPoistion);
            index = new Index(startIndex);
            currentRoom = room;
        }

        private void resetRoom() {
            setRoom(currentRoom);
        }

        public Index getRoomStartIndex() {
            return startIndex;
        }

        public Index getRoomEndIndex() {
            return endIndex;
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

        public void setRoomBorder() {
            setTilesAboveRoom();
            setTilesBelowRoom();
            setTilesToLeftOfRoom();
            setTilesToRightOfRoom();
            resetRoom();
        }

    }

    private int[][] tiles;
    private double tileSize;
    private RoomParser roomParser;

    public Gridd(int rowCount, int columnCount, double tileSize) {
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

    public double getTileSize() {
        return tileSize;
    }

    public double getWidth() {
        return getColumnCount() * getTileSize();
    }

    public double getHeight() {
        return getRowCount() * getTileSize();
    }

    public Index getGriddIndexBasedOnPosition(Point position) {
        return new Index(getPositionTileCountInY(position), getPositionTileCountInX(position));
    }

    private int getPositionTileCountInY(Point position) {
        return (int) (position.getY() / tileSize);
    }

    private int getPositionTileCountInX(Point position) {
        return (int) (position.getX() / tileSize);
    }
}
