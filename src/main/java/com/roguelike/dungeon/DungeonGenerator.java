package com.roguelike.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.roguelike.Point2D;

public class DungeonGenerator {
    public static final double MIN_TILE_SIZE = 10.0;
    public static final double MIN_ROOM_WIDTH_OR_HEIGHT = DungeonGenerator.MIN_TILE_SIZE;
    public static final double MAX_ROOM_WIDTH_OR_HEIGHT = 400.0;
    private static final int NON_OCCUPIED_TILE_VALUE = -1;

    private Random random;
    private Grid grid;

    public DungeonGenerator() {
        random = new Random();
    }

    void setRandom(Random random) {
        this.random = random;
    }

    public List<Room> generateDungeon(int roomCount, double minRoomWidth, double maxRoomWidth, double minRoomHeight,
            double maxRoomHeight, int numberOfTriesBeforeDiscard, int rowCount, int columnCount, double tileSize) {
        List<Room> rooms = generateListOfRooms(roomCount, minRoomWidth, maxRoomWidth, minRoomHeight, maxRoomHeight);
        List<Room> placedRooms = placeRoomsInArea(rooms, numberOfTriesBeforeDiscard, rowCount, columnCount, tileSize);
        connectRooms(placedRooms);
        setPlacedRoomsToCorrectGridPositionAndDimensions(placedRooms);
        return Collections.unmodifiableList(placedRooms);
    }

    private void setPlacedRoomsToCorrectGridPositionAndDimensions(List<Room> placedRooms) {
        RoomParser roomParser = new RoomParser(grid);
        for (Room room : placedRooms) {
            roomParser.setRoom(room);
            Point2D position = room.getPosition();
            double xDifference = position.getX() % grid.getTileSize();
            double yDifference = position.getY() % grid.getTileSize();
            room.setPosition(position.getX() - xDifference, position.getY() - yDifference);
            room.setWidth(roomParser.getRoomTileCountInX() * grid.getTileSize());
            room.setHeight(roomParser.getRoomTileCountInY() * grid.getTileSize());
        }
    }

    public Grid getCopyOfGrid() {
        Grid copy = new Grid(grid.getRowCount(), grid.getColumnCount(),
                grid.getTileSize());
        for (int row = 0; row < grid.getRowCount(); row++) {
            for (int column = 0; column < grid.getColumnCount(); column++) {
                copy.setTile(row, column, grid.getTile(row, column));
            }
        }
        return copy;
    }

    Room generateRoom(double minWidth, double maxWidth, double minHeight, double maxHeight) {
        validateArgumentsInGenerateRoom(minWidth, maxWidth, minHeight, maxHeight);
        Room room = new Room(randomDoubleInBounds(minWidth, maxWidth), randomDoubleInBounds(minHeight, maxHeight));
        return room;
    }

    private void validateArgumentsInGenerateRoom(double minWidth, double maxWidth, double minHeight, double maxHeight) {
        if (minWidth < MIN_ROOM_WIDTH_OR_HEIGHT || minHeight < MIN_ROOM_WIDTH_OR_HEIGHT) {
            throw new IllegalArgumentException(
                    String.format("Min width or height of rooms: %f", MIN_ROOM_WIDTH_OR_HEIGHT));
        } else if (maxWidth > MAX_ROOM_WIDTH_OR_HEIGHT || maxHeight > MAX_ROOM_WIDTH_OR_HEIGHT) {
            throw new IllegalArgumentException(
                    String.format("Max width or height of rooms: %f", MAX_ROOM_WIDTH_OR_HEIGHT));
        } else if (minWidth > maxWidth) {
            throw new IllegalArgumentException(
                    String.format("minWidth(%f) is greater than or equal to maxWidth(%f)", minWidth, maxWidth));
        } else if (minHeight > maxHeight) {
            throw new IllegalArgumentException(
                    String.format("minHeight(%f) is greater than or equal to maxHeight(%f)", minHeight, maxHeight));
        }
    }

    private double randomDoubleInBounds(double low, double high) {
        return (random.nextDouble() * (high - low)) + low;
    }

    List<Room> generateListOfRooms(int roomCount, double minWidth, double maxWidth, double minHeight,
            double maxHeight) {
        if (roomCount <= 0) {
            throw new IllegalArgumentException("roomCount can't be less or equals to 0");
        }
        List<Room> result = new ArrayList<>(roomCount);
        for (int i = 0; i < roomCount; i++) {
            result.add(generateRoom(minWidth, maxWidth, minHeight, maxHeight));
        }

        return result;
    }

