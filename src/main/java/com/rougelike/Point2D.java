package com.rougelike;

public class Point2D {
    private double x;
    private double y;

    public Point2D() {
        x = 0;
        y = 0;
    }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D otherPoint) {
        this(otherPoint.getX(), otherPoint.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double newX) {
        x = newX;
    }

    public void setY(double newY) {
        y = newY;
    }

    public void clone(Point2D otherPoint) {
        x = otherPoint.getX();
        y = otherPoint.getY();
    }

    public Vector2D minus(Point2D otherPoint) {
        return new Vector2D(x - otherPoint.getX(), y - otherPoint.getY());
    }

    public Point2D plus(Vector2D vector) {
        return new Point2D(x + vector.getX(), y + vector.getY());
    }

    public boolean equals(Object obj) {
        if (obj instanceof Point2D) {
            Point2D other = (Point2D) obj;
            return x == other.getX() && y == other.getY();
        }
        return false;
    }

}
