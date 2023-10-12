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
        String expectedElementName = "Water";
        assertEquals(expectedName, spell.getName());
        assertEquals(expectedBaseStrength, spell.getBaseStrength());
        assertEquals(expectedType, spell.getType());
        assertEquals(expectedElementName, spell.getElement().getName());
    }

    @Test
    void testSpelInstanceElementShouldBeInstanceOfMagicElementType() {
        Spell spell = Spell.HEAL;
        assertInstanceOf(MagicElementType.class, spell.getElement(), "Spell element should be an instance of MagicElementType");
    }

    @Test
    void testSpelInstanceElementShouldBeInstanceOfWaterType() {
        Spell spell = Spell.FREEZE;
        assertInstanceOf(ElementWater.class, spell.getElement(), "Spell element should be an instance of ElementWater");
    }

    
    
}
