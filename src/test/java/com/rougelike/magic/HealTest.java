package com.rougelike.magic;

import com.rougelike.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HealTest {

    @Test
    void testSpellHealShouldBeInstanceOfMagicInvoker() {
        Magic magic = new Magic(Spell.HEAL);
        assertTrue(magic.getType() instanceof MagicInvoker);
    }

    @Test
    void testSpellHealShouldBeInstanceOfHeal() {
        Magic magic = new Magic(Spell.HEAL);
        assertTrue(magic.getType() instanceof Heal);
    }

    @Test
    void testMethodHealShouldIncreasePlayersHealth() {
        Magic magic = new Magic(Spell.HEAL);
        Player player = new Player("Test"); 
        player.setHealth(50);
        double expectedValue = 60;
        magic.getType().throwMagic(magic, player);
        assertEquals(expectedValue, player.getHealth());
    }

    @Test
    void testMethodHealShouldIncreasePlayersHealthToMax() {
        Magic magic = new Magic(Spell.HEAL);
        Player player = new Player("Test"); 
        player.setHealth(95);
        double expectedValue = 100;
        magic.getType().throwMagic(magic, player);
        assertEquals(expectedValue, player.getHealth());
    }
}
