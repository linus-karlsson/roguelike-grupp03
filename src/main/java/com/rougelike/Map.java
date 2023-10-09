package com.rougelike;

import java.util.Random;

public class Map {
    Random random;

    public class Room {
        private double width;
        private double height;

        public Room(double width, double height) {
            this.width = width;
            this.height = height;
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

    public Room generateRoom(double minWidth, double maxWidth, double minHeight, double maxHeight) {
        Room room = new Room(randomDoubleInBounds(minWidth, maxWidth), randomDoubleInBounds(minHeight, maxHeight));
        return room;
    }

    private double randomDoubleInBounds(double low, double high) {
        return (random.nextDouble() * (high - low)) + low;
    }
}
