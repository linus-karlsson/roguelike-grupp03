package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;

//Alv starkare, orch svagare
// Vind är starka mot jord (dvärg) och vatten (starkare vid kombination med eld)
// Vind påverkar jord(dvärg) svagare
public class ElementAir extends MagicElementType {

    private static final double AIR_MULTIPLIER_ELF = 1.05;
    private static final double AIR_MULTIPLIER_ORC = 0.95;
    
        public ElementAir() {
            super("Air");
        }
        @Override
        public double getMultiplier(Player player) {
            if (player.getRace() instanceof Elf) {
                return AIR_MULTIPLIER_ELF;
            }
            if (player.getRace() instanceof Orc) {
                return AIR_MULTIPLIER_ORC;
            }
            return BASE_MULTIPLIER;
        }
}
