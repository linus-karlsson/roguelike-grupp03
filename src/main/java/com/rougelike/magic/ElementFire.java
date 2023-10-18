package com.rougelike.magic;

import com.rougelike.*;
//Orch stakare, alv svagare
// Eld är stark mot vind(vind) och jord (dvärg) (starkare vid kombination med vind)
// Eld påverkar orcher svagare och mot vatten
public class ElementFire extends MagicElementType   {

    public ElementFire() {
        super("Fire");
    }

    @Override
    public double getMultiplier(Player player) {
        return BASE_MULTIPLIER;
    }
    
}
