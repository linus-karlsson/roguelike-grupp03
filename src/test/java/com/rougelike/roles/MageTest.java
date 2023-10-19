package com.rougelike.roles;

import com.rougelike.Player;
import com.rougelike.Point;
import com.rougelike.enemies.Troll;
import com.rougelike.races.Human;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MageTest {

    @Test
    public void TestPlayerMageDebuffDebuffsEnemy(){
        Mage mage = new Mage();
        Troll troll = new Troll();
        double trollStartingDamage = troll.getDamage();
        mage.debuff(troll, 1);
        assertNotEquals(trollStartingDamage, troll.getDamage());
    }
}
