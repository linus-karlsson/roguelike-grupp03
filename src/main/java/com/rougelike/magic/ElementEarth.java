package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;
//Dvärg starkare, ordch svagare
// jord är stark mot eld (orch) och vind (Alv) (starkare vid kombination med vatten)
// jord påverkar vatten svagare
public class ElementEarth extends MagicElementType{

    private static final double EARTH_MULTIPLIE_DWARF = 1.05;
    private static final double EARTH_MULTIPLIE_ORCH = 0.95;
    private static final String ELEMENT_TYPE = "Earth";
       
            public ElementEarth() {
                super(ELEMENT_TYPE);
            }

            @Override
            public final double getMultiplier(Player player) {
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
