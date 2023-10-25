package com.roguelike.magic;

import org.junit.jupiter.api.Test;

import com.roguelike.*;
import com.roguelike.enemies.*;

import static org.junit.jupiter.api.Assertions.*;

public class MagicInventoryPlayerTest {

    private final Player player = new Player("Test", new Point2D());

    @Test
    void testMethodAddMagicToInventoryShouldAddMagicToInventory() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        player.addMagicToInventory(magic);
        assertEquals(1, player.getMagicInventory().size());
    }

    @Test
    void testMethodAddMagicToInventoryShouldNotAddMagicToInventory() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        player.addMagicToInventory(magic);
        player.addMagicToInventory(magic);
        assertEquals(1, player.getMagicInventory().size());
    }

    @Test
    void testUseMagicWithNoArgumentShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(null);
        });
    }

    @Test
    void testUseMagicWithNullArgumentShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(null);
        });
    }

    @Test
    void testUseMagicWithMagicNotInInventoryShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(Spell.FIREBALL);
        });
    }

    @Test
    void testUseMagicreturnRightHealth() {
        Entity enemy = new Troll();
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        player.useMagic(Spell.FIREBALL, enemy);
        double expectedValue = 40.0;
        assertEquals(expectedValue, enemy.getHealth());
    }

    @Test
    void testUseMagicWithHealthShouldIncresePlayersHelth() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        player.addMagicToInventory(magic);
        player.setHealth(50);
        player.useMagic(Spell.HARMONYHEAL);
        double expectedValue = 60.0;
        assertEquals(expectedValue, player.getHealth());
    }

    @Test
    void testUseMagicWithTwoArgumentsThowsExceptionIfFirstArgumentIsNull() {
        Entity enemy = new Troll();
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(null, enemy);
        });
    }

    @Test
    void testUseMagicWithTwoArgumentsThowsExceptionIfSecondArgumentIsNull() {
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(Spell.FIREBALL, null);
        });
    }

    @Test
    void testUseMagicWithTwoArgumentsThowsExceptionIfSpellArgumentIsNotInInventory() {
        Entity enemy = new Troll();
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(Spell.HARMONYHEAL, enemy);
        });
    }

    @Test
    void testUseMagicWithTwoArgumentsThrowExceptionIfSpellNotAttack() {
        Entity enemy = new Troll();
        Magic magic = new Magic(Spell.HARMONYHEAL);
        player.addMagicToInventory(magic);
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(Spell.HARMONYHEAL, enemy);
        });
    }

    @Test
    void testUseMagicWithTwoArgumentsThrowExceptionIfEnemyIsDead() {
        Entity enemy = new Troll();
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        enemy.setIsDead(true);
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(Spell.FIREBALL, enemy);
        });
    }
}
