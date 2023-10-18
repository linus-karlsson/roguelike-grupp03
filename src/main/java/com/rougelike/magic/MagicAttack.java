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
    public void throwMagic(Magic magic, Player player, Player enemy) {
        double attackBeforeControl = enemy.getHealth() - MagicValue(magic, player);
        player.setHealth(attackBeforeControl < 0 ? 0 : attackBeforeControl);
    }
}
