package com.rougelike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class DungeonGenerator {
    public static final double TILE_SIZE = 10.0;
    private static final double MIN_ROOM_WIDTH_OR_HEIGHT = TILE_SIZE;
    private static final double MAX_ROOM_WIDTH_OR_HEIGHT = 400.0;

    private Random random;
    private Grid grid;

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

    public List<Room> generateListOfRooms(int roomCount, double minWidth, double maxWidth, double minHeight,
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

    public List<Room> placeRoomsInArea(List<Room> rooms, int numberOfTriesBeforeDiscard, int rowCount,
            int columnCount) {
        checkArgumentsInPlaceRooomsInArea(rooms, numberOfTriesBeforeDiscard, rowCount, columnCount);
        grid = setUpGridd(rowCount, columnCount);
        List<Room> roomsPlaced = new ArrayList<>();
        for (Room currentRoom : rooms) {
            // TILE_SIZE är med här för att inte ha med griddens border i platseringen,
            // alltså kan man inte platsera ett rum på the bordern
            Point2D min = new Point2D(TILE_SIZE, TILE_SIZE);
            Point2D max = new Point2D(grid.getWidth() - TILE_SIZE - (currentRoom.getWidth() + 1.0),
                    grid.getHeight() - TILE_SIZE - (currentRoom.getHeight() + 1.0));

            for (int i = 0; i < numberOfTriesBeforeDiscard; i++) {
                currentRoom.setPosition(randomDoubleInBounds(min.getX(), max.getX()),
                        randomDoubleInBounds(min.getY(), max.getY()));

                grid.getRoomParser().setRoom(currentRoom);
                if (checkIfRoomCanBePlaced(grid)) {
                    currentRoom.setId(roomsPlaced.size());
                    placeRoomInGridd(grid.getRoomParser());
                    roomsPlaced.add(new Room(currentRoom));
                    break;
                }
            }
        }
        return Collections.unmodifiableList(roomsPlaced);
    }

    private void checkArgumentsInPlaceRooomsInArea(List<Room> rooms, int numberOfTriesBeforeDiscard, int rowCount,
            int columnCount) {
        if (rooms == null) {
            throw new IllegalArgumentException("rooms can't be null");
        }
        if (numberOfTriesBeforeDiscard <= 0) {
            throw new IllegalArgumentException("numberOfTriesBeforeDiscard can't be less than or equal 0");
        }
        if (rowCount <= 0 || columnCount <= 0) {
            throw new IllegalArgumentException("rowCount or columnCount can't be less than or equal 0");
        }
    }

    private Grid setUpGridd(int rowCount, int columnCount) {
        Grid grid = new Grid(rowCount, columnCount, TILE_SIZE);
        grid.fillWithValue(-1);
        grid.setBorder();
        return grid;
    }

    private boolean checkIfRoomCanBePlaced(Grid grid) {
        while (grid.getRoomParser().hasNextIndex()) {
            Grid.Index index = grid.getRoomParser().nextIndex();
            int currentTileValue = grid.getTile(index);
            // Kollar att tileValue är ett rum eller en korridor hence >= 0
            if (currentTileValue >= 0
                    || currentTileValue == Grid.BORDER_VALUE) {
                return false;
            }
        }
        return true;
    }

    private void placeRoomInGridd(Grid.RoomParser griddParser) {
        griddParser.setRoomBorder();
        griddParser.placeRoomInGridd();
    }

    public int connectRooms(List<Room> rooms) {
        int corridorCount = 0;
        for (int i = 0, endRoomIndex = 1; i < rooms.size() - 1; i++) {
            Room startRoom = rooms.get(i);
            Room endRoom = getNextNonConnectedRoom(rooms, endRoomIndex);
            if (endRoom == null) {
                return corridorCount;
            }
            endRoomIndex = endRoom.getId();

            Grid.Index startRoomGriddIndex = grid.getGriddIndexBasedOnPosition(startRoom.getPosition());
            Grid.Index endRoomGriddIndex = grid.getGriddIndexBasedOnPosition(endRoom.getPosition());

            Grid.Index indexForRowTraversal = startRoomGriddIndex.row <= endRoomGriddIndex.row
                    ? startRoomGriddIndex
                    : endRoomGriddIndex;
            Grid.Index indexForColumnTraversal = startRoomGriddIndex.column <= endRoomGriddIndex.column
                    ? startRoomGriddIndex
                    : endRoomGriddIndex;

            int rowDifference = abs(startRoomGriddIndex.row - endRoomGriddIndex.row);
            int columnDifference = abs(startRoomGriddIndex.column - endRoomGriddIndex.column);
            iterateRowTilesToNextRoom(rowDifference, indexForRowTraversal, rooms);
            iterateColumnTilesToNextRoom(columnDifference, indexForColumnTraversal, rooms);
            startRoom.setConnected(true);
            endRoom.setConnected(true);
            corridorCount++;
        }
        return corridorCount;
    }

    private Room getNextNonConnectedRoom(List<Room> rooms, int index) {
        int count = 0;
        Room endRoom = null;
        do {
            endRoom = rooms.get(index++ % rooms.size());
        } while (endRoom.isConnected() && ++count < rooms.size());
        return count != rooms.size() ? endRoom : null;
    }

    private int abs(int value) {
        return value < 0 ? value * -1 : value;
    }

    private void iterateRowTilesToNextRoom(int rowDifference, Grid.Index indexForRowTraversal, List<Room> rooms) {
        for (int j = 0; j <= rowDifference; j++, indexForRowTraversal.row++) {
            int currentTileValue = grid.getTile(indexForRowTraversal);
            grid.setTile(indexForRowTraversal, currentTileValue >= 0 ? currentTileValue : rooms.size());
            checkAdjacentTiles(grid, indexForRowTraversal, rooms);
        }
        indexForRowTraversal.row--; // Reset the last addition
    }

    private void iterateColumnTilesToNextRoom(int columnDifference, Grid.Index indexForColumnTraversal,
            List<Room> rooms) {
        for (int j = 0; j <= columnDifference; j++, indexForColumnTraversal.column++) {
            int currentTileValue = grid.getTile(indexForColumnTraversal);
            grid.setTile(indexForColumnTraversal, currentTileValue >= 0 ? currentTileValue : rooms.size());
            checkAdjacentTiles(grid, indexForColumnTraversal, rooms);
        }
        indexForColumnTraversal.column--;
    }

    private void checkAdjacentTiles(Grid grid, Grid.Index index, List<Room> rooms) {
        for (int i = -1; i < 2; i += 2) {
            int tileValue = grid.getTile(grid.new Index(index.row + i, index.column));
            if (tileValue >= 0 && tileValue < rooms.size()) {
                rooms.get(tileValue).setConnected(true);
            }
        }
        for (int i = -1; i < 2; i += 2) {
            int tileValue = grid.getTile(grid.new Index(index.row, index.column + i));
            if (tileValue >= 0 && tileValue < rooms.size()) {
                rooms.get(tileValue).setConnected(true);
            }
        }
    }

    public int[] getConnectedRooms(List<Room> placedRooms, Grid grid) {
        if (!grid.hasBorder()) {
            throw new IllegalArgumentException("Gridd must have a border");
        }
        Stack<Grid.Index> stack = new Stack<>();
        stack.add(grid.getGriddIndexBasedOnPosition(placedRooms.get(0).getPosition()));

        int[] roomsFound = getArrayOfNegtiveOnes(placedRooms.size());
        int[][] visited = getGriddOfZeros(grid.getRowCount(), grid.getColumnCount());
        while (!stack.isEmpty()) {
            Grid.Index currentIndex = stack.pop();
            int tileValue = grid.getTile(currentIndex);
            if (isValid(tileValue, visited, currentIndex)) {
                visited[currentIndex.row][currentIndex.column] = 1;
                if (tileValue < placedRooms.size()) {
                    roomsFound[tileValue] = tileValue;
                }
                addAdjacentTiles(stack, grid, currentIndex);
            }
        }
        return roomsFound;
    }

    private int[] getArrayOfNegtiveOnes(int size) {
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

    private boolean isValid(int tileValue, int[][] visited, Grid.Index currentIndex) {
        return tileValue >= 0 && visited[currentIndex.row][currentIndex.column] != 1;
    }

    private void addAdjacentTiles(Stack<Grid.Index> stack, Grid grid, Grid.Index index) {
        for (int i = -1; i < 2; i += 2) {
            stack.add(grid.new Index(index.row + i, index.column));
        }
        for (int i = -1; i < 2; i += 2) {
            stack.add(grid.new Index(index.row, index.column + i));
        }
    }

    public Grid getCopyOfGridd() {
        Grid copy = new Grid(grid.getRowCount(), grid.getColumnCount(),
                grid.getTileSize());
        for (int row = 0; row < grid.getRowCount(); row++) {
            for (int column = 0; column < grid.getColumnCount(); column++) {
                copy.setTile(row, column, grid.getTile(row, column));
            }
        }
        return copy;
    }

}
