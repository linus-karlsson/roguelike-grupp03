package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;
import com.rougelike.roles.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rougelike.Player;

public class RaceImpactCheckerTest {

    @Test
    void testIfPlayerElfIsImpactByElementAir() {
        Player player = new Player("Test", new Elf(), new Thief(), new Point());
        RaceImpactChecker raceImpactChecker = new RaceImpactChecker();
        assertTrue(raceImpactChecker.isPlayerImpactByAir(player)); 
    }

    @Test
    void testIfPlayerHumanNotImpactByAir() {
        Player player = new Player("Test", new Human(), new Thief(), new Point());
        RaceImpactChecker raceImpactChecker = new RaceImpactChecker();
        assertFalse(raceImpactChecker.isPlayerImpactByAir(player)); 
    }

    @Test
    void testIfPlayerOrcIsImpactByElementFire() {
        Player player = new Player("Test", new Orc(), new Thief(), new Point());
        RaceImpactChecker raceImpactChecker = new RaceImpactChecker();
        assertTrue(raceImpactChecker.isPlayerImpactByFire(player)); 
    }

}
