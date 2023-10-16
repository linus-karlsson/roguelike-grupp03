package com.rougelike.races;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class RaceTest {
    private static final double DWARF_STARTING_HEALTH = 100.0;

    @Test
    public void TestDwarfInstanceCreation(){
        Dwarf dwarf = new Dwarf();
        assertEquals(DWARF_STARTING_HEALTH, dwarf.getStartingHealth());
    }
}
