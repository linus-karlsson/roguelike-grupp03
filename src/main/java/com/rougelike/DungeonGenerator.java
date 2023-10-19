package com.rougelike;

import java.util.ArrayList;
import java.util.Random;

public class DungeonGenerator {
    public static final double TILE_SIZE = 10.0;
    private static final double MIN_ROOM_WIDTH_OR_HEIGHT = TILE_SIZE;
    private static final double MAX_ROOM_WIDTH_OR_HEIGHT = 400.0;

    private Random random;
    private Gridd gridd;

    public DungeonGenerator() {
        random = new Random();
    }

    public DungeonGenerator(Random random) {
        this.random = random;
    }

    public void setRandom(Random random) {
        this.random = random;
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

    public ArrayList<Room> generateListOfRooms(int roomCount, double minWidth, double maxWidth, double minHeight,
            double maxHeight) {
        ArrayList<Room> result = new ArrayList<>(roomCount);

        for (int i = 0; i < roomCount; i++) {
            result.add(generateRoom(minWidth, maxWidth, minHeight, maxHeight));
        }

        return result;
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

                gridd.getRoomParser().setRoom(currentRoom);
                if (checkIfRoomCanBePlaced(gridd)) {
                    currentRoom.setId(roomsPlaced.size());
                    placeRoomInGridd(gridd.getRoomParser());
                    roomsPlaced.add(new Room(currentRoom));
                    break;
                }
            }
        }
        return roomsPlaced;
    }

    private Gridd setUpGridd(int rows, int columns) {
        Gridd gridd = new Gridd(rows, columns, TILE_SIZE);
        gridd.fillWithValue(-1);
        setUpGriddBorder(gridd);
        return gridd;
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

    private boolean checkIfRoomCanBePlaced(Gridd gridd) {
        while (gridd.getRoomParser().hasNextIndex()) {
            Gridd.Index index = gridd.getRoomParser().nextIndex();
            int currentTileValue = gridd.getTile(index);
            if (currentTileValue >= 0
                    || currentTileValue == Gridd.BORDER_VALUE) {
                return false;
            }
        }
        return true;
    }

    private void placeRoomInGridd(Gridd.RoomParser griddParser) {
        griddParser.setRoomBorder();
        griddParser.placeRoomInGridd();
    }

    private Room getNextNonConnectedRoom(ArrayList<Room> rooms, int index) {
        int count = 1;
        Room endRoom = null;
        do {
            endRoom = rooms.get(index++ % rooms.size());
            if (count++ == rooms.size()) {
                return null;
            }
        } while (endRoom.isConnected());
        return endRoom;
    }

    public void connectRooms(ArrayList<Room> rooms) {
        int endRoomIndex = 1;
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room startRoom = rooms.get(i);
            Room endRoom = getNextNonConnectedRoom(rooms, endRoomIndex);
            if (endRoom == null) {
                return;
            }
            endRoomIndex = endRoom.getId();

            Gridd.Index startRoomGriddIndex = gridd.getGriddIndexBasedOnPosition(startRoom.getPosition());
            Gridd.Index endRoomGriddIndex = gridd.getGriddIndexBasedOnPosition(endRoom.getPosition());

            Gridd.Index indexForRowTraversal = startRoomGriddIndex.row <= endRoomGriddIndex.row
                    ? startRoomGriddIndex
                    : endRoomGriddIndex;
            Gridd.Index indexForColumnTraversal = startRoomGriddIndex.column <= endRoomGriddIndex.column
                    ? startRoomGriddIndex
                    : endRoomGriddIndex;

            int rowDifferens = abs(startRoomGriddIndex.row - endRoomGriddIndex.row);
            int columnDifferens = abs(startRoomGriddIndex.column - endRoomGriddIndex.column);
            iterateRowTilesToNextRoom(rowDifferens, indexForRowTraversal, rooms);
            iterateColumnTilesToNextRoom(columnDifferens, indexForColumnTraversal, rooms);
            startRoom.setConnected(true);
            endRoom.setConnected(true);
        }
    }

    private int abs(int value) {
        return value < 0 ? value * -1 : value;
    }

    private void iterateRowTilesToNextRoom(int rowDifferens, Gridd.Index indexForRowTraversal, ArrayList<Room> rooms) {
        for (int j = 0; j <= rowDifferens; j++, indexForRowTraversal.row++) {
            int currentTileValue = gridd.getTile(indexForRowTraversal);
            gridd.setTile(indexForRowTraversal, currentTileValue >= 0 ? currentTileValue : rooms.size());
            checkAdjacentTiles(gridd, indexForRowTraversal, rooms);
        }
        indexForRowTraversal.row--; // Reset the last addition
    }

    private void iterateColumnTilesToNextRoom(int columnDifferens, Gridd.Index indexForColumnTraversal,
            ArrayList<Room> rooms) {
        for (int j = 0; j <= columnDifferens; j++, indexForColumnTraversal.column++) {
            int currentTileValue = gridd.getTile(indexForColumnTraversal);
            gridd.setTile(indexForColumnTraversal, currentTileValue >= 0 ? currentTileValue : rooms.size());
            checkAdjacentTiles(gridd, indexForColumnTraversal, rooms);
        }
    }

    private void checkAdjacentTiles(Gridd gridd, Gridd.Index index, ArrayList<Room> rooms) {
        for (int i = -1; i < 2; i += 2) {
            int tileValue = gridd.getTile(gridd.new Index(index.row + i, index.column));
            if (tileValue >= 0 && tileValue < rooms.size()) {
                rooms.get(tileValue).setConnected(true);
            }
        }
        for (int i = -1; i < 2; i += 2) {
            int tileValue = gridd.getTile(gridd.new Index(index.row + i, index.column));
            if (tileValue >= 0 && tileValue < rooms.size()) {
                rooms.get(tileValue).setConnected(true);
            }
        }
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
}
