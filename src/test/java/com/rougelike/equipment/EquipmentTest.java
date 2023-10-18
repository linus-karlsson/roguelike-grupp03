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

    // Test på FireSword
    Weapon dummyWeapon = new Sword();

    @Test
    public void isSubclassOfWeapon() {
        Class<?> subClass = FireSword.class;
        Class<?> superClass = Weapon.class;

        boolean isSubclass = superClass.isAssignableFrom(subClass);

        assertTrue(isSubclass);
    }

    @Test
    public void fireSwordHasCorrectName() {
        FireSword fireSword = new FireSword();
        String expected = "Fire Sword";
        assertEquals(expected, fireSword.getName());
    }

    @Test
    public void fireSwordHasAnEquipmentType() {
        FireSword fireSword = new FireSword();

        assertTrue(fireSword.getType() != null);
    }

    @Test
    public void fireSwordHasCorrectEquipmentType() {
        FireSword fireSword = new FireSword();
        EquipmentType expected = EquipmentType.SWORD;

        assertEquals(expected, fireSword.getType());
    }

    @Test
    public void fireSwordHasAnElement() {
        FireSword fireSword = new FireSword();

        assertTrue(fireSword.getWeaponElementType() != null);
    }

    @Test
    public void fireSwordHasCorrectElement() {
        FireSword fireSword = new FireSword();
        ElementType expected = ElementType.FIRE;

        assertEquals(expected, fireSword.getWeaponElementType());
    }

    @Test
    public void fireSwordHasCorrectStrength() {
        FireSword fireSword = new FireSword();
        assertEquals(8, fireSword.getStrength());

    }
}
