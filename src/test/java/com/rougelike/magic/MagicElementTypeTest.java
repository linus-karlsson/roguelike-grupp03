package com.rougelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MagicElementTypeTest {

    @Test
    void testGetMultiplierReturnsCorrectValueWhenNeutral() {
        Magic magic = new Magic(Spell.HEAL);
        double expectedMultiplier = 1.0;
        assertEquals(expectedMultiplier, magic.getElement().getMultiplier());
    }

}
