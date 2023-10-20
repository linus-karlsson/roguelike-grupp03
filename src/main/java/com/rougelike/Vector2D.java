package com.rougelike;

public class Vector2D {

    private double x;
    private double y;

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
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

    public void clone(Vector2D vector) {
        x = vector.getX();
        y = vector.getY();
    }

    public Vector2D scalarMulti(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

}
