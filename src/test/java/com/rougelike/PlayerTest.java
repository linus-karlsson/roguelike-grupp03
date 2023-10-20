package com.rougelike;

import com.rougelike.enemies.Troll;
import com.rougelike.enemies.Witch;
import com.rougelike.equipment.EarthHammer;
import com.rougelike.equipment.ElementType;
import com.rougelike.equipment.WaterDagger;
import com.rougelike.races.Dwarf;
import com.rougelike.races.Elf;
import com.rougelike.races.Human;
import com.rougelike.roles.Knight;
import com.rougelike.roles.Mage;
import com.rougelike.roles.Role;
import com.rougelike.roles.Thief;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testPlayerConstructor() {
        Player player = new Player("Test", new Point2D());
        String expected = "Test";
        assertEquals(expected, player.getName());
    }

    @Test
    public void testIncreaseXp() {
        Player player = new Player("Test", new Point2D());
        double xpToNextLevel = player.getXpToNextLevel();
        double xpToIncrease = xpToNextLevel;
        int level = player.getLevel();
        player.increaseXp(xpToIncrease);
        assertEquals(xpToNextLevel * player.getLevelMultiplier(), player.getXpToNextLevel());
        assertEquals(level + 1, player.getLevel());
    }

    private void setUpMovementTest(Vector2D velocity, Point2D roomPosition, Point2D playerPosition) {
        int rowCount = 10;
        int columnCount = 10;
        double tileSize = 5.0;
        Grid gridd = new Grid(rowCount, columnCount, tileSize);
        gridd.fillWithValue(-1);
        Room room = new Room(10.0, 10.0);
        room.setPosition(10.0, 10.0);
        gridd.getRoomParser().setRoom(room);
        gridd.getRoomParser().placeRoomInGridd();

        Player player = new Player("Test", room.getPosition());
        double deltaTime = 1.0;
        player.setVelocity(velocity);
        player.updateMovement(gridd, deltaTime);

        roomPosition.clone(room.getPosition());
        playerPosition.clone(player.getPosition());
    }

    @Test
    public void testUpdateMovementShoulNotMove() {
        Point2D roomPosition = new Point2D();
        Point2D playerPosition = new Point2D();
        setUpMovementTest(new Vector2D(-5.0, -5.0), roomPosition, playerPosition);
        assertEquals(roomPosition.getX(), playerPosition.getX());
        assertEquals(roomPosition.getY(), playerPosition.getY());
    }

    @Test
    public void testUpdateMovementShoulMove() {
        Point2D roomPosition = new Point2D();
        Point2D playerPosition = new Point2D();
        setUpMovementTest(new Vector2D(5.0, 5.0), roomPosition, playerPosition);
        assertNotEquals(roomPosition.getX(), playerPosition.getX());
        assertNotEquals(roomPosition.getY(), playerPosition.getY());
    }

    @Test
    public void TestPlayerInstanceCreationDwarfKnight() {
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Gimli", dwarf, knight, new Point2D());
        double expectedHealth = dwarf.getStartingHealth() * knight.getHealthMultiplier();
        double expectedIntelligence = dwarf.getStartingIntelligence() * knight.getIntelligenceMultiplier();
        assertEquals(expectedHealth, player.getHealth());
        assertEquals(expectedIntelligence, player.getIntelligence());
    }

    @Test
    public void TestTotalWeaponDamage() {
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Gimli", dwarf, knight, new Point2D());
        double knightWeaponDamage = knight.getStartingWeapon().getDamage();
        double dwarfStrength = dwarf.getStartingStrength();
        double knightStrengthMultiplier = knight.getStrengthMultiplier();
        double totalStrength = knightStrengthMultiplier * dwarfStrength;
        double expectedDamage = knightWeaponDamage * totalStrength;
        double playerTotalWeaponDamage = player.getTotalWeaponDamage();
        assertEquals(expectedDamage, playerTotalWeaponDamage);
    }

    @Test
    public void TestTakeDamage() {
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        player.takeDamage(50);
        double expectedHealthLeft = 50;
        assertEquals(expectedHealthLeft, player.getHealth());
    }

    @Test
    void TestPlayerResetResetsHealth() {
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        player.setHealth(300);
        player = player.reset();
        double expectedHealth = 100;
        assertEquals(expectedHealth, player.getHealth());
    }

    @Test
    void TestPlayerResetResetsLevel() {
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        player.setLevel(20);
        player = player.reset();
        double expectedLevel = 1;
        assertEquals(expectedLevel, player.getLevel());
    }

    @Test
    public void TestPlayerThiefCantAttackWhenInvisible() {
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        Troll troll = new Troll();
        double expectedTrollHealth = troll.getHealth();
        thief.invisibility();
        player.attackEnemyWithWeapon(troll);
        assertEquals(expectedTrollHealth, troll.getHealth());
    }

    @Test
    public void TestPlayerThiefAvoidsDamageWhenInvisible() {
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        Troll troll = new Troll();
        double expectedPlayerHealth = player.getHealth();
        player.invisibility();
        troll.attack(player);
        assertEquals(expectedPlayerHealth, player.getHealth());
    }

    @Test
    public void TestPlayerKnightShieldBashStunsEnemy() {
        Human human = new Human();
        Knight knight = new Knight();
        Player player = new Player("Aragorn", human, knight, new Point2D());
        Witch witch = new Witch();
        double expectedPlayerHealth = player.getHealth();
        player.shieldBash(witch);
        witch.attack(player);
        assertEquals(expectedPlayerHealth, player.getHealth());
    }

    @Test
    public void TestPlayerMageDebuffEnemy() {
        Human human = new Human();
        Mage mage = new Mage();
        Player player = new Player("Gandalf", human, mage, new Point2D());
        player.setLevel(49);
        Troll troll = new Troll();
        double expectedTrollHealth = troll.getHealth() * 0.8;
        player.debuff(troll);
        assertEquals(expectedTrollHealth, troll.getHealth());
    }

    @Test
    public void TestPlayerWeaponIsEffective() {
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        WaterDagger waterDagger = new WaterDagger();
        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        boolean result = player.weaponIsEffective(ElementType.FIRE);
        assertTrue(result);
    }

}
