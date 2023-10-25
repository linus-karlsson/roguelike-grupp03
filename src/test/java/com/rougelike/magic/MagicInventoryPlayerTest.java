package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.enemies.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagicInventoryPlayerTest {

    @Test
    void testMethodAddMagicToInventoryShouldAddMagicToInventory() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        player.addMagicToInventory(magic);
        assertEquals(1, player.getMagicInventory().size());
    }

    @Test
    void testMethodAddMagicToInventoryShouldNotAddMagicToInventory() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        player.addMagicToInventory(magic);
        player.addMagicToInventory(magic);
        assertEquals(1, player.getMagicInventory().size());
    }

    @Test
    void testUseMagicWithNoArgumentShouldThrowException() {
        Player player = new Player("Test", new Point2D());
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(null);
        });
    }

    @Test
    void testUseMagicWithNullArgumentShouldThrowException() {
        Player player = new Player("Test", new Point2D());
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(null);
        });
    }

    @Test
    void testUseMagicWithMagicNotInInventoryShouldThrowException() {
        Player player = new Player("Test", new Point2D());
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(Spell.FIREBALL);
        });
    }

    @Test
    void testUseMagic() {
        Player player = new Player("Test", new Point2D());
        Entity enemy = new Troll();
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        player.useMagic(Spell.FIREBALL, enemy);
        double expectedValue = 40.0;
        assertEquals(expectedValue, enemy.getHealth());
    }

    @Test
    void testUseMagicWithHealthShouldIncresePlayersHelth() {
        Player player = new Player("Test", new Point2D());
        Magic magic = new Magic(Spell.HARMONYHEAL);
        player.addMagicToInventory(magic);
        player.setHealth(50);
        player.useMagic(Spell.HARMONYHEAL);
        double expectedValue = 60.0;
        assertEquals(expectedValue, player.getHealth());
    }
}
