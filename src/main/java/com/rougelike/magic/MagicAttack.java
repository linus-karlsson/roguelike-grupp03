package com.rougelike.magic;

import java.util.Random;

import com.rougelike.Player;
import com.rougelike.roles.*;

public class MagicAttack extends MagicInvoker {
    private Random random = new Random();

    public MagicAttack() {
        super("Attack");
    }

    public MagicAttack(Random random) {
        this();
        this.random = random;
    }

    public String getName() {
        return super.name;
    }

    private boolean succeedToInvokeSpell(Player player) {
        if (player.getRole() instanceof Mage) {
            return (random.nextInt(100) + 1) < 98;
        } else
            return (random.nextInt(100) + 1) < 95;
    }

    public double throwMagic(Magic magic, Player player) {
        if (succeedToInvokeSpell(player)) {
            return magicValue(magic, player) < 0 ? 0 : magicValue(magic, player);
        }
        return 0;
    }

}
