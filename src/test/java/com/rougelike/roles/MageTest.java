package com.rougelike.roles;

import com.rougelike.enemies.Troll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MageTest {

    @Test
    public void testPlayerMageDebuffDebuffsEnemy(){
        Mage mage = new Mage();
        Troll troll = new Troll();
        double trollStartingDamage = troll.getDamage();
        mage.debuff(troll, 1);
        assertNotEquals(trollStartingDamage, troll.getDamage());
    }
}
