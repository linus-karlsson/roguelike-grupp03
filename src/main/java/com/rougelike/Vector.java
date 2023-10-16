package com.rougelike;

public class Vector {

    private double x;
    private double y;

    public Vector() {
        x = 0;
        y = 0;
    }

    public Vector(double x, double y) {
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

    public void clone(Vector vector) {
        x = vector.getX();
        y = vector.getY();
    }

    public Vector scalarMulti(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

}
