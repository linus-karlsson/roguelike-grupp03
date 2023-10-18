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
        Magic magic = new Magic(Spell.HEAL);
        Player player = new Player("Test", new Elf(), new Thief(), new Point());
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
        Player player = new Player("Test", new Elf(), new Thief(), new Point());
        double expectedMultiplier = 1.05;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }

    @Test
    void testGetMultiplierReturnsCorrectValueIfPlayerIsDwarfAndElementtypeAir() {
        Magic magic = new Magic(Spell.TORNADO);
        Player player = new Player("Test", new Dwarf(), new Thief(), new Point());
        double expectedMultiplier = 1.00;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier(player));
    }

}
