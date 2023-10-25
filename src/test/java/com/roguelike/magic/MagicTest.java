package com.roguelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MagicTest {

    @Test
    void testMagicInstanceCreation() {
        Magic magic = new Magic(Spell.FIREBALL);
        assertNotNull(magic);
    }

    @Test
    void testMagicToStringContainsName() {
       Magic magic  = new Magic(Spell.POISON);
        String magicString = "Poison";
        assertTrue(magicString.contains(magic.getName()));
    }

    @Test
    void testMagicMethodGetName() {
        Magic magic  = new Magic(Spell.POISON);
        String magicString = "Poison";
        assertEquals(magicString, magic.getName());

    }

    @Test
    void testMagicMethodGetBaseStrength() {
        Magic magic  = new Magic(Spell.POISON);
        double magicString = 10.0;
        assertEquals(magicString, magic.getBaseStrength());
    }

    @Test
    void testMagicMethodGetElement() {
        Magic magic  = new Magic(Spell.POISON);
        String magicString = "Earth";
        assertEquals(magicString, magic.getElement().getName());
    }

    @Test
    void testMagicToStringContainsBaseStrength() {
        Magic magic  = new Magic(Spell.POISON);
        double expectedValue = 10.0;
        assertEquals(expectedValue, magic.getBaseStrength());

    }

    @Test
    void testMagicToStringContainsType() {
        Magic magic  = new Magic(Spell.POISON);
        String magicString = "Attack";
        assertTrue(magicString.contains(magic.getType().getName()));

    }

    @Test
    void testMagicToStringContainsElement() {
        Magic magic  = new Magic(Spell.POISON);
        String magicString = "Earth";
        assertTrue(magicString.contains(magic.getElement().getName()));
    }

}
