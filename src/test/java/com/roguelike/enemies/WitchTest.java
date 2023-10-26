package com.roguelike.enemies;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WitchTest {

    private static final double WITCH_XP = 80;

    @Test
    public void testWitchGetXp() {
        Witch witch = new Witch();
        double result = witch.getWitchXp();
        assertThat(result, is(WITCH_XP));
    }
}
