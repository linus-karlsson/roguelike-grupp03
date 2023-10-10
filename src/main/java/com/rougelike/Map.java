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

    public Map() {
        random = new Random();
    }

    public Map(Random random) {
        this.random = random;
    }

    // Returnerar rummen som blir
    public ArrayList<Room> placeRoomsInArea(ArrayList<Room> rooms, int rows, int columns, double cellSize) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException();
        }
        int griddSize = rows * columns;
        gridd = new ArrayList<>(griddSize);

        for (int i = 0; i < griddSize; i++) {
            gridd.add(0);
        }
        ArrayList<Room> roomsPlaced = new ArrayList<>();

        // double width = columns * cellSize;
        // double height = rows * cellSize;

        while (true) {
            Room currentRoom = rooms.get(random.nextInt(rooms.size() - 1));
            currentRoom.setPosition(10.0, 10.0);

            double xStart = currentRoom.getPosition().getX();
            double xSpand = xStart + currentRoom.getWidth();
            double yStart = currentRoom.getPosition().getY();
            double ySpand = yStart + currentRoom.getHeight();

            int startIndex = getCanonicalPostion(currentRoom.getPosition(), columns, cellSize);
            Point xEndPosition = new Point(xSpand, yStart);
            int endIndexInX = getCanonicalPostion(xEndPosition, columns, cellSize);
            Point lastPoistion = new Point(xSpand, ySpand);
            int endIndex = getCanonicalPostion(lastPoistion, columns, cellSize);

            int index = startIndex;
            int rowCount = 1;
            while (index <= endIndex) {
                if (index == endIndexInX) {
                    index = startIndex + (columns * rowCount);
                    endIndexInX += columns;
                    rowCount++;
                }
                gridd.set(index, gridd.get(index) + 1);
                index++;
            }
            roomsPlaced.add(currentRoom);
            break;
        }
        return roomsPlaced;
    }

    public int getCanonicalPostion(Point position, int columns, double cellSize) {
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

}
