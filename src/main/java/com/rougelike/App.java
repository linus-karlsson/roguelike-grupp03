package com.rougelike;

import java.util.*;

public class App {
    public static void main(String[] args) {
        double minWidth = 10.0;
        double maxWidth = 30.0;
        double minHeight = 10.0;
        double maxHeight = 30.0;

        Map map = new Map();
        int roomCount = 50;
        ArrayList<Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int rows = 40;
        int columns = 40;
        ArrayList<Room> placedRooms = map.placeRoomsInArea(rooms, 30, rows, columns);
        map.connectRooms(placedRooms);
        Gridd gridd = map.getCopyOfGridd();
        int[] connectedRooms = Graph.getConnectedRooms(placedRooms, gridd);
        map.printGridd();
    }
}
