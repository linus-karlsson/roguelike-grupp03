package com.rougelike.magic;

import com.rougelike.Player;
import com.rougelike.races.Elf;
import com.rougelike.races.Orc;

public class RaceImpactChecker {

    boolean isPlayerImpactByAir(Player player) {
        return player.getRace() instanceof Elf || player.getRace() instanceof Orc;
    }

        boolean isPlayerImpactByFire(Player player) {
        return player.getRace() instanceof Orc || player.getRace() instanceof Elf;
    }
}
