package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.enemies.*; 
import com.rougelike.equipment.*;   


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagicInventoryPlayerTest {

    @Test
    void testMethodAddMagicToInventoryShouldAddMagicToInventory() {
        Magic magic = new Magic(Spell.HEAL);
        Player player = new Player("Test", new Point()); 
        player.addMagicToInventory(magic);
        assertTrue(player.hasMagicKnowledge(magic.getName()));
    }

    @Test
    void testMethodAddMagicToInventoryShouldNotAddMagicToInventory() {
        Magic magic = new Magic(Spell.HEAL);
        Player player = new Player("Test", new Point()); 
        player.addMagicToInventory(magic);
        player.addMagicToInventory(magic);
        assertEquals(1, player.getMagicInventory().size());
    }

    @Test
    void testUseMagicWithNoArgumentShouldThrowException() {
        Player player = new Player("Test", new Point());
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic("");
        });
    }

    @Test
    void testUseMagicWithNullArgumentShouldThrowException() {
        Player player = new Player("Test", new Point());
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic(null);
        });
    }

    @Test
    void testUseMagicWithMagicNotInInventoryShouldThrowException() {
        Player player = new Player("Test", new Point());
        assertThrows(IllegalArgumentException.class, () -> {
            player.useMagic("Tornado");
        });
    }

    @Test
    void testUseMagic() {
        Player player = new Player( "Test", new Point());
        Entity enemy = new Troll();
        Magic magic = new Magic(Spell.FIREBALL);
        player.addMagicToInventory(magic);
        player.useMagic("Fireball", enemy);
        double expectedValue = 40.0;
        assertEquals(expectedValue, enemy.getHealth());
    }

    @Test
    void testUseMagicWithHealthShouldIncresePlayersHelth() {
        Player player = new Player( "Test", new Point());
        Magic magic = new Magic(Spell.HEAL);
        player.addMagicToInventory(magic);
        player.setHealth(50);
        player.useMagic("Heal");
        double expectedValue = 60.0;
        assertEquals(expectedValue, player.getHealth());
    }
}
    

