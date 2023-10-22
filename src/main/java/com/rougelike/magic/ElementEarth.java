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
            public double getMultiplier(Player player) {
                if (player.getRace() instanceof Dwarf) {
                    return EARTH_MULTIPLIE_DWARF;
                }
                if (player.getRace() instanceof Orc) {
                    return EARTH_MULTIPLIE_ORCH;
                }
                return BASE_MULTIPLIER;
            }
}
