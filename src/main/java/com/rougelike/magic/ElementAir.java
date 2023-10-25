package com.rougelike.magic;

import com.rougelike.Player;
import com.rougelike.races.Elf;
import com.rougelike.races.Orc;

public class ElementAir extends MagicElementType {

    private static final double AIR_MULTIPLIER_ELF = 1.05;
    private static final double AIR_MULTIPLIER_ORC = 0.95;
    private static final String ELEMENT_TYPE = "Air";
    
        public ElementAir() {
            super(ELEMENT_TYPE);
        }
        @Override
        public double getMultiplier(Player player) {
            if (player == null) {
                throw new IllegalArgumentException("Player cannot be null!");
            }
            double multiplierToRetrun = BASE_MULTIPLIER;
            if (player.getRace() instanceof Elf) {
               multiplierToRetrun = AIR_MULTIPLIER_ELF;
            }
            if (player.getRace() instanceof Orc) {
                multiplierToRetrun = AIR_MULTIPLIER_ORC;
            }
            return multiplierToRetrun;
        }
}
