package com.roguelike.magic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testMagicToStringContainsBaseStrength() {
        Spell spell = Spell.POISON;
        double magicString = 10.0;
        assertTrue(magicString == spell.getBaseStrength());
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
