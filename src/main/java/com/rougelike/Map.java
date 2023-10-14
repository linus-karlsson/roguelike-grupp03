package com.rougelike;

import java.util.*;

public class Map {
    private static final double MIN_ROOM_WIDTH_OR_HEIGHT = 1.0;
    private static final double MAX_ROOM_WIDTH_OR_HEIGHT = 400.0;

    private Random random;

    private ArrayList<Integer> gridd;

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

    public class GriddParser {
        private int columns;
        private int rows;
        private double cellSize;

        private int index;
        private int startIndex;
        private int endIndexInX;
        private int endIndex;
        private int rowCount;

        public GriddParser(int columns, int rows, double cellSize) {
            this.columns = columns;
            this.rows = rows;
            this.cellSize = cellSize;
            index = 0;
            startIndex = 0;
            endIndexInX = 0;
            endIndex = 0;
            rowCount = 0;
        }

        public void setRoom(Room room) {
            double xSpand = room.getPosition().getX() + room.getWidth();
            double ySpand = room.getPosition().getY() + room.getHeight();
            Point xEndPosition = new Point(xSpand, room.getPosition().getY());
            Point lastPoistion = new Point(xSpand, ySpand);

            startIndex = getGriddIndexBasedOnPosition(room.getPosition(), columns, cellSize);
            endIndexInX = getGriddIndexBasedOnPosition(xEndPosition, columns, cellSize);
            endIndex = getGriddIndexBasedOnPosition(lastPoistion, columns, cellSize);
            index = startIndex;
            rowCount = 0;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public int getCurrentRoomCellCountInX() {
            return (endIndexInX - startIndex) + 1;
        }

        public int getCurrentRoomCellCountInY() {
            return ((endIndex - endIndexInX) / columns) + 1;
        }

        public boolean hasNextIndex() {
            return index <= endIndex;
        }

        public int nextIndex() {
            int result = index;
            if (index == endIndexInX + (columns * rowCount)) {
                index = startIndex + (columns * ++rowCount);
            } else {
                index++;
            }
            return result;
        }

        public void setCellsAboveRoom(ArrayList<Integer> gridd) {
            int cellCountInX = getCurrentRoomCellCountInX();
            for (int i = -1; i < cellCountInX; i++) {
                int currentIndex = startIndex - columns + i;
                gridd.set(currentIndex, -1);
            }
        }

        public void setCellsBelowRoom(ArrayList<Integer> gridd) {
            int cellCountInX = getCurrentRoomCellCountInX();
            for (int i = -1; i < cellCountInX; i++) {
                int currentIndex = endIndex + columns - i;
                gridd.set(currentIndex, -1);
            }
        }

        public void setCellsToLeftOfRoom(ArrayList<Integer> gridd) {
            int cellCountInY = getCurrentRoomCellCountInY();
            for (int i = 0; i < cellCountInY + 1; i++) {
                int currentIndex = (startIndex - 1) + (columns * i);
                gridd.set(currentIndex, -1);
            }
        }

        public void setCellsToRightOfRoom(ArrayList<Integer> gridd) {
            int cellCountInY = getCurrentRoomCellCountInY();
            for (int i = 0; i < cellCountInY + 1; i++) {
                int currentIndex = (endIndex + 1) - (columns * i);
                gridd.set(currentIndex, -1);
            }
        }

        public void setAllCellsAroundRoom(ArrayList<Integer> gridd) {
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

    public ArrayList<Room> placeRoomsInArea(ArrayList<Room> rooms, int numberOfTriesBeforeDiscard, int rows,
            int columns, double cellSize) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException();
        }
        int griddSize = rows * columns;
        gridd = new ArrayList<>(griddSize);
        for (int i = 0; i < griddSize; i++) {
            gridd.add(0);
        }
        for (int column = 0; column < columns; column++) {
            gridd.set(column, -1);
        }
        int lastCell = columns * rows;
        for (int column = lastCell - columns; column < lastCell; column++) {
            gridd.set(column, -1);
        }
        for (int row = 0; row < rows; row++) {
            gridd.set(row * columns, -1);
            gridd.set((row * columns) + columns - 1, -1);
        }
        ArrayList<Room> roomsPlaced = new ArrayList<>();

        double width = columns * cellSize;
        double height = rows * cellSize;

        GriddParser griddParser = new GriddParser(columns, rows, cellSize);

        for (Room currentRoom : rooms) {
            for (int i = 0; i < numberOfTriesBeforeDiscard; i++) {
                currentRoom.setPosition(
                        randomDoubleInBounds(cellSize, width - cellSize - (currentRoom.getWidth() + 1.0)),
                        randomDoubleInBounds(cellSize, height - cellSize - (currentRoom.getHeight() + 1.0)));

                boolean allCellsEmpty = true;
                griddParser.setRoom(currentRoom);
                while (griddParser.hasNextIndex()) {
                    int index = griddParser.nextIndex();
                    int currentCellValue = gridd.get(index);
                    if (currentCellValue == 1 || currentCellValue == -1) {
                        allCellsEmpty = false;
                        break;
                    }
                }
                if (allCellsEmpty) {
                    griddParser.setRoom(currentRoom);
                    griddParser.setAllCellsAroundRoom(gridd);
                    while (griddParser.hasNextIndex()) {
                        int index = griddParser.nextIndex();
                        gridd.set(index, gridd.get(index) + 1);
                    }
                    roomsPlaced.add(currentRoom);
                    break;
                }
            }
        }
        return roomsPlaced;
    }

    public int getGriddIndexBasedOnPosition(Point position, int columns, double cellSize) {
        int cellIndex = (getCellCountInY(position, cellSize) * columns) + getCellCountInX(position, cellSize);
        return cellIndex;
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
        for (int i = 0; i < gridd.size(); i++) {
            copy.add(gridd.get(i));
        }
        return copy;
    }

    public void printGridd(int columns) {
        for (int i = 0; i < gridd.size(); i++) {
            int cellValue = gridd.get(i) == -1 ? 0 : gridd.get(i);
            System.out.print(String.format("%d%d", cellValue, cellValue));
            if ((i + 1) % columns == 0) {
                System.out.println();
            }
        }
    }

}
