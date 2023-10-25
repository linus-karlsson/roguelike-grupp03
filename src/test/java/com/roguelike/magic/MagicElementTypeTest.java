package com.roguelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.roguelike.*;
import com.roguelike.races.*;
import com.roguelike.roles.*;

public class MagicElementTypeTest {

    // Test MagicElementTypeNeutral och abstrakt superklass
    @Test
    void testMagicElementNeutralInstanceCreation() {
        MagicElementType magicElementType = new ElementNeutral();
        assertEquals("Neutral", magicElementType.getName());
    }

    @Test
    void testMagicElementNeutralIsSubclassOfMagicElementType() {
        MagicElementType magicElementType = new ElementNeutral();
        assertEquals(MagicElementType.class, magicElementType.getClass().getSuperclass());
    }

    @Test
    void testMagicElementNeutralIsNotSubclassOfMagicElementTypeAir() {
        MagicElementType magicElementType = new ElementNeutral();
        assertNotEquals(ElementAir.class, magicElementType.getClass().getSuperclass());
    }

    @Test
    void testMagicElementNeutralGetNameReturnsCorrectValue() {
        MagicElementType magicElementType = new ElementNeutral();
        assertEquals("Neutral", magicElementType.getName());
    }

    @Test
    void testMagicElementNeutralGetMultiplierThrowsExceptionIfPlayerIsNull() {
        MagicElementType magicElementType = new ElementNeutral();
        assertThrows(IllegalArgumentException.class, () -> magicElementType.getMultiplier(null));
    }

    @Test
    void testMagicElementNeutralGetMultiplierReturnsCorrectValue() {
        MagicElementType magicElementType = new ElementNeutral();
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.0;
        assertEquals(expectedMultiplier, magicElementType.getMultiplier(player));
    }

    // Test MagicElementTypeAir

    @Test
    void testMagicElementAirInstanceCreation() {
        MagicElementType magicElementType = new ElementAir();
        assertEquals("Air", magicElementType.getName());
    }

    @Test
    void testMagicElementAirIsSubclassOfMagicElementType() {
        MagicElementType magicElementType = new ElementAir();
        assertEquals(MagicElementType.class, magicElementType.getClass().getSuperclass());
    }

    @Test
    void testMagicElementAirIsNotSubclassOfMagicElementTypeNeutral() {
        MagicElementType magicElementType = new ElementAir();
        assertNotEquals(ElementNeutral.class, magicElementType.getClass().getSuperclass());
    }

    @Test
    void testMagicElementTypeGetMultiplierThrowsExceptionIfPlayerIsNull() {
        MagicElementType magicElementType = new ElementAir();
        assertThrows(IllegalArgumentException.class, () -> magicElementType.getMultiplier(null));
    }

    @Test
    void testMagicElementAirGetNameReturnsCorrectValue() {
        MagicElementType magicElementType = new ElementAir();
        assertEquals("Air", magicElementType.getName());
    }

    @Test
    void testMagicElementAirGetMultiplierReturnsCorrectValue() {
        MagicElementType magicElementType = new ElementAir();
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.05;
        assertEquals(expectedMultiplier, magicElementType.getMultiplier(player));
    }

    @Test
    void testMagicElementAirGetMultiplierReturnsCorrectValueIfPlayerIsOrc() {
        MagicElementType magicElementType = new ElementAir();
        Player player = new Player("Test", new Orc(), new Thief(), new Point2D());
        double expectedMultiplier = 0.95;
        assertEquals(expectedMultiplier, magicElementType.getMultiplier(player));
    }

    @Test
    void testMagicElementAirGetMultiplierReturnsCorrectValueIfPlayerIsHuman() {
        MagicElementType magicElementType = new ElementAir();
        Player player = new Player("Test", new Human(), new Thief(), new Point2D());
        double expectedMultiplier = 1.0;
        assertEquals(expectedMultiplier, magicElementType.getMultiplier(player));
    }

    // Test MagicElementTypeEarth

    @Test
    void testMagicElementEarthInstanceCreation() {
        MagicElementType magicElementType = new ElementEarth();
        assertEquals("Earth", magicElementType.getName());
    }

    @Test
    void testMagicElementEarthIsSubclassOfMagicElementType() {
        MagicElementType magicElementType = new ElementEarth();
        assertEquals(MagicElementType.class, magicElementType.getClass().getSuperclass());
    }

    @Test
    void testMagicElementEarthIsNotSubclassOfMagicElementTypeNeutral() {
        MagicElementType magicElementType = new ElementEarth();
        assertNotEquals(ElementNeutral.class, magicElementType.getClass().getSuperclass());
    }

    @Test
    void testMagicElementTypeEarthGetMultiplierThrowsExceptionIfPlayerIsNull() {
        MagicElementType magicElementType = new ElementEarth();
        assertThrows(IllegalArgumentException.class, () -> magicElementType.getMultiplier(null));
    }

    @Test
    void testMagicElementEarthGetNameReturnsCorrectValue() {
        MagicElementType magicElementType = new ElementEarth();
        assertEquals("Earth", magicElementType.getName());
    }

    @Test
    void testMagicElementEarthGetMultiplierReturnsCorrectValue() {
        MagicElementType magicElementType = new ElementEarth();
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.0;
        assertEquals(expectedMultiplier, magicElementType.getMultiplier(player));
    }

    @Test
    void testMagicElementEarthGetMultiplierReturnsCorrectValueIfPlayerIsDwarf() {
        MagicElementType magicElementType = new ElementEarth();
        Player player = new Player("Test", new Dwarf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.05;
        assertEquals(expectedMultiplier, magicElementType.getMultiplier(player));
    }

    @Test
    void testMagicElementEarthGetMultiplierReturnsCorrectValueIfPlayerIsOrc() {
        MagicElementType magicElementType = new ElementEarth();
        Player player = new Player("Test", new Orc(), new Thief(), new Point2D());
        double expectedMultiplier = 0.95;
        assertEquals(expectedMultiplier, magicElementType.getMultiplier(player));
    }

    // Test MagicElementTypeFire

    @Test
    void testMagicElementFireInstanceCreation() {
        MagicElementType magicElementType = new ElementFire();
        assertEquals("Fire", magicElementType.getName());
    }

    @Test
    void testMagicElementFireIsSubclassOfMagicElementType() {
        MagicElementType magicElementType = new ElementFire();
        assertEquals(MagicElementType.class, magicElementType.getClass().getSuperclass());
    }

    @Test
    void testMagicElementFireIsNotSubclassOfMagicElementTypeNeutral() {
        MagicElementType magicElementType = new ElementFire();
        assertNotEquals(ElementNeutral.class, magicElementType.getClass().getSuperclass());
    }

    @Test
    void testMagicElementTypeFireGetMultiplierThrowsExceptionIfPlayerIsNull() {
        MagicElementType magicElementType = new ElementFire();
        assertThrows(IllegalArgumentException.class, () -> magicElementType.getMultiplier(null));
    }

    @Test
    void testMagicElementFireGetNameReturnsCorrectValue() {
        MagicElementType magicElementType = new ElementFire();
        assertEquals("Fire", magicElementType.getName());
    }

    @Test
    void testMagicElementFireGetMultiplierReturnsCorrectValue() {
        MagicElementType magicElementType = new ElementFire();
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        double expectedMultiplier = 1.0;
        assertEquals(expectedMultiplier, magicElementType.getMultiplier(player));
    }

}