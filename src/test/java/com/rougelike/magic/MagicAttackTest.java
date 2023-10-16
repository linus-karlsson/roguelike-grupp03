package com.rougelike.magic;

import com.rougelike.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MagicAttackTest {

    @Test
    void testSpellAttackShouldBeInstanceOfMagicInvoker() {
        Magic magic = new Magic(Spell.POISON);
        assertTrue(magic.getType() instanceof MagicInvoker);
    }

    @Test
    void testSpellAttackShouldBeInstanceOfAttack() {
        Magic magic = new Magic(Spell.FIREBALL);
        assertTrue(magic.getType() instanceof MagicAttack);
    }

    @Test
    void testMethodAttackShouldDecreasePlayersHealth() {
        Magic magic = new Magic(Spell.TORNADO);
        Player player = new Player("Test"); 
        player.setHealth(50);
        double expectedValue = 40;
        magic.getType().throwMagic(magic, player);
        assertEquals(expectedValue, player.getHealth());
    }

    @Test
    void testMethodAttackShouldDecreasePlayersHealthToZeroInsteadOfNegativeHealth() {
        Magic magic = new Magic(Spell.FREEZE);
        Player player = new Player("Test"); 
        player.setHealth(5);
        double expectedValue = 0;
        magic.getType().throwMagic(magic, player);
        assertEquals(expectedValue, player.getHealth());
    }
    
}
