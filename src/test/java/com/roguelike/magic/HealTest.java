package com.roguelike.magic;

import org.junit.jupiter.api.Test;

import com.roguelike.*;

import static org.junit.jupiter.api.Assertions.*;

public class HealTest {

    @Test
    void testHealShouldBeInstanceOfMagicInvoker() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        assertTrue(magic.getType() instanceof MagicInvoker);
    }

    @Test
    void testHealShouldBeInstanceOfHeal() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        assertTrue(magic.getType() instanceof Heal);
    }

    @Test
    void testHealShouldHaveCorrectName() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        assertEquals("Heal", magic.getType().getName());
    }

    @Test
    void testMethodThrowMagicWhenMagicIsNullShouldThrowIllegalArgumentException() {
        Magic magic = null;
        Player player = new Player("Test", new Point2D());
        Heal heal = new Heal();
        assertThrows(IllegalArgumentException.class, () -> heal.throwMagic(magic, player));
    }

    @Test
    void testMethodThrowMagicWhenPlayerIsNullShouldThrowIllegalArgumentException() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = null;
        Heal heal = new Heal();
        assertThrows(IllegalArgumentException.class, () -> heal.throwMagic(magic, player));
    }

    @Test
    void testMethodThrowMagicIfPlayerHealthIsMaxShouldReturnMaxHealth() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        Heal heal = (Heal) magic.getType();
        player.setHealth(100);
        double expectedValue = 100;
        assertEquals(expectedValue, heal.throwMagic(magic, player));
    }

    @Test
    void testMethodThrowMagicShouldIncreasePlayersHealth() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        Heal heal = (Heal) magic.getType();
        player.setHealth(50);
        double expectedValue = 60;
        assertEquals(expectedValue, heal.throwMagic(magic, player));
    }

    @Test
    void testMethodThrowMagicShouldIncreasePlayersHealthToMax() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        Heal heal = (Heal) magic.getType();
        player.setHealth(95);
        double expectedValue = 100;
        assertEquals(expectedValue, heal.throwMagic(magic, player));
    }

}
