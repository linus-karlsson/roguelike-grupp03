package com.rougelike.equipment;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {

    @Test
    public void testStickDamage() {
        Stick stick = new Stick();
        int expectedDamage = 3;
        assertEquals(expectedDamage, stick.getDamage());
    }

    @Test
    public void testTorchDamage_NoResistances() {
        Torch torch = new Torch();
        int expectedDamage = 5;
        assertEquals(expectedDamage, torch.getDamage() + torch.getElementalDamage());
    }
}
