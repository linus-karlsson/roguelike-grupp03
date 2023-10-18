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
        assertTrue(player.hasMagicKnowledge(magic));
    }

    @Test
    void testMethodAddMagicToInventoryShouldNotAddMagicToInventory() {
        Magic magic = new Magic(Spell.HEAL);
        Player player = new Player("Test", new Point()); 
        player.addMagicToInventory(magic);
        player.addMagicToInventory(magic);
        assertEquals(1, player.getMagicInventory().size());
    }
    
}
