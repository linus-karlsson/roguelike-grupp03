package com.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.roguelike.Point2D;

public class RoomParser {

    private Room currentRoom;
    private GridIndex index;
    private GridIndex startIndex;
    private GridIndex endIndex;
    private Grid grid;

    public RoomParser(Grid grid) {
        this.grid = grid;
        currentRoom = null;
        index = new GridIndex();
        startIndex = new GridIndex();
        endIndex = new GridIndex();
    }

    public RoomParser(Grid grid, Room room) {
        this(grid);
        setRoom(room);
    }

    public void setRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room can't be null");
        }
        double xSpan = room.getPosition().getX() + room.getWidth();
        double ySpan = room.getPosition().getY() + room.getHeight();
        Point2D lastPosition = new Point2D(xSpan, ySpan);

        startIndex = grid.getGriddIndexBasedOnPosition(room.getPosition());
        endIndex = grid.getGriddIndexBasedOnPosition(lastPosition);
        resetRoom();
        currentRoom = room;
    }

    public void resetRoom() {
        index = new GridIndex(startIndex);
    }

    Room getCurrentRoom() {
        return currentRoom;
    }

    public GridIndex getRoomStartIndex() {
        return new GridIndex(startIndex);
    }

    public GridIndex getRoomEndIndex() {
        return new GridIndex(endIndex);
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

    public GridIndex nextIndex() {
        if (!hasNextIndex()) {
            throw new IllegalAccessError("nextIndex is out of bounds");
        }
        GridIndex result = new GridIndex(index);
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
        GridIndex i = new GridIndex(startIndex.row - 1, startIndex.column - 1);
        for (; i.column <= endIndex.column; i.column++) {
            grid.setTile(i, Grid.BORDER_VALUE);
        }
    }

    private void setTilesBelowRoom() {
        GridIndex i = new GridIndex(endIndex.row + 1, endIndex.column + 1);
        for (; i.column >= startIndex.column; i.column--) {
            grid.setTile(i, Grid.BORDER_VALUE);
        }
    }

    private void setTilesToLeftOfRoom() {
        GridIndex i = new GridIndex(startIndex.row, startIndex.column - 1);
        for (; i.row <= endIndex.row + 1; i.row++) {
            grid.setTile(i, Grid.BORDER_VALUE);
        }
    }

    private void setTilesToRightOfRoom() {
        GridIndex i = new GridIndex(endIndex.row, endIndex.column + 1);
        for (; i.row >= startIndex.row - 1; i.row--) {
            grid.setTile(i, Grid.BORDER_VALUE);
        }
    }

}
