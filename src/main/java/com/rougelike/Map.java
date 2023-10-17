package com.rougelike;

import java.util.*;

public class Map {
    public static final double TILE_SIZE = 5.0;
    private static final double MIN_ROOM_WIDTH_OR_HEIGHT = 1.0;
    private static final double MAX_ROOM_WIDTH_OR_HEIGHT = 400.0;

    private Random random;
    private Gridd gridd;

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
            gridd.setTile(0, column, Gridd.BORDER_VALUE);
            gridd.setTile(lastRow, column, Gridd.BORDER_VALUE);
        }
        int lastColumn = gridd.getColumnCount() - 1;
        for (int row = 0; row < gridd.getRowCount(); row++) {
            gridd.setTile(row, 0, Gridd.BORDER_VALUE);
            gridd.setTile(row, lastColumn, Gridd.BORDER_VALUE);
        }
    }

    private void fillGriddWithNegativeOne(Gridd gridd) {
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                gridd.setTile(row, column, -1);
            }
        }
    }

    private Gridd setUpGridd(int rows, int columns) {
        Gridd gridd = new Gridd(rows, columns, TILE_SIZE);
        fillGriddWithNegativeOne(gridd);
        setUpGriddBorder(gridd);
        return gridd;
    }

    private boolean checkIfRoomCanBePlaced(Gridd gridd, Room room) {
        gridd.getRoomParser().setRoom(room);
        while (gridd.getRoomParser().hasNextIndex()) {
            Gridd.Index index = gridd.getRoomParser().nextIndex();
            int currentTileValue = gridd.getTile(index);
            if (currentTileValue >= 0
                    || currentTileValue == Gridd.BORDER_VALUE
                    || currentTileValue == Gridd.ROOM_BORDER_VALUE) {
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
            int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException();
        }
        gridd = setUpGridd(rows, columns);
        ArrayList<Room> roomsPlaced = new ArrayList<>();
        for (Room currentRoom : rooms) {
            // TILE_SIZE är med här för att inte ha med griddens border i platseringen,
            // alltså kan man inte platsera ett rum på the bordern
            Point min = new Point(TILE_SIZE, TILE_SIZE);
            Point max = new Point(gridd.getWidth() - TILE_SIZE - (currentRoom.getWidth() + 1.0),
                    gridd.getHeight() - TILE_SIZE - (currentRoom.getHeight() + 1.0));

            for (int i = 0; i < numberOfTriesBeforeDiscard; i++) {
                currentRoom.setPosition(randomDoubleInBounds(min.getX(), max.getX()),
                        randomDoubleInBounds(min.getY(), max.getY()));

                if (checkIfRoomCanBePlaced(gridd, currentRoom)) {
                    currentRoom.setId(roomsPlaced.size());
                    placeRoomInGridd(gridd.getRoomParser(), currentRoom);
                    roomsPlaced.add(new Room(currentRoom));
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
                gridd.getTileSize());
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                copy.setTile(row, column, gridd.getTile(row, column));
            }
        }
        return copy;
    }

    public void printGridd() {
        for (int row = 0; row < gridd.getRowCount(); row++) {
            for (int column = 0; column < gridd.getColumnCount(); column++) {
                int cellValue = gridd.getTile(row, column);
                if (cellValue == Gridd.BORDER_VALUE || cellValue == Gridd.ROOM_BORDER_VALUE || cellValue == -1)
                    cellValue = 0;
                else if (cellValue >= 0) {
                    cellValue = 1;
                }
                System.out.print(String.format("%d%d", cellValue, cellValue));
            }
            System.out.println();
        }
    }

}
