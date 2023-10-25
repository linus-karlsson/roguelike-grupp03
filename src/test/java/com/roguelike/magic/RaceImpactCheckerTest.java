package com.roguelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.roguelike.magic.ElementAir;
import com.roguelike.magic.ElementEarth;
import com.roguelike.magic.ElementFire;
import com.roguelike.magic.ElementWater;
import com.roguelike.magic.RaceImpactChecker;
import com.roguelike.races.*;

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
