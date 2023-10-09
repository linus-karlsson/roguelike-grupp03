package com.rougelike;

public class Map {

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

    }

    public Room generateRoom(double minWidth, double maxWidth, double minHeight, double maxHeight) {
        Room room = new Room(minWidth, minHeight);
        return room;
    }
}
