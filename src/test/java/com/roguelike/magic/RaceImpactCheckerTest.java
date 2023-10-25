package com.roguelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.roguelike.races.*;

public class RaceImpactCheckerTest {

    @Test
    void testCreationOfInstanceOfRaceImpactChecker() {
        RaceImpactChecker raceImpactChecker = new RaceImpactChecker();
        assertNotNull(raceImpactChecker);
    }

    @Test
    void testIfElfIsImpactByElementAir() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Elf(), new ElementAir()));
    }

    @Test
    void testIfElfIsImpactByElementFire() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Elf(), new ElementFire()));
    }

    @Test
    void testIfElfNotImpactByElementWater() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Elf(), new ElementWater()));
    }

    @Test
    void testIfElfNotImpactByElementEarth() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Elf(), new ElementEarth()));
    }

    @Test
    void testIfOrcIsImpactByElementAir() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Orc(), new ElementAir()));
    }

    @Test
    void testIfOrcIsImpactByElementFire() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Orc(), new ElementFire()));
    }

    @Test
    void testIfOrcIsImpactByElementEarth() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Orc(), new ElementEarth()));
    }

    @Test
    void testIfOrcIsImpactByElementWater() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Orc(), new ElementWater()));
    }

    @Test
    void testIfDwarfNotImpactByElementAir() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Dwarf(), new ElementAir()));
    }

    @Test
    void testIfDwarfNotImpactByElementFire() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Dwarf(), new ElementFire()));
    }

    @Test
    void testIfDwarfIsImpactByElementWater() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Dwarf(), new ElementWater()));
    }

    @Test
    void testIfDwarfIsImpactByElementEarth() {
        assertTrue(RaceImpactChecker.isPlayerImpact(new Dwarf(), new ElementEarth()));
    }

        @Test
    void testIfHumanNotImpactByAir() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Human(), new ElementAir()));
    }

    @Test
    void testIfHumanNotImpactByFire() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Human(), new ElementFire()));
    }

     @Test
    void testIfHumanNotImpactByElementWater() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Human(), new ElementWater()));
    }

        @Test
    void testIfHumanNotImpactByEarth() {
        assertFalse(RaceImpactChecker.isPlayerImpact(new Human(), new ElementEarth()));     
    }

}
