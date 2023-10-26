package com.roguelike;

import java.util.*;

import com.roguelike.races.Human;
import com.roguelike.roles.Mage;
import com.roguelike.enemies.*;
import com.roguelike.equipment.AirWand;
import com.roguelike.magic.Magic;
import com.roguelike.magic.Spell;

public class App {
    public static void main(String[] args) {
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
            long end = System.nanoTime() - start;
            sec += (double) end / 1_000_000_000.0;
        }
    }
}
