package com.rougelike.magic;

import com.rougelike.*;

//Alv stakare, orch svagare
// Vind är starka mot jord (dvärg) och vatten (starkare vid kombination med eld)
// Vind påverkar jord(dvärg) svagare
public class ElementAir extends MagicElementType {

    private static final double AIR_MULTIPLIER = 1.05;
    
        public ElementAir() {
            super("Air");
        }
        @Override
        public double getMultiplier(Player player) {

            return AIR_MULTIPLIER;
        }
}
