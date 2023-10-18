package com.rougelike.magic;

import com.rougelike.*;
import com.rougelike.races.*;

//Alv stakare, orch svagare
// Vind är starka mot jord (dvärg) och vatten (starkare vid kombination med eld)
// Vind påverkar jord(dvärg) svagare
public class ElementAir extends MagicElementType {

    private static final double AIR_MULTIPLIE_BASE = 1.00;
    private static final double AIR_MULTIPLIE_ELF = 1.05;
    private static final double AIR_MULTIPLIE_ORCH = 0.95;
    
        public ElementAir() {
            super("Air");
        }
        @Override
        public double getMultiplier(Player player) {
            if (player.getRace() instanceof Elf) {
                return AIR_MULTIPLIE_ELF;
            }
            if (player.getRace() instanceof Orc) {
                return AIR_MULTIPLIE_ORCH;
            }
            return AIR_MULTIPLIE_BASE;
        }
}
