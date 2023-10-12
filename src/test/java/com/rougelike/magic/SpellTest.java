package com.rougelike.magic;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SpellTest {

    @Test
    void testSpellInstanceCreation() {
        Spell spell = Spell.FIREBALL;
        assertNotNull(spell);
    }

    @Test
    void testSpellInstanceReturnsAllCorrectValues() {
        Spell spell = Spell.FREEZE;
        String expectedName = "Freeze";
        int expectedBaseStrength = 10;
        String expectedType = "Attack";
        String expectedElement = "Water";
        assertEquals(expectedName, spell.getName());
        assertEquals(expectedBaseStrength, spell.getBaseStrength());
        assertEquals(expectedType, spell.getType());
        assertEquals(expectedElement, spell.getElement());
    }
    
}
