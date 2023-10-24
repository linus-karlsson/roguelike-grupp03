package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;
import com.rougelike.roles.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RaceImpactCheckerTest {

    @Test
    void testIfPlayerElfIsImpactByElementAir() {
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        assertTrue(RaceImpactChecker.isPlayerImpact(new Elf(), new ElementAir()));
    }

    @Test
    void testIfPlayerHumanNotImpactByAir() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Human(), new ElementAir()));
    }

    @Test
    void testIfPlayerOrcIsImpactByElementFire() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Orc(), new ElementFire()));
    }

    @Test
    void testIfPlayerDwarfNotImpactByElementFire() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Dwarf(), new ElementFire()));
    }

    @Test
    void testIfPlayerDwarfIsImpactByElementEarth() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Dwarf(), new ElementEarth()));
    }

    @Test
    void testIfPlayerElfNotImpactByElementEarth() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Elf(), new ElementEarth()));
    }

    @Test
    void testIfPlayerOrcIsImpactByElementWater() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Orc(), new ElementWater()));
    }

    @Test
    void testIfPlayerHumanNotImpactByElementWater() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Human(), new ElementWater()));
    }

}
