package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.roles.*;
import com.rougelike.races.*;

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
        Player player = new Player("Test", new Human(), new Mage(), new Point());
        player.setHealth(50);
        double expectedValue = 39;
        magic.getType().throwMagic(magic, player);
        assertEquals(expectedValue, player.getHealth());
    }

    @Test
    void testMethodAttackShouldDecreasePlayersHealthThrownByKnight() {
        Magic magic = new Magic(Spell.TORNADO);
        Player player = new Player("Test", new Human(), new Knight(), new Point());
        player.setHealth(50);
        double expectedValue = 41;
        magic.getType().throwMagic(magic, player);
        assertEquals(expectedValue, player.getHealth());
    }

    @Test
    void testMethodAttackShouldDecreasePlayersHealthToZeroInsteadOfNegativeHealth() {
        Magic magic = new Magic(Spell.FREEZE);
        Player player = new Player("Test", new Human(), new Mage(), new Point());
        player.setHealth(5);
        double expectedValue = 0;
        magic.getType().throwMagic(magic, player);
        assertEquals(expectedValue, player.getHealth());
    }

}
