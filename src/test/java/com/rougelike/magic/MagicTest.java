package com.rougelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rougelike.Player;
import com.rougelike.magic.Magic;

public class MagicTest {

    @Test
    void testMagicInstanceCreation() {
        Spell spell = Spell.FIREBALL;
        Magic magic = new Magic(spell);
        assertNotNull(magic);
    }

    @Test
    void testMagicToStringContainsAllValues()   {
        Spell spell = Spell.POISON;
        Magic magic = new Magic(spell);
        String magicString = magic.toString();
        assertTrue(magicString.contains(spell.getName()));
        assertTrue(magicString.contains(Integer.toString(spell.getBaseStrength())));
        assertTrue(magicString.contains(spell.getType()));
        assertTrue(magicString.contains(spell.getElement().getName()));
    }   


}
