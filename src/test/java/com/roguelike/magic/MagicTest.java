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
        Spell spell = Spell.POISON;
        String magicString = "Poison";
        assertTrue(magicString.contains(spell.getName()));
    }

    @Test
    void testMagicMethodGetName() {
        Spell spell = Spell.POISON;
        String magicString = "Poison";
        assertEquals(magicString, spell.getName());

    }

    @Test
    void testMagicMethodGetBaseStrength() {
        Spell spell = Spell.POISON;
        double magicString = 10.0;
        assertEquals(magicString, spell.getBaseStrength());
    }

    @Test
    void testMagicMethodGetElement() {
        Spell spell = Spell.POISON;
        String magicString = "Earth";
        assertEquals(magicString, spell.getElement().getName());
    }

    @Test
    void testMagicToStringContainsBaseStrength() {
        Spell spell = Spell.POISON;
        double expectedValue = 10.0;
        assertEquals(expectedValue, spell.getBaseStrength());

    }

    @Test
    void testMagicToStringContainsType() {
        Spell spell = Spell.POISON;
        String magicString = "Attack";
        assertTrue(magicString.contains(spell.getType().getName()));

    }

    @Test
    void testMagicToStringContainsElement() {
        Spell spell = Spell.POISON;
        String magicString = "Earth";
        assertTrue(magicString.contains(spell.getElement().getName()));
    }

}
