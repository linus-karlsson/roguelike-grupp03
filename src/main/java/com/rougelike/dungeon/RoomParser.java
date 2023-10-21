package com.rougelike.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.rougelike.Point2D;

public class RoomParser {

    private Room currentRoom;
    private Grid.Index index;
    private Grid.Index startIndex;
    private Grid.Index endIndex;
    private Grid grid;

    public RoomParser(Grid grid) {
        this.grid = grid;
        currentRoom = new Room(0.0, 0.0);
        index = grid.new Index();
        startIndex = grid.new Index();
        endIndex = grid.new Index();
    }

    public RoomParser(Grid grid, Room room) {
        this(grid);
        setRoom(room);
    }

    public void setRoom(Room room) {
        double xSpan = room.getPosition().getX() + room.getWidth();
        double ySpan = room.getPosition().getY() + room.getHeight();
        Point2D lastPosition = new Point2D(xSpan, ySpan);

        startIndex = grid.getGriddIndexBasedOnPosition(room.getPosition());
        endIndex = grid.getGriddIndexBasedOnPosition(lastPosition);
        resetRoom();
        currentRoom = room;
    }

    public void resetRoom() {
        index = grid.new Index(startIndex);
    }

    public Grid.Index getRoomStartIndex() {
        return grid.new Index(startIndex);
    }

    public Grid.Index getRoomEndIndex() {
        return grid.new Index(endIndex);
    }

    public int getRoomTileCountInX() {
        return (endIndex.column - startIndex.column) + 1;
    }

    public int getRoomTileCountInY() {
        return (endIndex.row - startIndex.row) + 1;
    }

    public boolean hasNextIndex() {
        return index.compareTo(endIndex) <= 0;
    }

    public Grid.Index nextIndex() {
        if (!hasNextIndex()) {
            throw new IllegalAccessError("nextIndex is out of bounds");
        }
        Grid.Index result = grid.new Index(index);
        if (index.column == endIndex.column) {
            index.column = startIndex.column;
            index.row += 1;
        } else {
            index.column++;
        }
        return result;
    }

    public List<Integer> roomAreaToList() {
        int rows = getRoomTileCountInY();
        int columns = getRoomTileCountInX();
        List<Integer> result = new ArrayList<>(rows * columns);
        while (hasNextIndex()) {
            result.add(grid.getTile(nextIndex()));
        }
        resetRoom();
        return result;
    }

    public void placeRoomInGridd() {
        while (hasNextIndex()) {
            grid.setTile(nextIndex(), currentRoom.getId());
        }
        resetRoom();
    }

    public void setRoomBorder() {
        setTilesAboveRoom();
        setTilesBelowRoom();
        setTilesToLeftOfRoom();
        setTilesToRightOfRoom();
    }

    private void setTilesAboveRoom() {
        Grid.Index i = grid.new Index(startIndex.row - 1, startIndex.column - 1);
        for (; i.column <= endIndex.column; i.column++) {
            grid.setTile(i, Grid.BORDER_VALUE);
        }
    }

    private void setTilesBelowRoom() {
        Grid.Index i = grid.new Index(endIndex.row + 1, endIndex.column + 1);
        for (; i.column >= startIndex.column; i.column--) {
            grid.setTile(i, Grid.BORDER_VALUE);
        }
    }

    private void setTilesToLeftOfRoom() {
        Grid.Index i = grid.new Index(startIndex.row, startIndex.column - 1);
        for (; i.row <= endIndex.row + 1; i.row++) {
            grid.setTile(i, Grid.BORDER_VALUE);
        }
    }

    private void setTilesToRightOfRoom() {
        Grid.Index i = grid.new Index(endIndex.row, endIndex.column + 1);
        for (; i.row >= startIndex.row - 1; i.row--) {
            grid.setTile(i, Grid.BORDER_VALUE);
        }
    }

}
