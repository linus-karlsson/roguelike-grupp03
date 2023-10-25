package com.roguelike;

import com.roguelike.enemies.Bandit;
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
   private Human human = new Human();
    private Dwarf dwarf = new Dwarf();
    private Elf elf = new Elf();
    private Mage mage = new Mage();
    private Knight knight = new Knight();
    private Thief thief = new Thief();
    private Troll troll = new Troll();
    private Witch witch = new Witch();

    private Bandit bandit = new Bandit();

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
    public void testPlayerInstanceCreationDwarfKnight() {
        Player player = new Player("Gimli", dwarf, knight, new Point2D());
        double expectedHealth = dwarf.getStartingHealth() * knight.getHealthMultiplier();
        double expectedIntelligence = dwarf.getStartingIntelligence() * knight.getIntelligenceMultiplier();
        MatcherAssert.assertThat(player, allOf(
                hasProperty("health", is(expectedHealth)),
                hasProperty("intelligence", is(expectedIntelligence))));
    }

    @Test
    public void testGetTotalWeaponDamage() {
        Player player = new Player("Gimli", dwarf, knight, new Point2D());
        double knightWeaponDamage = knight.getStartingWeapon().getDamage();
        double dwarfStrength = dwarf.getStartingStrength();
        double knightStrengthMultiplier = knight.getStrengthMultiplier();
        double totalStrength = knightStrengthMultiplier * dwarfStrength;
        double expectedDamage = knightWeaponDamage * totalStrength;
        double playerTotalWeaponDamage = player.getTotalWeaponDamage();
        MatcherAssert.assertThat(playerTotalWeaponDamage, is(expectedDamage));

    }

    @Test
    public void testTakeDamage() {
        Player player = new Player("Legolas", elf, thief, new Point2D());
        player.takeDamage(50);
        double expectedHealthLeft = 50;
        MatcherAssert.assertThat(player.getHealth(), is(expectedHealthLeft));
    }

    @Test
    void testPlayerResetResetsHealth() {
        Player player = new Player("Legolas", elf, thief, new Point2D());
        player.setHealth(300);
        player = player.reset();
        double expectedHealth = 100;
        MatcherAssert.assertThat(player.getHealth(), is(expectedHealth));
    }

    @Test
    void testPlayerResetResetsLevel() {
        Player player = new Player("Legolas", elf, thief, new Point2D());
        player.setLevel(20);
        player = player.reset();
        int expectedLevel = 1;
        MatcherAssert.assertThat(expectedLevel, is(player.getLevel()));

    }

    @Test
    public void testPlayerThiefCantAttackWhenInvisible() {
        Player player = new Player("Legolas", elf, thief, new Point2D());
        double expectedTrollHealth = troll.getHealth();
        thief.becomeInvisible();
        player.attackEnemyWithWeapon(troll);
        MatcherAssert.assertThat(troll.getHealth(), is(expectedTrollHealth));
    }

    @Test
    public void testPlayerThiefAvoidsDamageWhenInvisible() {
        Player player = new Player("Legolas", elf, thief, new Point2D());
        double expectedPlayerHealth = player.getHealth();
        player.invisibility();
        troll.attack(player);
        MatcherAssert.assertThat(player.getHealth(), is(expectedPlayerHealth));

    }

    @Test
    public void testPlayerKnightShieldBashStunsEnemy() {
        Player player = new Player("Aragorn", human, knight, new Point2D());
        double expectedPlayerHealth = player.getHealth();
        player.shieldBash(witch);
        witch.attack(player);
        MatcherAssert.assertThat(player.getHealth(), is(expectedPlayerHealth));
    }

    @Test
    public void testPlayerMageDebuffEnemy() {
        Player player = new Player("Gandalf", human, mage, new Point2D());
        player.setLevel(49);
        double expectedTrollHealth = troll.getHealth() * 0.8;
        player.debuff(troll);
        MatcherAssert.assertThat(troll.getHealth(), is(expectedTrollHealth));

    }

    @Test
    public void testPlayerWeaponIsEffective() {
        Player player = new Player("Legolas", elf, thief, new Point2D());
        WaterDagger waterDagger = new WaterDagger();
        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        boolean result = player.weaponIsEffective(ElementType.FIRE);
        MatcherAssert.assertThat(result, is(true));
    }

    @Test
    public void testPlayerGainXPFromTroll() {
        Player player = new Player("Aragorn", human, knight, new Point2D());
        double trollXp = troll.getTrollXp();
        double expectedPlayerXp = player.getXp() + trollXp;
        player.increaseXp(trollXp);
        double currentPlayerXp = player.getXp();
        MatcherAssert.assertThat(currentPlayerXp, is(expectedPlayerXp));
    }

    @Test
    public void testPlayerGainXPFromBandit() {
        Player player = new Player("Aragorn", human, knight, new Point2D());
        double banditXp = bandit.getBanditXp();
        double expectedPlayerXp = player.getXp() + banditXp;
        player.increaseXp(banditXp);
        double currentPlayerXp = player.getXp();
        MatcherAssert.assertThat(currentPlayerXp, is(expectedPlayerXp));
    }

    @Test
    public void testPlayerGainXPFromWitch() {
        Player player = new Player("Aragorn", human, knight, new Point2D());
        double witchXp = witch.getWitchXp();
        double expectedPlayerXp = player.getXp() + witchXp;
        player.increaseXp(witchXp);
        double currentPlayerXp = player.getXp();
        MatcherAssert.assertThat(currentPlayerXp, is(expectedPlayerXp));
    }
}
