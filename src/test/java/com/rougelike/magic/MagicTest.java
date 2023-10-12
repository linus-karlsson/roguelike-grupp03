package com.rougelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rougelike.Player;
import com.rougelike.magic.Magic;

public class MagicTest {

    @Test
    void testMagicInstanceCreation() {
        Spell spell = Spell.FIREBALL;
        Magic magic = new Magic(spell);
        assertNotNull(magic);
    }


}
