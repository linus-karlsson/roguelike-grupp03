package com.rougelike.magic;
import com.rougelike.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MagicInvokerTest {

    @Test
    void testSpellValueReturnsCorrectValueWhenPlayerStartLevel() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Point());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.MagicValue(magic, player));}

    @Test
    void testSpellValueReturnsCorrectValueWhenPlayerLevelTwo() {
        Magic magic = new Magic(Spell.FREEZE);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Point());
        player.setLevel(2);
        double expectedValue = 14.4;
        assertEquals(expectedValue, magicInvoker.MagicValue(magic, player));
    }

}
