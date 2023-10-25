package com.roguelike.magic;

import org.junit.jupiter.api.Test;

import com.roguelike.*;
import com.roguelike.races.*;
import com.roguelike.roles.*;

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

    private double setupSucceedToInvokeSpell(Role role, int randomValue) {
        Player player = new Player("Test", new Elf(), role, new Point2D());
        MagicAttack attack = new MagicAttack(new RandomInternal(randomValue));
        Magic magic = new Magic(Spell.FIREBALL);
        return attack.throwMagic(magic, player);
    }

    @Test
    void testSucceedToInvokeSpellMageFail() {
        int expected = 0;
        assertEquals(expected, setupSucceedToInvokeSpell(new Mage(), 97));
    }

    @Test
    void testSucceedToInvokeSpellMage() {
        assertTrue(setupSucceedToInvokeSpell(new Mage(), 96) > 0);
    }

    @Test
    void testSucceedToInvokeSpellAllElseFail() {
        int expected = 0;
        assertEquals(expected, setupSucceedToInvokeSpell(new Knight(), 94));
    }

    @Test
    void testSucceedToInvokeAllElseMage() {
        assertTrue(setupSucceedToInvokeSpell(new Knight(), 93) > 0);
    }

    // nedan test kommer vara mer för player.
    // @Test
    // void testMethodAttackShouldDecreasePlayersHealth() {
    // Magic magic = new Magic(Spell.TORNADO);
    // Player player = new Player("Test", new Human(), new Mage(), new Point());
    // Player enemy = new Player("Test", new Human(), new Mage(), new Point());
    // enemy.setHealth(50);
    // double expectedValue = 39;
    // MagicAttack magicAttack = (MagicAttack) magic.getType();
    // magicAttack.throwMagic(magic, player);
    // assertEquals(expectedValue, player.getHealth());
    // }

    // @Test
    // void testMethodAttackShouldDecreasePlayersHealthThrownByKnight() {
    // Magic magic = new Magic(Spell.TORNADO);
    // Player player = new Player("Test", new Human(), new Knight(), new Point());
    // Player enemy = new Player("Test", new Human(), new Mage(), new Point());
    // enemy.setHealth(50);
    // double expectedValue = 41;
    // MagicAttack magicAttack = (MagicAttack) magic.getType();
    // magicAttack.throwMagic(magic, player);
    // assertEquals(expectedValue, player.getHealth());
    // }

    // @Test
    // void
    // testMethodAttackShouldDecreasePlayersHealthToZeroInsteadOfNegativeHealth() {
    // Magic magic = new Magic(Spell.FREEZE);
    // Player player = new Player("Test", new Human(), new Mage(), new Point());
    // Player enemy = new Player("Test", new Human(), new Mage(), new Point());
    // enemy.setHealth(5);
    // double expectedValue = 0;
    // MagicAttack magicAttack = (MagicAttack) magic.getType();
    // assertEquals(expectedValue, magicAttack.throwMagic(magic, player));
    // }

}
