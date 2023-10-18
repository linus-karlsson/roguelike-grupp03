package com.rougelike.magic;

import com.rougelike.*;

//Alv
// Vind är starka mot jord (dvärg) och vatten (starkare vid kombination med eld)
// Vind påverkar jord(dvärg) svagare
public class ElementAir extends MagicElementType {
    
        public ElementAir() {
            super("Air");
        }
        @Override
        public double getMultiplier(Player player) {

            return 1.05;
        }
}
