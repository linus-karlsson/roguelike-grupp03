package com.rougelike;

public class Gridd {

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

        private Index index;
        private Index startIndex;
        private Index endIndex;

        public RoomParser() {
            index = new Index();
            startIndex = new Index();
            endIndex = new Index();
        }

        public void setRoom(Map.Room room) {
            double xSpand = room.getPosition().getX() + room.getWidth();
            double ySpand = room.getPosition().getY() + room.getHeight();
            Point lastPoistion = new Point(xSpand, ySpand);

            startIndex = getGriddIndexBasedOnPosition(room.getPosition());
            endIndex = getGriddIndexBasedOnPosition(lastPoistion);
            index = new Index(startIndex);
        }

        public Index getRoomStartIndex() {
            return startIndex;
        }

        public Index getRoomEndIndex() {
            return endIndex;
        }

        public int getRoomCellCountInX() {
            return (endIndex.column - startIndex.column) + 1;
        }

        public int getRoomCellCountInY() {
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
                setCell(i, getCell(i) + 1);
            }
        }

        public void setCellsAboveRoom() {
            Index i = new Index(startIndex);
            for (i.row -= 1, i.column -= 1; i.column <= endIndex.column; i.column++) {
                setCell(i, -1);
            }
        }

        public void setCellsBelowRoom() {
            Index i = new Index(endIndex);
            for (i.row += 1, i.column += 1; i.column >= startIndex.column; i.column--) {
                setCell(i, -1);
            }
        }

        public void setCellsToLeftOfRoom() {
            Index i = new Index(startIndex);
            for (i.column -= 1; i.row <= endIndex.row + 1; i.row++) {
                setCell(i, -1);
            }
        }

        public void setCellsToRightOfRoom() {
            Index i = new Index(endIndex);
            for (i.column += 1; i.row >= startIndex.row - 1; i.row--) {
                setCell(i, -1);
            }
        }

        public void setRoomBorder() {
            setCellsAboveRoom();
            setCellsBelowRoom();
            setCellsToLeftOfRoom();
            setCellsToRightOfRoom();
        }

    }

    private int[][] cells;
    private double cellSize;
    private RoomParser roomParser;

    public Gridd(int rowCount, int columnCount, double cellSize) {
        cells = new int[rowCount][columnCount];
        this.cellSize = cellSize;
        roomParser = new RoomParser();
    }

    public RoomParser getRoomParser() {
        return roomParser;
    }

    public int getRowCount() {
        return cells.length;
    }

    public int getColumnCount() {
        return cells[0].length;
    }

    public int getCell(int row, int column) {
        return cells[row][column];
    }

    public int getCell(Index index) {
        return getCell(index.row, index.column);
    }

    public void setCell(int row, int column, int value) {
        cells[row][column] = value;
    }

    public void setCell(Index index, int value) {
        setCell(index.row, index.column, value);
    }

    public double getCellSize() {
        return cellSize;
    }

    public double getWidth() {
        return getColumnCount() * getCellSize();
    }

    public double getHeight() {
        return getRowCount() * getCellSize();
    }

    public Index getGriddIndexBasedOnPosition(Point position) {
        return new Index(getCellCountInY(position), getCellCountInX(position));
    }

    private int getCellCountInY(Point position) {
        return (int) (position.getY() / cellSize);
    }

    private int getCellCountInX(Point position) {
        return (int) (position.getX() / cellSize);
    }
}
