package com.roguelike.dungeon;

import com.roguelike.Point2D;

public class Room {
    private int id;
    private Point2D position;
    private double width;
    private double height;
    private boolean connected;

    public Room(double width, double height) {
        setWidth(width);
        setHeight(height);
        position = new Point2D();
        id = 0;
        connected = false;
    }

    public Room(Room otherRoom) {
        this(otherRoom.getWidth(), otherRoom.getHeight());
        id = otherRoom.id;
        position.clone(otherRoom.getPosition());
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setPosition(double x, double y) {
        position.setX(x);
        position.setY(y);
    }

    public Point2D getPosition() {
        return new Point2D(position);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double newWidth) {
        checkDimension(newWidth);
        width = newWidth;
    }

    public void setHeight(double newHeight) {
        checkDimension(newHeight);
        height = newHeight;
    }

    private void checkDimension(double dimension) {
        if (dimension < 0) {
            throw new IllegalArgumentException("Dimensions of room can't be less than 0");
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof Room) {
            Room other = (Room) obj;
            return id == other.id && position.equals(other.position) && width == other.width && height == other.height
                    && connected == other.connected;
        }
        return false;
    }
}
