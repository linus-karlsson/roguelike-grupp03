package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;

public class ElementEarth extends MagicElementType{

    private static final double EARTH_MULTIPLIE_DWARF = 1.05;
    private static final double EARTH_MULTIPLIE_ORCH = 0.95;
    private static final String ELEMENT_TYPE = "Earth";
       
            public ElementEarth() {
                super(ELEMENT_TYPE);
            }

            @Override
            public double getMultiplier(Player player) {
                if (player == null) {
                    throw new IllegalArgumentException("Player cannot be null!");
                }
                double multiplierToRetrun = BASE_MULTIPLIER;
                if (player.getRace() instanceof Dwarf) {
                    multiplierToRetrun = EARTH_MULTIPLIE_DWARF;
                }
                else if (player.getRace() instanceof Orc) {
                    multiplierToRetrun = EARTH_MULTIPLIE_ORCH;
                }
                return multiplierToRetrun;
            }
}
