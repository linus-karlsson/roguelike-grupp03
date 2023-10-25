package com.roguelike;

import org.junit.jupiter.api.*;

import com.roguelike.dungeon.Grid;
import com.roguelike.dungeon.Room;
import com.roguelike.dungeon.RoomParser;
import com.roguelike.enemies.Troll;
import com.roguelike.enemies.Witch;
import com.roguelike.equipment.ElementType;
import com.roguelike.equipment.WaterDagger;
import com.roguelike.races.Dwarf;
import com.roguelike.races.Elf;
import com.roguelike.races.Human;
import com.roguelike.roles.Knight;
import com.roguelike.roles.Mage;
import com.roguelike.roles.Thief;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.*;
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
        Grid grid = new Grid(rowCount, columnCount, tileSize);
        grid.fillWithValue(-1);
        Room room = new Room(10.0, 10.0);
        room.setPosition(10.0, 10.0);
        RoomParser roomParser = new RoomParser(grid);
        roomParser.setRoom(room);
        roomParser.placeRoomInGridd();

        Player player = new Player("Test", room.getPosition());
        double deltaTime = 1.0;
        player.setVelocity(velocity);
        player.updateMovement(grid, deltaTime);

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
        MatcherAssert.assertThat(player, allOf(
                hasProperty("health", is(expectedHealth)),
                hasProperty("intelligence", is(expectedIntelligence))));
    }

    @Test
    public void TestGetTotalWeaponDamage() {
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
        MatcherAssert.assertThat(playerTotalWeaponDamage, is(expectedDamage));

    }

    @Test
    public void TestTakeDamage() {
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        player.takeDamage(50);
        double expectedHealthLeft = 50;
        MatcherAssert.assertThat(player.getHealth(), is(expectedHealthLeft));
    }

    @Test
    void TestPlayerResetResetsHealth() {
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        player.setHealth(300);
        player = player.reset();
        double expectedHealth = 100;
        MatcherAssert.assertThat(player.getHealth(), is(expectedHealth));

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
        thief.becomeInvisible();
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
