package com.rougelike.enemies;

import com.rougelike.Player;
import com.rougelike.Point2D;
import com.rougelike.races.Elf;
import com.rougelike.roles.Thief;
import org.junit.jupiter.api.Test;
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
