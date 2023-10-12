package com.rougelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rougelike.Player;
import com.rougelike.magic.Magic;

public class MagicTest {

    @Test
    void testMagicInstanceCreation() {
        Magic magic = new Magic("Fireball", 10);
        assertNotNull(magic);
    }

    @Test
    void testMagicInstanceCreationWithNegativeStrength() {
        assertThrows(IllegalArgumentException.class, () -> {
            Magic magic = new Magic("Fireball", -10);
        });
    }

    @Test
    void testMagicInstanceCreationWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Magic magic = new Magic(null, 10);
        });
    }

    @Test
    void testMagicInstanceCreationWithNullNameAndDefaultStrength() {
        assertThrows(IllegalArgumentException.class, () -> {
            Magic magic = new Magic(null);
        });
    }

    @Test
    void testMagicInstanceCreationWithDefaultStrength() {
        Magic magic = new Magic("Fireball");
        assertNotNull(magic);
    }

    @Test
    void getName_ShouldReturnMagicName() {
        Magic magic = new Magic("Fireball", 10);
        String expectedName = "Fireball";
        assertEquals(expectedName, magic.getName());
    }

    // @Test
    // void testStrenghtValueBasedOnPlayersLevel() {
    //     Player player = new Player("Player");
    //     player.increaseXp(player.getXpToNextLevel());
    //     Magic magic = new Magic("Fireball", 10);
    //     double expectedStrength = 12.0;
    //     assertEquals(expectedStrength, magic.getStrength(player));
    // }

}
