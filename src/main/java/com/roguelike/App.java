package com.roguelike;

import java.util.*;

import com.roguelike.dungeon.DungeonGenerator;
import com.roguelike.races.Human;
import com.roguelike.roles.Mage;
import com.roguelike.enemies.*;
import com.roguelike.equipment.AirWand;
import com.roguelike.magic.Magic;
import com.roguelike.magic.Spell;

public class App {
    public static void main(String[] args) {
        /*
         * double minWidth = 30.0;
         * double maxWidth = 60.0;
         * double minHeight = 30.0;
         * double maxHeight = 60.0;
         * int numberOfTriesBeforeDiscard = 30;
         * int columnCount = 1000;
         * int rowCount = 1000;
         * double tileSize = DungeonGenerator.MIN_TILE_SIZE;
         * DungeonGenerator dungeonGenerator = new DungeonGenerator();
         * int roomCount = 1000;
         */
        List<Entity> enemies = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            enemies.add(new Bandit());
        }
        for (int i = 0; i < 100; i++) {
            enemies.add(new Troll());
        }
        for (int i = 0; i < 100; i++) {
            enemies.add(new Witch());
        }
        Collections.shuffle(enemies);
        Player player = new Player("Profiler victim", new Human(), new Mage(), new Point2D());
        player.addWeaponToInventory(new AirWand());
        player.addMagicToInventory(new Magic(Spell.FIREBALL));

        double sec = 0.0;
        final double duration = 30.0;
        while (sec <= duration) {
            long start = System.nanoTime();
            // dungeonGenerator.generateDungeon(roomCount, minWidth, maxWidth,
            // minHeight, maxHeight, numberOfTriesBeforeDiscard,
            // rowCount, columnCount, tileSize);
            long end = System.nanoTime() - start;
            sec += (double) end / 1_000_000_000.0;
        }
    }
}
