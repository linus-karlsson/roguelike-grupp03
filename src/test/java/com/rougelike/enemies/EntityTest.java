package com.rougelike.enemies;

import com.rougelike.Player;
import com.rougelike.Point2D;
import com.rougelike.races.Elf;
import com.rougelike.roles.Thief;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    @Test
    public void TestWitchAttacksPlayer() {
        Witch witch = new Witch();
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        witch.attack(player);
        double expectedPlayerHealthLeft = 85;
        assertEquals(expectedPlayerHealthLeft, player.getHealth());
    }

    @Test
    public void TestTrollAttacksPlayerUntilDead() {
        Troll troll = new Troll();
        Elf elf = new Elf();
        Thief thief = new Thief();
        Player player = new Player("Legolas", elf, thief, new Point2D());
        while (player.getHealth() > 1) {
            troll.attack(player);
        }

        assertEquals(true, player.isDead());
    }
}
