package com.roguelike.roles;

import org.junit.jupiter.api.Test;

import com.roguelike.enemies.Troll;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MageTest {

    @Test
    public void testPlayerMageDebuffDebuffsEnemy() {
        Mage mage = new Mage();
        Troll troll = new Troll();
        double trollStartingDamage = troll.getDamage();
        mage.debuff(troll, 1);
        assertNotEquals(trollStartingDamage, troll.getDamage());
    }
}
