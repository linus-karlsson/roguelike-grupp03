package com.roguelike.enemies;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BanditTest {

    private static final double BANDIT_XP = 30;
    @Test
    public void testBanditGetXp() {
        Bandit bandit = new Bandit();
        double result = bandit.getBanditXp();
        assertThat(result, is(BANDIT_XP));
    }
}
