package com.rougelike.magic;

import com.rougelike.*;
//Orch stakare, alv svagare
// Eld är stark mot vind(vind) och jord (dvärg) (starkare vid kombination med vind)
// Eld påverkar orcher svagare och mot vatten
public class ElementFire extends MagicElementType   {

    private static final String ELEMENT_TYPE = "Fire";

    public ElementFire() {
        super(ELEMENT_TYPE);
    }

    @Override
    public double getMultiplier(Player player) {
        return BASE_MULTIPLIER;
    }
    
}
