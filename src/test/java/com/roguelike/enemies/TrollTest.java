package com.roguelike.enemies;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrollTest {

    private static final double TROLL_XP = 50;

    @Test
    public void testTrollGetXp() {
        Troll troll = new Troll();
        double result = troll.getTrollXp();
        assertThat(result, is(TROLL_XP));
    }
}
