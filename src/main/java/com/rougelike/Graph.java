package com.rougelike;

import java.util.*;

public class Graph {

    private static void addAdjacentTiles(Stack<Gridd.Index> stack, Gridd gridd, Gridd.Index index) {
        for (int i = -1; i < 2; i += 2) {
            stack.add(gridd.new Index(index.row + i, index.column));
        }
        for (int i = -1; i < 2; i += 2) {
            stack.add(gridd.new Index(index.row, index.column + i));
        }
    }

    private static int[][] getGriddOfZeros(int rows, int columns) {
        int[][] gridd = new int[rows][columns];
        for (int row = 0; row < gridd.length; row++) {
            for (int column = 0; column < gridd[0].length; column++) {
                gridd[row][column] = 0;
            }
        }
        return gridd;
    }

    private static int[] getArrayOfNegtiveOnes(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = -1;
        }
        return result;
    }

    public static int[] getConnectedRooms(ArrayList<Room> placedRooms, Gridd gridd) {
        Room startRoom = placedRooms.get(0);

        Stack<Gridd.Index> stack = new Stack<>();
        stack.add(gridd.getGriddIndexBasedOnPosition(startRoom.getPosition()));

        int[] roomsFound = getArrayOfNegtiveOnes(placedRooms.size());
        roomsFound[startRoom.getId()] = startRoom.getId();

        int[][] visited = getGriddOfZeros(gridd.getRowCount(), gridd.getColumnCount());
        while (!stack.isEmpty()) {
            Gridd.Index currentIndex = stack.pop();
            int tileValue = gridd.getTile(currentIndex);
            if (tileValue < 0 || visited[currentIndex.row][currentIndex.column] == 1) {
                continue;
            }
            visited[currentIndex.row][currentIndex.column] = 1;
            if (tileValue < placedRooms.size()) {
                roomsFound[tileValue] = tileValue;
            }
            addAdjacentTiles(stack, gridd, currentIndex);
        }
        return roomsFound;
    }

}
