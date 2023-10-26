package com.roguelike;

import java.util.*;

import com.roguelike.races.Human;
import com.roguelike.roles.Knight;
import com.roguelike.roles.Mage;
import com.roguelike.enemies.*;
import com.roguelike.equipment.AirWand;
import com.roguelike.magic.Magic;
import com.roguelike.magic.Spell;

public class App {
    public static void main(String[] args) {
        List<Entity> enemies = new LinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            enemies.add(new Bandit());
            enemies.add(new Troll());
            enemies.add(new Witch());
        }
        Player player = new Player("Profiler victim", new Human(), new Knight(), new Point2D());
        player.addMagicToInventory(new Magic(Spell.FIREBALL));

        double sec = 0.0;
        final double duration = 30.0;
        while (sec <= duration) {
            long start = System.nanoTime();
            for (Entity enemy : enemies) {
                enemy.setIsDead(false);
                player.attackEnemyWithWeapon(enemy);
                enemy.setIsDead(false);
                player.useMagic(Spell.FIREBALL, enemy);
            }
            long end = System.nanoTime() - start;
            sec += (double) end / 1_000_000_000.0;
        }
    }
}
