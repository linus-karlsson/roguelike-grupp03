package com.rougelike.magic;

import com.rougelike.Player;
import com.rougelike.races.Elf;
import com.rougelike.races.Orc;

//Alv starkare, orch svagare
// Vind är starka mot jord (dvärg) och vatten (starkare vid kombination med eld)
// Vind påverkar jord(dvärg) svagare
public class ElementAir extends MagicElementType {

    private static final double AIR_MULTIPLIER_ELF = 1.05;
    private static final double AIR_MULTIPLIER_ORC = 0.95;
    private static final String ELEMENT_TYPE = "Air";
    
        public ElementAir() {
            super(ELEMENT_TYPE);
        }
        @Override
        public final double getMultiplier(Player player) {
            double multiplierToRetrun = BASE_MULTIPLIER;
            if (player.getRace() instanceof Elf) {
               multiplierToRetrun = AIR_MULTIPLIER_ELF;
            }
            if (player.getRace() instanceof Orc) {
                multiplierToRetrun = AIR_MULTIPLIER_ORC;
            }
            return multiplierToRetrun;
        }
}
