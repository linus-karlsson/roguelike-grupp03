package com.rougelike;

import java.util.*;

public class Map {
    private static final double MIN_ROOM_WIDTH_OR_HEIGHT = 1.0;
    private static final double MAX_ROOM_WIDTH_OR_HEIGHT = 400.0;

    private Random random;

    private Gridd gridd;

    public class Room {
        private Point position = new Point();
        private double width;
        private double height;

        public Room(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public void setPosition(double x, double y) {
            position.setX(x);
            position.setY(y);
        }

        public Point getPosition() {
            return position;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
    }

    public class GriddIndex {
        int row;
        int column;

        public GriddIndex() {
            row = 0;
            column = 0;
        }

        public GriddIndex(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public GriddIndex(GriddIndex otherIndex) {
            row = otherIndex.row;
            column = otherIndex.column;
        }
    }

    public class Gridd {
        private int[][] cells;
        private double cellSize;

        public Gridd(int rowCount, int columnCount, double cellSize) {
            cells = new int[rowCount][columnCount];
            this.cellSize = cellSize;
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

        public int getCell(GriddIndex index) {
            return getCell(index.row, index.column);
        }

        public void setCell(int row, int column, int value) {
            cells[row][column] = value;
        }

        public void setCell(GriddIndex index, int value) {
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
    }

    public class GriddParser {

        private GriddIndex index;
        private GriddIndex startIndex;
        private GriddIndex endIndex;

        public GriddParser(int columns, double cellSize) {
            index = new GriddIndex();
            startIndex = new GriddIndex();
            endIndex = new GriddIndex();
        }

        public void setRoom(Room room, double cellSize) {
            double xSpand = room.getPosition().getX() + room.getWidth();
            double ySpand = room.getPosition().getY() + room.getHeight();
            Point lastPoistion = new Point(xSpand, ySpand);

            startIndex = getGriddIndexBasedOnPosition(room.getPosition(), cellSize);
            endIndex = getGriddIndexBasedOnPosition(lastPoistion, cellSize);
            index = new GriddIndex(startIndex);
        }

        public GriddIndex getStartIndex() {
            return startIndex;
        }

        public GriddIndex getEndIndex() {
            return endIndex;
        }

        public int getCurrentRoomCellCountInX() {
            return (endIndex.column - startIndex.column) + 1;
        }

        public int getCurrentRoomCellCountInY() {
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

        public GriddIndex nextIndex() {
            GriddIndex result = new GriddIndex(index);
            if (index.column == endIndex.column) {
                index.column = startIndex.column;
                index.row += 1;
            } else {
                index.column++;
            }
            return result;
        }

        private void placeRoomInGridd(Gridd gridd) {
            while (hasNextIndex()) {
                GriddIndex i = nextIndex();
                gridd.setCell(i, gridd.getCell(i) + 1);
            }
        }

        public void setCellsAboveRoom(Gridd gridd) {
            GriddIndex i = new GriddIndex(startIndex);
            for (i.row -= 1, i.column -= 1; i.column <= endIndex.column; i.column++) {
                gridd.setCell(i, -1);
            }
        }

        public void setCellsBelowRoom(Gridd gridd) {
            GriddIndex i = new GriddIndex(endIndex);
            for (i.row += 1, i.column += 1; i.column >= startIndex.column; i.column--) {
                gridd.setCell(i, -1);
            }
        }

        public void setCellsToLeftOfRoom(Gridd gridd) {
            GriddIndex i = new GriddIndex(startIndex);
            for (i.column -= 1; i.row <= endIndex.row + 1; i.row++) {
                gridd.setCell(i, -1);
            }
        }

        public void setCellsToRightOfRoom(Gridd gridd) {
            GriddIndex i = new GriddIndex(endIndex);
            for (i.column += 1; i.row >= startIndex.row - 1; i.row--) {
                gridd.setCell(i, -1);
            }
        }

        public void setAllCellsAroundRoom(Gridd gridd) {
            setCellsAboveRoom(gridd);
            setCellsBelowRoom(gridd);
            setCellsToLeftOfRoom(gridd);
            setCellsToRightOfRoom(gridd);
        }

    }

    public Map() {
        random = new Random();
    }

    public Map(Random random) {
        this.random = random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    private void setUpGriddBorder(Gridd gridd) {
        int lastRow = gridd.getRowCount() - 1;
        for (int column = 0; column < gridd.getColumnCount(); column++) {
            gridd.setCell(0, column, -1);
            gridd.setCell(lastRow, column, -1);
        }
        int lastColumn = gridd.getColumnCount() - 1;
        for (int row = 0; row < gridd.getRowCount(); row++) {
            gridd.setCell(row, 0, -1);
            gridd.setCell(row, lastColumn, -1);
        }
    }

    private void fillGriddWithZeros(Gridd gridd) {
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                gridd.setCell(row, column, 0);
            }
        }
    }

    private Gridd setUpGridd(int rows, int columns, double cellSize) {
        Gridd gridd = new Gridd(rows, columns, cellSize);
        fillGriddWithZeros(gridd);
        setUpGriddBorder(gridd);
        return gridd;
    }

    private boolean checkIfRoomCanBePlaced(GriddParser griddParser, Room room) {
        griddParser.setRoom(room, gridd.getCellSize());
        while (griddParser.hasNextIndex()) {
            GriddIndex index = griddParser.nextIndex();
            int currentCellValue = gridd.getCell(index);
            if (currentCellValue == 1 || currentCellValue == -1) {
                return false;
            }
        }
        return true;
    }

    private void placeRoomInGridd(GriddParser griddParser, Room room) {
        griddParser.setRoom(room, gridd.getCellSize());
        griddParser.setAllCellsAroundRoom(gridd);
        griddParser.placeRoomInGridd(gridd);
    }

    public ArrayList<Room> placeRoomsInArea(ArrayList<Room> rooms, int numberOfTriesBeforeDiscard, int rows,
            int columns, double cellSize) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException();
        }
        gridd = setUpGridd(rows, columns, cellSize);
        ArrayList<Room> roomsPlaced = new ArrayList<>();
        GriddParser griddParser = new GriddParser(columns, cellSize);
        for (Room currentRoom : rooms) {
            // cellSize är med här för att inte ha med griddens border i platseringen,
            // alltså kan man int platsera ett rum på the border
            Point min = new Point(cellSize, cellSize);
            Point max = new Point(gridd.getWidth() - cellSize - (currentRoom.getWidth() + 1.0),
                    gridd.getHeight() - cellSize - (currentRoom.getHeight() + 1.0));

            for (int i = 0; i < numberOfTriesBeforeDiscard; i++) {
                currentRoom.setPosition(randomDoubleInBounds(min.getX(), max.getX()),
                        randomDoubleInBounds(min.getY(), max.getY()));
                if (!checkIfRoomCanBePlaced(griddParser, currentRoom)) {
                    int j = 0;
                    j++;
                }
                if (checkIfRoomCanBePlaced(griddParser, currentRoom)) {
                    placeRoomInGridd(griddParser, currentRoom);
                    roomsPlaced.add(currentRoom);
                    break;
                }
            }
        }
        return roomsPlaced;
    }

    public GriddIndex getGriddIndexBasedOnPosition(Point position, double cellSize) {
        return new GriddIndex(getCellCountInY(position, cellSize),
                getCellCountInX(position, cellSize));
    }

    private int getCellCountInY(Point position, double cellSize) {
        return (int) (position.getY() / cellSize);
    }

    private int getCellCountInX(Point position, double cellSize) {
        return (int) (position.getX() / cellSize);
    }

    public ArrayList<Room> generateListOfRooms(int roomCount, double minWidth, double maxWidth, double minHeight,
            double maxHeight) {
        ArrayList<Room> result = new ArrayList<>(roomCount);

        for (int i = 0; i < roomCount; i++) {
            result.add(generateRoom(minWidth, maxWidth, minHeight, maxHeight));
        }

        return result;
    }

    public Room generateRoom(double minWidth, double maxWidth, double minHeight, double maxHeight) {

        if (minWidth < MIN_ROOM_WIDTH_OR_HEIGHT || minHeight < MIN_ROOM_WIDTH_OR_HEIGHT) {
            throw new IllegalArgumentException(
                    String.format("Min width or height of rooms: %f", MIN_ROOM_WIDTH_OR_HEIGHT));
        } else if (maxWidth > MAX_ROOM_WIDTH_OR_HEIGHT || maxHeight > MAX_ROOM_WIDTH_OR_HEIGHT) {
            throw new IllegalArgumentException(
                    String.format("Max width or height of rooms: %f", MAX_ROOM_WIDTH_OR_HEIGHT));
        }
        Room room = new Room(randomDoubleInBounds(minWidth, maxWidth), randomDoubleInBounds(minHeight, maxHeight));
        return room;
    }

    private double randomDoubleInBounds(double low, double high) {
        return (random.nextDouble() * (high - low)) + low;
    }

    public ArrayList<Integer> getCopyOfGridd() {
        ArrayList<Integer> copy = new ArrayList<>();
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                copy.add(gridd.getCell(row, column));
            }
        }
        return copy;
    }

    /*
     * public Gridd getCopyOfGridd() {
     * Gridd copy = new Gridd(gridd.getRowCount(), gridd.getColumnCount(),
     * gridd.getCellSize());
     * for (int row = 0; row <= gridd.getRowCount(); row++) {
     * for (int column = 0; column <= gridd.getColumnCount(); column++) {
     * copy.setCell(row, column, gridd.getCell(row, column));
     * }
     * }
     * return copy;
     * }
     */

    public void printGridd(int columns) {
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                int cellValue = gridd.getCell(row, column) == -1 ? 0 : gridd.getCell(row, column);
                System.out.print(String.format("%d%d", cellValue, cellValue));
            }
            System.out.println();
        }
    }

}
