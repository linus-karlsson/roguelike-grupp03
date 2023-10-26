package com.roguelike.magic;

import com.roguelike.Player;
import com.roguelike.Point2D;
import com.roguelike.enemies.Bandit;
import com.roguelike.races.Elf;
import com.roguelike.roles.Mage;

import java.util.ArrayList;

public class ProfilerMagic {

    public static void main(String[] args) {
        ArrayList<Bandit> bandits = new ArrayList<>();

        Player player = new Player("TestPlayer", new Elf(), new Mage(), new Point2D(1, 1));
        player.setLevel(2);
        player.addMagicToInventory(new Magic(Spell.TORNADO));
        double sec = 0.0;
        final double duration = 30.0;
        while (sec <= duration) {
            long start = System.nanoTime();
            for (int i = 0; i < 1000000; i++) {
                bandits.add(new Bandit());
            }
            for (Bandit bandit : bandits) {
                System.out.println(bandit.getHealth());

                do {
                    player.useMagic(Spell.TORNADO, bandit);
                    System.out.println(bandit.getHealth());
                } while (!bandit.isDead());
                System.out.println(bandit.isDead());
                long end = System.nanoTime() - start;
                sec += (double) end / 1_000_000_000.0;
            }
        }

    }}
