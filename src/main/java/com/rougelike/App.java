package com.rougelike;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Map map = new Map();

        double minWidth = 10.0;
        double maxWidth = 30.0;
        double minHeight = 10.0;
        double maxHeight = 30.0;

        int roomCount = 10;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        // 800 * 800 pixels
        int rows = 80;
        int columns = 80;
        double cellSize = 5.0;
        map.placeRoomsInArea(rooms, rows, columns, cellSize);

        map.printMap(columns);

    }
}
