package com.rougelike.magic;

import com.rougelike.*;

//dvärg svagare
//vatten påverkar vind(alv) starkare i kombination med jord
//vatten påverkar eld(orch) svagare
public class ElementWater extends MagicElementType {
        
            public ElementWater() {
                super("Water");
            }

            @Override
            public double getMultiplier(Player player) {
                return BASE_MULTIPLIER;
            }
}
