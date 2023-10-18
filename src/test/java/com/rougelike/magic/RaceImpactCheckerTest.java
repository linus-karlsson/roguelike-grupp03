package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;
import com.rougelike.roles.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rougelike.Player;

public class RaceImpactCheckerTest {

    @Test
    void testIfPlayerElfIsImpactByElementAir() {
        Player player = new Player("Test", new Elf(), new Thief(), new Point());
        assertTrue(RaceImpactChecker.isPlayerImpactByAir(player)); 
    }

}
