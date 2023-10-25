package com.roguelike.magic;

import org.junit.jupiter.api.Test;

import com.roguelike.*;

import static org.junit.jupiter.api.Assertions.*;

public class HealTest {

    @Test
    void testSpellHealShouldBeInstanceOfMagicInvoker() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        assertTrue(magic.getType() instanceof MagicInvoker);
    }

    @Test
    void testSpellHealShouldBeInstanceOfHeal() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        assertTrue(magic.getType() instanceof Heal);
    }

    @Test
    void testMethodHealShouldIncreasePlayersHealth() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        Heal heal = (Heal) magic.getType();
        player.setHealth(50);
        double expectedValue = 60;
        assertEquals(expectedValue, heal.throwMagic(magic, player));
    }

    @Test
    void testMethodHealShouldIncreasePlayersHealthToMax() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        Heal heal = (Heal) magic.getType();
        player.setHealth(95);
        double expectedValue = 100;
        assertEquals(expectedValue, heal.throwMagic(magic, player));
    }
}
