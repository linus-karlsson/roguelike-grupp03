package com.roguelike.equipment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ElementTypeTest {

    @Test
    void testElementIsEffectivAWaterFalse() {
        boolean result = ElementType.elementIsEffective(ElementType.WATER, ElementType.EARTH);
        assertFalse(result);
    }

    @Test
    void testElementIsEffectivAWaterTrue() {
        boolean result = ElementType.elementIsEffective(ElementType.WATER, ElementType.FIRE);
        assertTrue(result);
    }

    @Test
    void testElementIsEffectivAEarthFalse() {
        boolean result = ElementType.elementIsEffective(ElementType.EARTH, ElementType.WATER);
        assertFalse(result);
    }

    @Test
    void testElementIsEffectivAEarthTrue() {
        boolean result = ElementType.elementIsEffective(ElementType.EARTH, ElementType.FIRE);
        assertTrue(result);
    }

    @Test
    void testElementIsEffectiveAAirFalse() {
        boolean result = ElementType.elementIsEffective(ElementType.AIR, ElementType.WATER);
        assertFalse(result);
    }

    @Test
    void testElementIsEffectiveAirTrue() {
        boolean result = ElementType.elementIsEffective(ElementType.AIR, ElementType.EARTH);
        assertTrue(result);
    }

}
