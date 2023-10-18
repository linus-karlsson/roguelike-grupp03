package com.rougelike.magic;

import com.rougelike.*;
//Dvärg starkare, ordch svagare
// jord är stark mot eld (orch) och vind (Alv) (starkare vid kombination med vatten)
// jord påverkar vatten svagare
public class ElementEarth extends MagicElementType{

       
            public ElementEarth() {
                super("Earth");
            }

            @Override
            public double getMultiplier(Player player) {
                return 1.05;
            }
}
