package com.rougelike;

public class Gridd {

    public static final int BORDER_VALUE = -3;
    public static final int ROOM_BORDER_VALUE = -2;

    public class Index {
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
    }

    public class RoomParser {

        private int currentRoomId;
        private Index index;
        private Index startIndex;
        private Index endIndex;

        public RoomParser() {
            currentRoomId = -1;
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
            currentRoomId = room.getId();
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
            boolean result = index.row <= endIndex.row;
            if (result) {
                if (index.row == endIndex.row) {
                    result = index.column <= endIndex.column;
                }
            }
            return result;
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

        public void placeRoomInGridd() {
            while (hasNextIndex()) {
                Index i = nextIndex();
                setTile(i, currentRoomId);
            }
        }

        public void setTilesAboveRoom() {
            Index i = new Index(startIndex.row - 1, startIndex.column - 1);
            int borderValue = getTile(i) == BORDER_VALUE ? BORDER_VALUE : ROOM_BORDER_VALUE;
            for (; i.column <= endIndex.column; i.column++) {
                setTile(i, borderValue);
            }
        }

        public void setTilesBelowRoom() {
            Index i = new Index(endIndex.row + 1, endIndex.column + 1);
            int borderValue = getTile(i) == BORDER_VALUE ? BORDER_VALUE : ROOM_BORDER_VALUE;
            for (; i.column >= startIndex.column; i.column--) {
                setTile(i, borderValue);
            }
        }

        public void setTilesToLeftOfRoom() {
            Index i = new Index(startIndex.row, startIndex.column - 1);
            int borderValue = getTile(i) == BORDER_VALUE ? BORDER_VALUE : ROOM_BORDER_VALUE;
            for (; i.row <= endIndex.row + 1; i.row++) {
                setTile(i, borderValue);
            }
        }

        public void setTilesToRightOfRoom() {
            Index i = new Index(endIndex.row, endIndex.column + 1);
            int borderValue = getTile(i) == BORDER_VALUE ? BORDER_VALUE : ROOM_BORDER_VALUE;
            for (; i.row >= startIndex.row - 1; i.row--) {
                setTile(i, borderValue);
            }
        }

        public void setRoomBorder() {
            setTilesAboveRoom();
            setTilesBelowRoom();
            setTilesToLeftOfRoom();
            setTilesToRightOfRoom();
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