    List<Room> placeRoomsInArea(List<Room> rooms, int numberOfTriesBeforeDiscard, int rowCount,
            int columnCount, double tileSize) {
        validateArgumentsInPlaceRooomsInArea(rooms, numberOfTriesBeforeDiscard, rowCount, columnCount, tileSize);
        grid = setUpGrid(rowCount, columnCount, tileSize);
        List<Room> roomsPlaced = new ArrayList<>();
        RoomParser roomParser = new RoomParser(grid);
        for (Room currentRoom : rooms) {
            // tileSize är med här för att inte ha med griddens border i platseringen,
            // alltså kan man inte platsera ett rum på bordern
            Point2D minRoomPosition = new Point2D(tileSize, tileSize);
            Point2D maxRoomPosition = new Point2D(grid.getWidth() - tileSize - (currentRoom.getWidth() + 1.0),
                    grid.getHeight() - tileSize - (currentRoom.getHeight() + 1.0));

            for (int i = 0; i < numberOfTriesBeforeDiscard; i++) {
                if (tryToPlaceRoom(currentRoom, minRoomPosition, maxRoomPosition, roomParser, roomsPlaced)) {
                    break;
                }
            }
        }
        return roomsPlaced;
    }

    private boolean tryToPlaceRoom(Room room, Point2D minRoomPosition, Point2D maxRoomPosition, RoomParser roomParser,
            List<Room> roomsPlaced) {
        room.setPosition(randomDoubleInBounds(minRoomPosition.getX(), maxRoomPosition.getX()),
                randomDoubleInBounds(minRoomPosition.getY(), maxRoomPosition.getY()));

        if (checkIfRoomCanBePlaced(roomParser, room)) {
            room.setId(roomsPlaced.size());
            roomParser.setRoom(room);
            placeRoomInGridd(roomParser);
            roomsPlaced.add(new Room(room));
            return true;
        }
        return false;
    }

    private void validateArgumentsInPlaceRooomsInArea(List<Room> rooms, int numberOfTriesBeforeDiscard, int rowCount,
            int columnCount, double tileSize) {
        if (rooms == null) {
            throw new IllegalArgumentException("rooms can't be null");
        }
        if (numberOfTriesBeforeDiscard <= 0) {
            throw new IllegalArgumentException("numberOfTriesBeforeDiscard can't be less than or equal 0");
        }
        if (rowCount <= 0 || columnCount <= 0) {
            throw new IllegalArgumentException("rowCount or columnCount can't be less than or equal 0");
        }
        if (tileSize < MIN_TILE_SIZE) {
            throw new IllegalArgumentException(
                    String.format("tileSize is: %f, but the minimum tileSize is: %f", tileSize, MIN_TILE_SIZE));
        }
    }

    private Grid setUpGrid(int rowCount, int columnCount, double tileSize) {
        Grid grid = new Grid(rowCount, columnCount, tileSize);
        grid.fillWithValue(NON_OCCUPIED_TILE_VALUE);
        grid.setBorder();
        return grid;
    }

    private boolean checkIfRoomCanBePlaced(RoomParser roomParser, Room currentRoom) {
        roomParser.setRoom(currentRoom);
        boolean result = true;
        while (result && roomParser.hasNextIndex()) {
            GridIndex index = roomParser.nextIndex();
            int currentTileValue = grid.getTile(index);
            if (currentTileValue != NON_OCCUPIED_TILE_VALUE) {
                result = false;
            }
        }
        roomParser.resetRoom();
        return result;
    }

    private void placeRoomInGridd(RoomParser roomParser) {
        roomParser.setRoomBorder();
        roomParser.placeRoomInGridd();
    }

    int connectRooms(List<Room> rooms) {
        int corridorCount = 0;
        for (int i = 0, endRoomIndex = 1; i < rooms.size() - 1; i++) {
            Room startRoom = rooms.get(i);
            Room endRoom = getNextNonConnectedRoom(rooms, endRoomIndex);
            if (endRoom == null) {
                return corridorCount;
            }
            endRoomIndex = endRoom.getId();
            iterateRowsAndColumns(rooms, grid.getGriddIndexBasedOnPosition(startRoom.getPosition()),
                    grid.getGriddIndexBasedOnPosition(endRoom.getPosition()));

            startRoom.setConnected(true);
            endRoom.setConnected(true);
            corridorCount++;
        }
        return corridorCount;
    }

