package com.rougelike.magic;

import com.rougelike.Player;

public class MagicDefence extends MagicInvoker {

    public MagicDefence() {
        super("Defence");
    }

    public String getName() {
        return super.name;
    }

    public void throwMagic(Magic magic, Player player) {
        // för utveckling
    }

}
