package com.rougelike;

import com.rougelike.races.Dwarf;
import com.rougelike.roles.Knight;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testPlayerConstructor() {
        Player player = new Player("Test", null);
        String expected = "Test";
        assertEquals(expected, player.getName());
    }

    @Test
    public void testIncreaseXp() {
        Player player = new Player("Test", null);
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
        Player player = new Player("Gimli", dwarf, knight, null);
        double expectedHealth = dwarf.getStartingHealth() * knight.getHealthMultiplier();
        double expectedIntelligence = dwarf.getStartingIntelligence() * knight.getIntelligenceMultiplier();
        assertEquals(expectedHealth, player.getHealth());
        assertEquals(expectedIntelligence, player.getIntelligence());
    }

    @Test
    public void TestTotalWeaponDamage(){
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Gimli", dwarf, knight, null);
        double knightWeaponDamage = knight.getStartingWeapon().getDamage();
        double dwarfStrength = dwarf.getStartingStrength();
        double expectedDamage = knightWeaponDamage * dwarfStrength;
        double playerTotalWeaponDamage = player.attackWithWeapon();
        assertEquals(expectedDamage, playerTotalWeaponDamage);

    }
}
