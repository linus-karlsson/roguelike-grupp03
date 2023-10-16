package com.rougelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MagicTest {

    @Test
    void testMagicInstanceCreation() {
        Magic magic = new Magic(Spell.FIREBALL);
        assertNotNull(magic);
    }

    @Test
    void testMagicToStringContainsAllValues()   {
        Spell spell = Spell.POISON;
        Magic magic = new Magic(spell);
        String magicString = magic.toString();
        assertTrue(magicString.contains(spell.getName()));
        assertTrue(magicString.contains(Double.toString(spell.getBaseStrength())));
        assertTrue(magicString.contains(spell.getType().getName()));
        assertTrue(magicString.contains(spell.getElement().getName()));
    }   


}
