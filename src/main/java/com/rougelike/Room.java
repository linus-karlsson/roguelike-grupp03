package com.rougelike;

public class Room {
    private int id;
    private Point position;
    private double width;
    private double height;

    public Room(double width, double height) {
        this.width = width;
        this.height = height;
        position = new Point();
        id = 0;
    }

    public Room(Room otherRoom) {
        this(otherRoom.getWidth(), otherRoom.getHeight());
        id = otherRoom.id;
        position.clone(otherRoom.getPosition());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
