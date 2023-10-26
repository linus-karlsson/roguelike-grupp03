package com.roguelike.roles;

import static org.junit.jupiter.api.Assertions.*;

import com.roguelike.equipment.EquipmentType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class RoleTest {
    EquipmentType equipmentType;
    @Test
    public void testRoleInstanceCreation() {
        Knight knight = new Knight();
        assertEquals(1.5, knight.getStrengthMultiplier());
    }

    @Test
    public void testRoleGetEligibleWeapons() {
        Knight knight = new Knight();
        Set<EquipmentType> expectedKnightEligibleWeapons = new HashSet<>();

        expectedKnightEligibleWeapons.add(EquipmentType.SWORD);
        expectedKnightEligibleWeapons.add(EquipmentType.CLUB);
        expectedKnightEligibleWeapons.add(EquipmentType.HEAVY_ARMOR);
        expectedKnightEligibleWeapons.add(EquipmentType.MEDIUM_ARMOR);
        expectedKnightEligibleWeapons.add(EquipmentType.LIGHT_ARMOR);
        expectedKnightEligibleWeapons.add(EquipmentType.SHIELD);

        Set<EquipmentType> actualKnightEligibleWeapons = knight.getEligibleEquipment();

        assertTrue(actualKnightEligibleWeapons.containsAll(expectedKnightEligibleWeapons));
    }

}
