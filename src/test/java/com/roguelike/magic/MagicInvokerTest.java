package com.roguelike.magic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.roguelike.*;
import com.roguelike.races.*;
import com.roguelike.roles.*;

public class MagicInvokerTest {


    @Test
    void testSpellValueReturnsCorrectValueWhenPlayerStartLevel() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point2D());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testSpellValueReturnsCorrectValueWhenPlayerLevelTwo() {
        Magic magic = new Magic(Spell.FREEZE);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point2D());
        player.setLevel(2);
        double expectedValue = 12.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfPlayerRoleMageMagicValueShouldReturnTenPercentHigher() {
        Magic magic = new Magic(Spell.POISON);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Mage(), new Point2D());
        double expectedValue = 11.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfPlayerRoleKnightMagicValueShouldReturnTenPercentLower() {
        Magic magic = new Magic(Spell.FIREBALL);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Knight(), new Point2D());
        double expectedValue = 9.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }    

    @Test
    void testIfNeutralElementTypeNotImpactInMagicValue() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Human(), new Thief(), new Point2D());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfElementAirThrownByElfIncreaseMagicValueByFivePercent() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Elf(), new Thief(), new Point2D());
        double expectedValue = 10.5;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfElementAirThrownByOrchDecreaseValue() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Orc(), new Thief(), new Point2D());
        double expectedValue = 9.5;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    @Test
    void testIfElementAirThrownByDwarfNotImpactValue() {
        Magic magic = new Magic(Spell.TORNADO);
        MagicInvoker magicInvoker = magic.getType();
        Player player = new Player("Test", new Dwarf(), new Thief(), new Point2D());
        double expectedValue = 10.0;
        assertEquals(expectedValue, magicInvoker.magicValue(magic, player));
    }

    //HealTests
    @Test
    void testHealShouldBeInstanceOfMagicInvoker() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        assertTrue(magic.getType() instanceof MagicInvoker);
    }

    @Test
    void testHealShouldBeInstanceOfHeal() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        assertTrue(magic.getType() instanceof Heal);
    }

    @Test
    void testHealShouldHaveCorrectName() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        assertEquals("Heal", magic.getType().getName());
    }

    @Test
    void testHealMethodThrowMagicWhenMagicIsNullShouldThrowIllegalArgumentException() {
        Magic magic = null;
        Player player = new Player("Test", new Point2D());
        Heal heal = new Heal();
        assertThrows(IllegalArgumentException.class, () -> heal.throwMagic(magic, player));
    }

    @Test
    void testMethodThrowMagicWhenPlayerIsNullShouldThrowIllegalArgumentException() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = null;
        Heal heal = new Heal();
        assertThrows(IllegalArgumentException.class, () -> heal.throwMagic(magic, player));
    }

    @Test
    void testMethodThrowMagicIfPlayerHealthIsMaxShouldReturnMaxHealth() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        Heal heal = (Heal) magic.getType();
        player.setHealth(100);
        double expectedValue = 100;
        assertEquals(expectedValue, heal.throwMagic(magic, player));
    }

    @Test
    void testMethodThrowMagicShouldIncreasePlayersHealth() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        Heal heal = (Heal) magic.getType();
        player.setHealth(50);
        double expectedValue = 60;
        assertEquals(expectedValue, heal.throwMagic(magic, player));
    }

    @Test
    void testMethodThrowMagicShouldIncreasePlayersHealthToMax() {
        Magic magic = new Magic(Spell.HARMONYHEAL);
        Player player = new Player("Test", new Point2D());
        Heal heal = (Heal) magic.getType();
        player.setHealth(95);
        double expectedValue = 100;
        assertEquals(expectedValue, heal.throwMagic(magic, player));
    }

    //MagicAttackTests

    @Test
    void testSpellAttackShouldBeInstanceOfMagicInvoker() {
        Magic magic = new Magic(Spell.POISON);
        assertTrue(magic.getType() instanceof MagicInvoker);
    }

    @Test
    void testSpellAttackShouldBeInstanceOfAttack() {
        Magic magic = new Magic(Spell.FIREBALL);
        assertTrue(magic.getType() instanceof MagicAttack);
    }

    private double setupSucceedToInvokeSpell(Role role, int randomValue) {
        Player player = new Player("Test", new Elf(), role, new Point2D());
        MagicAttack attack = new MagicAttack(new RandomInternal(randomValue));
        Magic magic = new Magic(Spell.FIREBALL);
        return attack.throwMagic(magic, player);
    }

    @Test
    void testSucceedToInvokeSpellMageFail() {
        int expected = 0;
        assertEquals(expected, setupSucceedToInvokeSpell(new Mage(), 97));
    }

    @Test
    void testSucceedToInvokeSpellMage() {
        assertTrue(setupSucceedToInvokeSpell(new Mage(), 96) > 0);
    }

    @Test
    void testSucceedToInvokeSpellAllElseFail() {
        int expected = 0;
        assertEquals(expected, setupSucceedToInvokeSpell(new Knight(), 94));
    }

    @Test
    void testSucceedToInvokeAllElseMage() {
        assertTrue(setupSucceedToInvokeSpell(new Knight(), 93) > 0);
    }

    //MagicDefenseTests

    @Test
    void testSpellDefenseShouldBeInstanceOfMagicInvoker() {
        Magic magic = new Magic(Spell.FIRESHIELD);
        assertTrue(magic.getType() instanceof MagicInvoker);
    }

    @Test
    void testSpellDefenseShouldBeInstanceOfDefense() {
        Magic magic = new Magic(Spell.FIRESHIELD);
        assertTrue(magic.getType() instanceof MagicDefence);
    }

    @Test
    void testDefenceMethodThrowMagicWhenMagicIsNullShouldThrowIllegalArgumentException() {
        Magic magic = null;
        Player player = new Player("Test", new Point2D());
        MagicDefence defence = new MagicDefence();
        assertThrows(IllegalArgumentException.class, () -> defence.throwMagic(magic, player));
    }

    @Test
    void testDefenceMethodThrowMagicWhenPlayerIsNullShouldThrowIllegalArgumentException() {
        Magic magic = new Magic(Spell.FIRESHIELD);
        Player player = null;
        MagicDefence defence = new MagicDefence();
        assertThrows(IllegalArgumentException.class, () -> defence.throwMagic(magic, player));
    }

    @Test
    void testDefenceMethodThrowMagicShouldReturnHalfValue() {
        Magic magic = new Magic(Spell.FIRESHIELD);
        Player player = new Player("Test", new Point2D());
        MagicDefence defence = new MagicDefence();
        double expectedValue = 5;
        assertEquals(expectedValue, defence.throwMagic(magic, player));
    }

}
