package com.roguelike;

import com.roguelike.dungeon.DungeonGenerator;

public class App {
    public static void main(String[] args) {
        double minWidth = 30.0;
        double maxWidth = 60.0;
        double minHeight = 30.0;
        double maxHeight = 60.0;
        int numberOfTriesBeforeDiscard = 30;
        int columnCount = 1000;
        int rowCount = 1000;
        double tileSize = DungeonGenerator.MIN_TILE_SIZE;
        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        int roomCount = 1000;

        double sec = 0.0;
        final double duration = 30.0;
        while (sec <= duration) {
            long start = System.nanoTime();
            dungeonGenerator.generateDungeon(roomCount, minWidth, maxWidth,
                    minHeight, maxHeight, numberOfTriesBeforeDiscard,
                    rowCount, columnCount, tileSize);
            long end = System.nanoTime() - start;
            sec += (double) end / 1_000_000_000.0;
        }
    }
}
