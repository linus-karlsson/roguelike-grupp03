package com.roguelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.roguelike.*;
import com.roguelike.races.*;
import com.roguelike.roles.*;

public class MagicInvokerTest {

    @Test
    void testSpellValueReturnsCorrectValueWhenPlayerStartLevel() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point2D());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testSpellValueReturnsCorrectValueWhenPlayerLevelTwo() {
        Magic magic = new Magic(Spell.FREEZE);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point2D());
        player.setLevel(2);
        double expectedValue = 12.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfPlayerRoleMageMagicValueShouldReturnTenPercentHigher() {
        Magic magic = new Magic(Spell.POISON);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Mage(), new Point2D());
        double expectedValue = 11.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfPlayerRoleKnightMagicValueShouldReturnTenPercentLower() {
        Magic magic = new Magic(Spell.FIREBALL);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Knight(), new Point2D());
        double expectedValue = 9.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }    

    @Test
    void testIfNeutralElementTypeNotImpactInMagicValue() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point2D());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfElementAirThrownByElfIncreaseMagicValueByFivePercent() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        double expectedValue = 10.5;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfElementAirThrownByOrchDecreaseValue() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Orc(), new Thief(), new Point2D());
        double expectedValue = 9.5;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfElementAirThrownByDwarfNotImpactValue() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Dwarf(), new Thief(), new Point2D());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

}
