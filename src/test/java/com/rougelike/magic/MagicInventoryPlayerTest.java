package com.rougelike.magic;

import com.rougelike.*;

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
    void testuseMagicReturnsPositiveDouble() {
        Player player = new Player("Test", new Point());
        player.addMagicToInventory(new Magic(Spell.TORNADO));
        Double expectedValue = 10.0;
        assertEquals(expectedValue, player.useMagic("Tornado"));
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

    
}
