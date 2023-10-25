package com.roguelike.magic;

import com.roguelike.Player;

public class MagicDefence extends MagicInvoker {

    private static final String TYPE_OF_MAGIC = "Defence";

    public MagicDefence() {
        super(TYPE_OF_MAGIC);
    }

    public double throwMagic(Magic magic, Player player) {
        return magicValue(magic, player) / 2;
        // för utveckling
    }

}
