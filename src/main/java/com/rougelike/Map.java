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

    private boolean checkIfRoomCanBePlaced(Gridd gridd, Room room) {
        gridd.getRoomParser().setRoom(room);
        while (gridd.getRoomParser().hasNextIndex()) {
            Gridd.Index index = gridd.getRoomParser().nextIndex();
            int currentCellValue = gridd.getCell(index);
            if (currentCellValue == 1 || currentCellValue == -1) {
                return false;
            }
        }
        return true;
    }

    private void placeRoomInGridd(Gridd.RoomParser griddParser, Room room) {
        griddParser.setRoom(room);
        griddParser.setRoomBorder();
        griddParser.placeRoomInGridd();
    }

    public ArrayList<Room> placeRoomsInArea(ArrayList<Room> rooms, int numberOfTriesBeforeDiscard, int rows,
            int columns, double cellSize) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException();
        }
        gridd = setUpGridd(rows, columns, cellSize);
        ArrayList<Room> roomsPlaced = new ArrayList<>();
        for (Room currentRoom : rooms) {
            // cellSize är med här för att inte ha med griddens border i platseringen,
            // alltså kan man int platsera ett rum på the border
            Point min = new Point(cellSize, cellSize);
            Point max = new Point(gridd.getWidth() - cellSize - (currentRoom.getWidth() + 1.0),
                    gridd.getHeight() - cellSize - (currentRoom.getHeight() + 1.0));

            for (int i = 0; i < numberOfTriesBeforeDiscard; i++) {
                currentRoom.setPosition(randomDoubleInBounds(min.getX(), max.getX()),
                        randomDoubleInBounds(min.getY(), max.getY()));

                if (checkIfRoomCanBePlaced(gridd, currentRoom)) {
                    placeRoomInGridd(gridd.getRoomParser(), currentRoom);
                    roomsPlaced.add(currentRoom);
                    break;
                }
            }
        }
        return roomsPlaced;
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

    public Gridd getCopyOfGridd() {
        Gridd copy = new Gridd(gridd.getRowCount(), gridd.getColumnCount(),
                gridd.getCellSize());
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                copy.setCell(row, column, gridd.getCell(row, column));
            }
        }
        return copy;
    }

    public void printGridd() {
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                int cellValue = gridd.getCell(row, column);
                if (cellValue == -1)
                    cellValue = 0;
                System.out.print(String.format("%d", cellValue));
            }
            System.out.println();
        }
    }

}
