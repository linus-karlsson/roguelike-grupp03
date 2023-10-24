package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;
import com.rougelike.roles.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MagicElementTypeTest {

    @Test
    @DisplayName("Test MagicElementType instance creation")
    void testGetMultiplierReturnsCorrectValueWhenNeutral() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.0;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }

    @Test
    void testMagicTornadoInstanceOfElementtypeAir() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicElementType expectedElement = new ElementAir();
        assertEquals(expectedElement.getClass(), magic.getElement().getClass());
    }

    @Test
    void testgetMultiplerReturnsCorrectValueIfPlayerIsElfAndElementtypeAir() {
        Magic magic = new Magic(Spell.TORNADO);
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.05;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }

    @Test
    void testGetMultiplierReturnsCorrectValueIfPlayerIsDwarfAndElementtypeAir() {
        Magic magic = new Magic(Spell.TORNADO);
        Player player = new Player("Test", new Dwarf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.00;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }

    @Test
    void testGetMultiplierReturnsCorrectValueIfPlayerIsOrcAndElementtypeAir() {
        Magic magic = new Magic(Spell.TORNADO);
        Player player = new Player("Test", new Orc(), new Thief(), new Point2D());
        double expectedMultiplier = 0.95;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }

    @Test
    void testGetMultiplierReturnsCorrectValueIfPlayerIsDwarfAndElementEarth() {
        Magic magic = new Magic(Spell.POISON);
        Player player = new Player("Test", new Dwarf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.05;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }

    @Test
    void testGetMultiplierRetunsCorrectValueIfPlayerIsHumanAndElementEarth() {
        Magic magic = new Magic(Spell.POISON);
        Player player = new Player("Test", new Human(), new Thief(), new Point2D());
        double expectedMultiplier = 1.00;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }

    @Test
    void testGetMultiplierReturnsCorrectValueIfPlayerIsOrcAndElementEarth() {
        Magic magic = new Magic(Spell.POISON);
        Player player = new Player("Test", new Orc(), new Thief(), new Point2D());
        double expectedMultiplier = 0.95;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }
}
