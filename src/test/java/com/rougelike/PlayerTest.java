package com.rougelike;

import com.rougelike.races.Dwarf;
import com.rougelike.roles.Knight;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testPlayerConstructor() {
        Player player = new Player("Test", new Point());
        String expected = "Test";
        assertEquals(expected, player.getName());
    }

    @Test
    public void testIncreaseXp() {
        Player player = new Player("Test", new Point());
        double xpToNextLevel = player.getXpToNextLevel();
        double xpToIncrease = xpToNextLevel;
        int level = player.getLevel();
        player.increaseXp(xpToIncrease);
        assertEquals(xpToNextLevel * player.getLevelMultiplier(), player.getXpToNextLevel());
        assertEquals(level + 1, player.getLevel());
    }

    @Test
    public void TestPlayerInstanceCreationDwarfKnight() {
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Gimli", dwarf, knight,new Point());
        double expectedHealth = dwarf.getStartingHealth() * knight.getHealthMultiplier();
        double expectedIntelligence = dwarf.getStartingIntelligence() * knight.getIntelligenceMultiplier();
        assertEquals(expectedHealth, player.getHealth());
        assertEquals(expectedIntelligence, player.getIntelligence());
    }
}
