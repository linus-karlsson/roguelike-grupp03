package com.rougelike;

public class Point {
    private double x;
    private double y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point otherPoint) {
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

    public void clone(Point otherPoint) {
        x = otherPoint.getX();
        y = otherPoint.getY();
    }

    public Vector minus(Point otherPoint) {
        return new Vector(x - otherPoint.getX(), y - otherPoint.getY());
    }

    public Point plus(Vector vector) {
        return new Point(x + vector.getX(), y + vector.getY());
    }

}
