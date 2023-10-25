package com.roguelike.enemies;

import org.junit.jupiter.api.Test;

import com.roguelike.Player;
import com.roguelike.Point2D;
import com.roguelike.races.Elf;
import com.roguelike.roles.Thief;

import org.hamcrest.MatcherAssert;

import static org.hamcrest.Matchers.*;

public class TrollTest {

    @Test
    public void testTrollAttacksPlayerUntilDead() {
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
