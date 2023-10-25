package com.roguelike.magic;

import com.roguelike.races.*;

public final class RaceImpactChecker {

    static boolean isPlayerImpact(Race race, MagicElementType elementType) {
        boolean impactByFireAndAir = ((race instanceof Elf || race instanceof Orc) && (elementType instanceof ElementAir
                || elementType instanceof ElementFire));
        boolean impactByWaterAndEarth = ((race instanceof Dwarf || race instanceof Orc)
                && (elementType instanceof ElementEarth
                        || elementType instanceof ElementWater));
        return impactByFireAndAir || impactByWaterAndEarth;

    }
}
