package com.rougelike.magic;

import com.rougelike.Player;

public class MagicAttack extends MagicInvoker {
    public MagicAttack() {
        super("Attack");
    }

    public String getName() {
        return super.name;
    }

    public double throwMagic(Magic magic, Player player) {
        return magicValue(magic, player) < 0 ? 0 : magicValue(magic, player);
    }

}
