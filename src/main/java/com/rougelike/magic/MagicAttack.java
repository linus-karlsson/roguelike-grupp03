package com.rougelike.magic;

import com.rougelike.Player;

public class MagicAttack extends MagicInvoker {
    public MagicAttack() {
        super("Attack");
    }

    public String getName() {
        return super.name;
    }

    @Override
    public void throwMagic(Magic magic, Player player) {
        double attackBeforeControl = player.getHealth() - MagicValue(magic, player);
        player.setHealth(attackBeforeControl < 0 ? 0 : attackBeforeControl);
    }
}
