package com.rougelike.dungeon;

import com.rougelike.Point2D;

public class Room {
    private int id;
    private Point2D position;
    private double width;
    private double height;
    private boolean connected;

    public Room(double width, double height) {
        this.width = width;
        this.height = height;
        position = new Point2D();
        id = 0;
        connected = false;
        if (width < 0) {
            throw new IllegalArgumentException("width of room can't be less than 0");
        } else if (height < 0) {
            throw new IllegalArgumentException("height of room can't be less than 0");
        }
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
}
