package com.roguelike.magic;

import java.util.Random;

import com.roguelike.Player;
import com.roguelike.roles.*;

public class MagicAttack extends MagicInvoker {
    private Random random = new Random();
    private static final String TYPE_OF_MAGIC = "Attack";

    public MagicAttack() {
        super(TYPE_OF_MAGIC);
    }

    public MagicAttack(Random random) {
        this();
        this.random = random;
    }

    private boolean isSpellInvoked(Player player) {
        final int chanceForMage = 98;
        final int chanceForAllOtherRoles = 95;
        final int span = 100;
        if (player.getRole() instanceof Mage) {
            return chanceForMage > (random.nextInt(span) + 1);
        } else
            return chanceForAllOtherRoles > (random.nextInt(span) + 1);
    }

    public double throwMagic(Magic magic, Player player) {
        if (magic == null || player == null) {
            throw new IllegalArgumentException("Magic or player is null");
        }
        if (isSpellInvoked(player)) {
            return magicValue(magic, player);
        }
        return 0.0;
    }

}
