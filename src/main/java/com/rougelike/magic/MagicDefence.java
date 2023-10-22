package com.rougelike.magic;

import com.rougelike.Player;

public class MagicDefence extends MagicInvoker {

    public MagicDefence() {
        super("Defence");
    }

    public double throwMagic(Magic magic, Player player) {
        return magicValue(magic, player)/2;
        // för utveckling
    }

}
