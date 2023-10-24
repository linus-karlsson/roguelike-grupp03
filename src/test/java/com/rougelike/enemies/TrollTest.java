package com.rougelike.enemies;

import com.rougelike.Player;
import com.rougelike.Point2D;
import com.rougelike.enemies.Troll;
import com.rougelike.races.Elf;
import com.rougelike.roles.Thief;
import org.junit.jupiter.api.Test;
import org.hamcrest.MatcherAssert;

import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrollTest {

    @Test
    public void TestTrollAttacksPlayerUntilDead() {
        Troll troll = new Troll();
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        while (player.getHealth() > 1) {
            troll.attack(player);
        }
        MatcherAssert.assertThat(player.isDead(), is(Boolean.TRUE));
    }
}
