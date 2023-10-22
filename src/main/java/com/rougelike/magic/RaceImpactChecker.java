package com.rougelike.magic;

import com.rougelike.Player;
import com.rougelike.races.*;
import com.rougelike.races.Orc;

public class RaceImpactChecker {

    static boolean isPlayerImpactByAir(Player player) {
        return player.getRace() instanceof Elf || player.getRace() instanceof Orc;
    }

    static boolean isPlayerImpactByFire(Player player) {
        return player.getRace() instanceof Orc || player.getRace() instanceof Elf;
    }

    static boolean isPlayerImpactByWater(Player player) {
        return player.getRace() instanceof Dwarf || player.getRace() instanceof Orc;
    }

    static boolean isPlayerImpactByEarth(Player player) {
        return player.getRace() instanceof Dwarf;
    }

}
