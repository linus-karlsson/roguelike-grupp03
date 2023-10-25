package com.rougelike.magic;

import com.rougelike.races.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RaceImpactCheckerTest {

    @Test
    void testIfElfIsImpactByElementAir() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Elf(), new ElementAir()));
    }

    @Test
    void testIfHumanNotImpactByAir() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Human(), new ElementAir()));
    }

    @Test
    void testIfOrcIsImpactByElementFire() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Orc(), new ElementFire()));
    }

    @Test
    void testIfDwarfNotImpactByElementFire() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Dwarf(), new ElementFire()));
    }

    @Test
    void testIfDwarfIsImpactByElementEarth() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Dwarf(), new ElementEarth()));
    }

    @Test
    void testIfElfNotImpactByElementEarth() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Elf(), new ElementEarth()));
    }

    @Test
    void testIfOrcIsImpactByElementWater() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Orc(), new ElementWater()));
    }

    @Test
    void testIfHumanNotImpactByElementWater() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Human(), new ElementWater()));
    }

}
