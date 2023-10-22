package com.rougelike.magic;

import com.rougelike.*;

//dvärg svagare
//vatten påverkar vind(alv) starkare i kombination med jord
//vatten påverkar eld(orch) svagare
public class ElementWater extends MagicElementType {

    private static final String ELEMENT_TYPE = "Water";
        
            public ElementWater() {
                super(ELEMENT_TYPE);
            }

            @Override
            public final double getMultiplier(Player player) {
                return BASE_MULTIPLIER;
            }
}
