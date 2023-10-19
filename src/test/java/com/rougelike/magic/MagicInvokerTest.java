package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;
import com.rougelike.roles.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MagicInvokerTest {

    @Test
    void testSpellValueReturnsCorrectValueWhenPlayerStartLevel() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testSpellValueReturnsCorrectValueWhenPlayerLevelTwo() {
        Magic magic = new Magic(Spell.FREEZE);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point());
        player.setLevel(2);
        double expectedValue = 14.4;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfPlayerRoleMageMagicValueShouldReturnTenPercentHigher() {
        Magic magic = new Magic(Spell.POISON);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Mage(), new Point());
        double expectedValue = 11.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfPlayerRoleKnightMagicValueShouldReturnTenPercentLower() {
        Magic magic = new Magic(Spell.FIREBALL);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Knight(), new Point());
        double expectedValue = 9.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfNeutralElementTypeNotImpactInMagicValue() {
        Magic magic = new Magic(Spell.HEAL);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfElementAirThrownByElfIncreaseMagicValueByFivePercent() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Elf(), new Thief(), new Point());
        double expectedValue = 10.5;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfElementAirThrownByOrchDecreaseValue() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Orc(), new Thief(), new Point());
        double expectedValue = 9.5;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

}
