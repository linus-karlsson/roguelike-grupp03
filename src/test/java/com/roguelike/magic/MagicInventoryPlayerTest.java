package com.roguelike.magic;

import org.junit.jupiter.api.Test;

import com.roguelike.*;
import com.roguelike.enemies.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.aMapWithSize;

public class MagicInventoryPlayerTest {

    private final Player player = new Player("Test", new Point2D());

    @Test
    void testMethodGetMagicInventoryShouldReturnEmptyMap() {
        assertThat(player.getMagicInventory(), anEmptyMap());
    }

    @Test
    void testMethodGetMagicInventoryShouldReturnMapSizeOne() {
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        assertThat(player.getMagicInventory(), aMapWithSize(1));
    }

    @Test
    void testMethodGetMagicInventorySameSpellTwiceShouldReturnSizeOne() {
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        player.addMagicToInventory(magic);
        assertThat(player.getMagicInventory(), aMapWithSize(1));
    }

    @Test
    void testMethodGetMagicInventoryTwoSpellsShouldReturnMapSizeTwo() {
        Magic magic = new Magic(Spell.FIREBALL);
        Magic magic2 = new Magic(Spell.FREEZE);
        player.addMagicToInventory(magic);
        player.addMagicToInventory(magic2);
        player.addMagicToInventory(magic2);
        assertThat(player.getMagicInventory(), aMapWithSize(2));
    }


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
