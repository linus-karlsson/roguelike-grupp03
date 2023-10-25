package com.roguelike.enemies;

import org.junit.jupiter.api.Test;

import com.roguelike.Player;
import com.roguelike.Point2D;
import com.roguelike.races.Elf;
import com.roguelike.roles.Thief;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.*;

public class WitchTest {

    @Test
    public void TestWitchAttacksPlayer() {
        Witch witch = new Witch();
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        witch.attack(player);
        double expectedPlayerHealthLeft = 85;
        MatcherAssert.assertThat(player.getHealth(), is(expectedPlayerHealthLeft));
    }
}
