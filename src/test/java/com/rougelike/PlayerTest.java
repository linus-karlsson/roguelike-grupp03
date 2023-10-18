package com.rougelike;

import com.rougelike.races.Dwarf;
import com.rougelike.races.Elf;
import com.rougelike.roles.Knight;
import com.rougelike.roles.Thief;
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

    @Test
    public void TestTotalWeaponDamage(){
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Gimli", dwarf, knight, new Point());
        double knightWeaponDamage = knight.getStartingWeapon().getDamage();
        double dwarfStrength = dwarf.getStartingStrength();
        double knightStrengthMultiplier = knight.getStrengthMultiplier();
        double totalStrength = knightStrengthMultiplier * dwarfStrength;
        double expectedDamage = knightWeaponDamage * totalStrength;
        double playerTotalWeaponDamage = player.getTotalWeaponDamage();
        assertEquals(expectedDamage, playerTotalWeaponDamage);
    }

    @Test
    public void TestTakeDamage(){
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point());
        player.takeDamage(50);
        double expectedHealthLeft = 50;
        assertEquals(expectedHealthLeft, player.getHealth());
    }

    @Test void TestPlayerResetResetsHealth(){
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point());
        player.setHealth(300);
        player = player.reset();
        double expectedHealth = 100;
        assertEquals(expectedHealth, player.getHealth());
    }

    @Test void TestPlayerResetResetsLevel(){
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point());
        player.setLevel(20);
        player = player.reset();
        double expectedLevel = 1;
        assertEquals(expectedLevel, player.getLevel());
    }
}
