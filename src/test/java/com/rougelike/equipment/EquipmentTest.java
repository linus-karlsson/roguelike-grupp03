package com.rougelike.equipment;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EquipmentTest {

    @Test
    void getDamageReturnsCorrectValue() {
        Stick stick = new Stick();
        int expectedDamage = 3;
        assertEquals(expectedDamage, stick.getDamage());
    }

    @Test
    void getElementalDamageReturnsCorrectValue() {
        WaterDagger waterDagger = new WaterDagger();
        int expectedDamage = 6;
        assertEquals(expectedDamage, waterDagger.getElementalDamage());
    }

    @Test
    void getDamageAndGetElementalDamageAddsCorrectly() {
        Torch torch = new Torch();
        int sumExpectedDamage = 5;
        assertEquals(sumExpectedDamage, torch.getDamage() + torch.getElementalDamage());
    }

    @Test
    void isSubclassOfWeapon() {
        Class<?> subClass = FireSword.class;
        Class<?> superClass = Weapon.class;

        boolean isSubclass = superClass.isAssignableFrom(subClass);

        assertTrue(isSubclass);
    }

    @Test
    void getNameReturnsCorrectName() {
        FireSword fireSword = new FireSword();
        String expected = "Fire Sword";
        assertEquals(expected, fireSword.getName());
    }

    @Test
    void fireSwordHasAnEquipmentType() {
        FireSword fireSword = new FireSword();

        assertTrue(fireSword.getType() != null);
    }

    @Test
    void fireSwordHasCorrectEquipmentType() {
        FireSword fireSword = new FireSword();
        EquipmentType expected = EquipmentType.SWORD;

        assertEquals(expected, fireSword.getType());
    }

    @Test
    void fireSwordHasAnElement() {
        FireSword fireSword = new FireSword();

        assertTrue(fireSword.getWeaponElementType() != null);
    }

    @Test
    void fireSwordHasCorrectElement() {
        FireSword fireSword = new FireSword();
        ElementType expected = ElementType.FIRE;

        assertEquals(expected, fireSword.getWeaponElementType());
    }

    @Test
    void fireSwordHasCorrectStrength() {
        FireSword fireSword = new FireSword();
        assertEquals(8, fireSword.getStrength());
    }

    @Test
    void daggerHasCorrectDexterity() {
        Dagger dagger = new Dagger();
        assertEquals(9, dagger.getDexterity());
    }

    @Test
    void airWandHasCorrectIntelligence() {
        AirWand airWand = new AirWand();
        assertEquals(15, airWand.getIntelligence());
    }

    @Test
    void earthHammerHasCorrectPrice() {
        EarthHammer earthHammer = new EarthHammer();
        assertEquals(90, earthHammer.getPrice());
    }

    @Test
    void noNameInConstructorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new DummyEquipment(null, 0, null, 0, 0, 0));
    }

    @Test
    void noTypeInConstructorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new DummyEquipment("Name", 0, null, 0, 0, 0));
    }

    @Test
    void waterDagger_AllValuesAreCorrect() {
        WaterDagger waterDagger = new WaterDagger();
        assertEquals("Water Dagger", waterDagger.getName());
        assertEquals(60, waterDagger.getPrice());
        assertEquals(EquipmentType.DAGGER, waterDagger.getType());
        assertEquals(0, waterDagger.getStrength());
        assertEquals(13, waterDagger.getDexterity());
        assertEquals(0, waterDagger.getIntelligence());
        assertEquals(2, waterDagger.getDamage());
        assertEquals(6, waterDagger.getElementalDamage());
        assertEquals(ElementType.WATER, waterDagger.getWeaponElementType());
    }

    @Test
    void heavyArmor_AllValuesAreCorrect() {
        HeavyArmor heavyArmor = new HeavyArmor();
        assertEquals("Heavy Armor", heavyArmor.getName());
        assertEquals(60, heavyArmor.getPrice());
        assertEquals(EquipmentType.HEAVY_ARMOR, heavyArmor.getType());
        assertEquals(5, heavyArmor.getStrength());
        assertEquals(0, heavyArmor.getDexterity());
        assertEquals(0, heavyArmor.getIntelligence());
        assertEquals(100, heavyArmor.getHealth());
        assertEquals(20, heavyArmor.getMana());
        assertEquals(10, heavyArmor.getArmorValue());
        assertEquals(ElementType.EARTH, heavyArmor.getElementType());
    }

    @Test
    void isSubclassOfWeapon_Matcher() {
        assertThat(Weapon.class, typeCompatibleWith(Equipment.class));
    }

}
