package com.rougelike;

import java.util.*;

public class App {
    public static void main(String[] args) {
        double minWidth = 10.0;
        double maxWidth = 30.0;
        double minHeight = 10.0;
        double maxHeight = 30.0;

        Map map = new Map();
        int roomCount = 100;
        ArrayList<Map.Room> rooms = map.generateListOfRooms(roomCount, minWidth, maxWidth,
                minHeight, maxHeight);

        int rows = 40;
        int columns = 40;
        double cellSize = 5.0;
        ArrayList<Map.Room> placedRooms = map.placeRoomsInArea(rooms, 30, rows, columns, cellSize);
        Gridd gridd = map.getCopyOfGridd();

        Random random = new Random();
        Map.Room randomRoom = placedRooms.get(random.nextInt(placedRooms.size() - 1));

        Player player = new Player("TOTO", randomRoom.getPosition());

        player.setVelocity(new Vector(10.0, 0.0));
        player.updatePlayerMovement(gridd, 0.0016);
    }
}