    private void iterateRowsAndColumns(List<Room> rooms, GridIndex startRoomGriddIndex, GridIndex endRoomGriddIndex) {
        GridIndex indexForRowTraversal = startRoomGriddIndex.row <= endRoomGriddIndex.row
                ? startRoomGriddIndex
                : endRoomGriddIndex;
        GridIndex indexForColumnTraversal = startRoomGriddIndex.column <= endRoomGriddIndex.column
                ? startRoomGriddIndex
                : endRoomGriddIndex;

        int rowDifference = Math.abs(startRoomGriddIndex.row - endRoomGriddIndex.row);
        int columnDifference = Math.abs(startRoomGriddIndex.column - endRoomGriddIndex.column);
        iterateRowTilesToNextRoom(rowDifference, indexForRowTraversal, rooms);
        iterateColumnTilesToNextRoom(columnDifference, indexForColumnTraversal, rooms);

    }

    private Room getNextNonConnectedRoom(List<Room> rooms, int index) {
        int count = 0;
        Room room = null;
        do {
            room = rooms.get(index++ % rooms.size());
        } while (room.isConnected() && ++count < rooms.size());
        return count != rooms.size() ? room : null;
    }

    private void iterateRowTilesToNextRoom(int rowDifference, GridIndex indexForRowTraversal, List<Room> rooms) {
        for (int j = 0; j <= rowDifference; j++, indexForRowTraversal.row++) {
            int currentTileValue = grid.getTile(indexForRowTraversal);
            grid.setTile(indexForRowTraversal, currentTileValue >= 0 ? currentTileValue : rooms.size());
            checkAdjacentTiles(grid, indexForRowTraversal, rooms);
        }
        indexForRowTraversal.row--; // Reset the last addition
    }

    private void iterateColumnTilesToNextRoom(int columnDifference, GridIndex indexForColumnTraversal,
            List<Room> rooms) {
        for (int j = 0; j <= columnDifference; j++, indexForColumnTraversal.column++) {
            int currentTileValue = grid.getTile(indexForColumnTraversal);
            grid.setTile(indexForColumnTraversal, currentTileValue >= 0 ? currentTileValue : rooms.size());
            checkAdjacentTiles(grid, indexForColumnTraversal, rooms);
        }
        indexForColumnTraversal.column--;
    }

    private void checkAdjacentTiles(Grid grid, GridIndex index, List<Room> rooms) {
        for (int i = -1; i < 2; i += 2) {
            int tileValue = grid.getTile(new GridIndex(index.row + i, index.column));
            if (tileValue >= 0 && tileValue < rooms.size()) {
                rooms.get(tileValue).setConnected(true);
            }
        }
        for (int i = -1; i < 2; i += 2) {
            int tileValue = grid.getTile(new GridIndex(index.row, index.column + i));
            if (tileValue >= 0 && tileValue < rooms.size()) {
                rooms.get(tileValue).setConnected(true);
            }
        }
    }

    int[] getConnectedRooms(List<Room> placedRooms, Grid grid) {
        if (!grid.hasBorder()) {
            throw new IllegalArgumentException("Gridd must have a border");
        }
        Stack<GridIndex> stack = new Stack<>();
        stack.add(grid.getGriddIndexBasedOnPosition(placedRooms.get(0).getPosition()));

        int[] roomsFound = getArrayOfNegativeOnes(placedRooms.size());
        int[][] visited = getGriddOfZeros(grid.getRowCount(), grid.getColumnCount());
        while (!stack.isEmpty()) {
            GridIndex currentIndex = stack.pop();
            int tileValue = grid.getTile(currentIndex);
            if (isValid(tileValue, visited, currentIndex)) {
                visited[currentIndex.row][currentIndex.column] = 1;
                if (tileValue < placedRooms.size()) {
                    roomsFound[tileValue] = tileValue;
                }
                addAdjacentTiles(stack, currentIndex);
            }
        }
        return roomsFound;
    }

    private int[] getArrayOfNegativeOnes(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = -1;
        }
        return result;
    }

    private int[][] getGriddOfZeros(int rows, int columnCount) {
        int[][] grid = new int[rows][columnCount];
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                grid[row][column] = 0;
            }
        }
        return grid;
    }

    private boolean isValid(int tileValue, int[][] visited, GridIndex currentIndex) {
        return tileValue >= 0 && visited[currentIndex.row][currentIndex.column] != 1;
    }

    private void addAdjacentTiles(Stack<GridIndex> stack, GridIndex index) {
        for (int i = -1; i < 2; i += 2) {
            stack.add(new GridIndex(index.row + i, index.column));
        }
        for (int i = -1; i < 2; i += 2) {
            stack.add(new GridIndex(index.row, index.column + i));
        }
    }

}
