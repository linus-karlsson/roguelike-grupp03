package com.roguelike.equipment;

import org.junit.jupiter.api.*;

import com.roguelike.*;
import com.roguelike.equipment.AirWand;
import com.roguelike.equipment.Armor;
import com.roguelike.equipment.Dagger;
import com.roguelike.equipment.Equipment;
import com.roguelike.equipment.FireSword;
import com.roguelike.equipment.HeavyArmor;
import com.roguelike.equipment.LightArmor;
import com.roguelike.equipment.MediumArmor;
import com.roguelike.equipment.Shield;
import com.roguelike.equipment.Stick;
import com.roguelike.equipment.SuperiorAirWand;
import com.roguelike.equipment.SuperiorHeavyArmor;
import com.roguelike.equipment.Sword;
import com.roguelike.equipment.Tome;
import com.roguelike.equipment.Torch;
import com.roguelike.equipment.WaterDagger;
import com.roguelike.equipment.Weapon;
import com.roguelike.races.Dwarf;
import com.roguelike.races.Elf;
import com.roguelike.races.Human;
import com.roguelike.roles.Knight;
import com.roguelike.roles.Mage;
import com.roguelike.roles.Thief;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class PlayerEquipmentInteractionTest {

    @Test
    void canAddWeaponToInventory() {
        Stick stick = new Stick();
        Player player = new Player("Sven", new Point2D());

        player.addWeaponToInventory(stick);
        Weapon expected = stick;

        assertEquals(player.getWeaponInventory().get(0), expected);
    }

    @Test
    void canAddArmorToInventory() {
        LightArmor lightArmor = new LightArmor();
        Player player = new Player("Sven", new Point2D());

        player.addArmorToInventory(lightArmor);
        Armor expected = lightArmor;

        assertEquals(player.getArmorInventory().get(0), expected);
    }

    @Test
    void inventoryCapacityIsFive() {
        Player player = new Player("Sven", new Point2D());
        int expected = 5;
        assertEquals(expected, player.getMaxInventoryCapacity());
    }

    @Test
    void canNotHaveMoreThanFiveWeaponsInWeaponInventory() {
        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Point2D());

        int expected = 5;

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertEquals(expected, player.getWeaponInventory().size());
    }

    @Test
    void canNotHaveMoreThanFiveArmorsInArmorInventory() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Point2D());

        int expected = 5;

        player.addArmorToInventory(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertEquals(expected, player.getArmorInventory().size());
    }

    @Test
    void canRemoveWeaponFromWeaponInventory() {
        Stick stick = new Stick();
        Player player = new Player("Sven", new Point2D());

        player.addWeaponToInventory(stick);
        player.removeWeaponFromInventory(stick);

        int expected = 0;

        assertEquals(expected, player.getWeaponInventory().size());
    }

    @Test
    void removesCorrectWeaponFromWeaponInventory() {
        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        Player player = new Player("Sven", new Point2D());

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.removeWeaponFromInventory(stick);

        assertTrue(player.getWeaponInventory().contains(dagger));
    }

    @Test
    void removingArmorFromArmorInventoryReturnsCost() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("sven", new Point2D());

        int expected = 160;

        player.addArmorToInventory(heavyArmor);
        player.removeArmorFromInventory(heavyArmor);
        assertEquals(expected, player.getWallet());
    }

    @Test
    void correctValueStartingMoney() {
        Player player = new Player("Sven", new Point2D());

        int expected = 100;

        assertEquals(expected, player.getWallet());
    }

    @Test
    void inventoryAtCapacityMinusOne_AddsWeapon() {

        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Player player = new Player("Sven", new Point2D());

        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);

        Sword sword = new Sword();
        player.addWeaponToInventory(sword);

        assertTrue(player.getWeaponInventory().contains(sword));

    }

    @Test
    void inventoryAtCapacityMinusOne_AddsArmor() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();

        Player player = new Player("Sven", new Point2D());
        player.addArmorToInventory(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);

        assertTrue(player.getArmorInventory().contains(heavyArmor));

    }

    @Test
    void ifAtMaxCapacity_RemoveEarliestAddedWeapon() {

        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Point2D());

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertFalse(player.getWeaponInventory().contains(stick));
    }

    @Test
    void ifAtMaxCapacity_RemoveEarliestAddedArmor() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Point2D());

        player.addArmorToInventory(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertFalse(player.getArmorInventory().contains(tome));
    }

    @Test
    void removingWeaponDueToCapacityReturnsWeaponsCost() {

        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Point2D());

        int expected = player.getWallet() + stick.getPrice();

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertEquals(expected, player.getWallet());
    }

    @Test
    void removingArmorDueToCapacityReturnsArmorCost() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Point2D());

        int expected = player.getWallet() + tome.getPrice();

        player.addArmorToInventory(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertEquals(expected, player.getWallet());
    }

    @Test
    void weaponInventoryContainsNoDuplicates() {
        Stick stick = new Stick();
        Player player = new Player("Sven", new Point2D());

        int expected = 1;

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(stick);

        assertEquals(expected, player.getWeaponInventory().size());
    }

    @Test
    void cantRemoveEquippedArmor() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        player.removeArmorFromInventory(heavyArmor);
        assertTrue(player.getArmorInventory().contains(heavyArmor));
    }

    @Test
    void cantRemoveEquippedWeapon() {
        Stick stick = new Stick();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addWeaponToInventory(stick);
        player.equipWeapon(stick);
        player.removeWeaponFromInventory(stick);
        assertTrue(player.getWeaponInventory().contains(stick));
    }

    @Test
    void cantRemoveEquippedOffhand_Dagger() {
        Dagger dagger = new Dagger();
        Player player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);
        player.removeWeaponFromInventory(dagger);
        assertTrue(player.getWeaponInventory().contains(dagger));
    }

    @Test
    void addingNullToWeaponInventoryThrowsException() {
        Player player = new Player("Sven", new Point2D());
        assertThrows(IllegalArgumentException.class, () -> {
            player.addWeaponToInventory(null);
        });
    }

    @Test
    void addingNullToArmorInventoryThrowsException() {
        Player player = new Player("Sven", new Point2D());
        assertThrows(IllegalArgumentException.class, () -> {
            player.addArmorToInventory(null);
        });
    }

    @Test
    void removingNullFromWeaponInventoryThrowsException() {
        Player player = new Player("Sven", new Point2D());
        assertThrows(IllegalArgumentException.class, () -> {
            player.removeWeaponFromInventory(null);
        });
    }

    @Test
    void removingNullFromArmorInventoryThrowsException() {
        Player player = new Player("Sven", new Point2D());
        assertThrows(IllegalArgumentException.class, () -> {
            player.removeArmorFromInventory(null);
        });
    }

    @Test
    void armorInventoryContainsNoDuplicates() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Point2D());
        int expected = 1;
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(heavyArmor);

        assertEquals(expected, player.getArmorInventory().size());
    }

    @Test
    void cantRemoveEquippedWeaponWhenRemovingDueToCapacityAndRemovesNextIndexedWeaponInstead() {
        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());

        player.addWeaponToInventory(stick);
        player.equipWeapon(stick);

        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertTrue(player.getEquippedWeapon() == stick);
        assertTrue(player.getWeaponInventory().contains(stick));
        assertFalse(player.getWeaponInventory().contains(dagger));
    }

    @Test
    void removesThirdItemWhenTheFirstAndSecondWeaponAreUneligibleForRemoval() {
        WaterDagger waterDagger = new WaterDagger();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());

        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);

        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertTrue(player.getEquippedWeapon() == waterDagger);
        assertTrue(player.getEquippedOffhand() == dagger);
        assertTrue(player.getWeaponInventory().contains(waterDagger));
        assertTrue(player.getWeaponInventory().contains(dagger));
        assertFalse(player.getWeaponInventory().contains(fireSword));
        assertTrue(player.getWeaponInventory().contains(sword));
    }

    @Test
    void cantRemoveEquippedArmorWhenRemovingDueToCapacityAndRemovesNextIndexedArmorInstead() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertTrue(player.getEquippedOffhand() == tome);
        assertTrue(player.getArmorInventory().contains(tome));
        assertFalse(player.getArmorInventory().contains(shield));
    }

    @Test
    void removesThirdItemWhenTheFirstAndSecondArmorAreUneligibleForRemoval() {
        Tome tome = new Tome();
        LightArmor lightArmor = new LightArmor();
        Shield shield = new Shield();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertTrue(player.getEquippedArmor() == lightArmor);
        assertTrue(player.getEquippedOffhand() == tome);
        assertTrue(player.getArmorInventory().contains(lightArmor));
        assertTrue(player.getArmorInventory().contains(tome));
        assertFalse(player.getArmorInventory().contains(shield));
        assertTrue(player.getArmorInventory().contains(superiorHeavyArmor));
    }

    @Test
    void doesNotEquipOffhandIfNotInWeaponInventory() {
        Dagger dagger = new Dagger();
        Player player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());

        Equipment expected = null;

        player.equipOffhand(dagger);

        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfNotInArmorInventory() {
        Shield shield = new Shield();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point2D());

        Equipment expected = null;
        player.equipOffhand(shield);
        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfWrongRole() {
        Dagger dagger = new Dagger();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point2D());

        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);
        assertFalse(player.getEquippedOffhand() == dagger);
    }

    @Test
    void equipsOffhandIfCorrectRoleAndInInventory_Knight() {
        Shield shield = new Shield();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point2D());

        Equipment expected = shield;
        player.addArmorToInventory(shield);
        player.equipOffhand(shield);

        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void equipsOffhandIfCorrectRoleAndInInventory_Mage() {
        Tome tome = new Tome();
        Dwarf dwarf = new Dwarf();
        Mage mage = new Mage();
        Player player = new Player("Sven", dwarf, mage, new Point2D());

        Equipment expected = tome;
        player.addArmorToInventory(tome);
        player.equipOffhand(tome);

        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void equipsOffhandIfCorrectRoleAndInInventory_Thief() {
        Dagger dagger = new Dagger();
        Dwarf dwarf = new Dwarf();
        Thief thief = new Thief();
        Player player = new Player("Sven", dwarf, thief, new Point2D());

        Equipment expected = dagger;
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);

        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void equipsSwordIfCorrectRoleAndInInventory() {
        FireSword fireSword = new FireSword();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        Weapon expected = fireSword;
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void equipsWandIfCorrectRoleAndInInventory() {
        SuperiorAirWand superiorAirWand = new SuperiorAirWand();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());
        Weapon expected = superiorAirWand;
        player.addWeaponToInventory(superiorAirWand);
        player.equipWeapon(superiorAirWand);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void equipsDaggerIfCorrectRoleAndInInvetory() {
        WaterDagger waterDagger = new WaterDagger();
        Player player = new Player("Sven", new Elf(), new Thief(), new Point2D());
        Weapon expected = waterDagger;
        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void doesNotEquipWeaponIfWrongRole() {
        FireSword fireSword = new FireSword();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());
        Weapon expected = player.getEquippedWeapon();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void doesNotEquipWeaponIfNotInWeaponInventory() {
        FireSword fireSword = new FireSword();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.equipWeapon(fireSword);
        assertFalse(player.getEquippedWeapon() == fireSword);
    }

    @Test
    void equippingWeaponIncreasesPlayerStats() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        FireSword fireSword = new FireSword();
        double nothingEquippedStats = player.getStrength();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        double weaponEquippedStats = player.getStrength();
        assertTrue(weaponEquippedStats > nothingEquippedStats);
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Dwarf_Knight_Sword() {
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point2D());
        FireSword fireSword = new FireSword();
        double expected = (player.getStrength() + (fireSword.getStrength() * knight.getStrengthMultiplier()));
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getStrength());
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Dwarf_Knight_Club() {
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point2D());
        Stick stick = new Stick();
        double expected = (player.getStrength() + (stick.getStrength() * knight.getStrengthMultiplier()));
        player.addWeaponToInventory(stick);
        player.equipWeapon(stick);
        assertEquals(expected, player.getStrength());
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Human_Mage() {
        Mage mage = new Mage();
        Player player = new Player("Bengt", new Human(), mage, new Point2D());
        SuperiorAirWand superiorAirWand = new SuperiorAirWand();
        double expected = (player.getIntelligence()
                + (superiorAirWand.getIntelligence() * mage.getIntelligenceMultiplier()));
        player.addWeaponToInventory(superiorAirWand);
        player.equipWeapon(superiorAirWand);
        assertEquals(expected, player.getIntelligence());
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Elf_Thief() {
        Thief thief = new Thief();
        Player player = new Player("Tor", new Elf(), thief, new Point2D());
        WaterDagger waterDagger = new WaterDagger();
        double expected = (player.getDexterity() + (waterDagger.getDexterity() * thief.getDexterityMultiplier()));
        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        assertEquals(expected, player.getDexterity());
    }

    @Test
    void canUnequipWeapon() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        Torch torch = new Torch();
        player.addWeaponToInventory(torch);
        player.equipWeapon(torch);
        player.unequipWeapon();
        assertTrue(player.getEquippedWeapon() != torch);
    }

    @Test
    void unequippingWeaponReducesStats() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        Torch torch = new Torch();
        player.addWeaponToInventory(torch);
        player.equipWeapon(torch);
        double statsBeforeUnequipping = player.getStrength();
        player.unequipWeapon();
        assertTrue(player.getStrength() < statsBeforeUnequipping);
    }

    @Test
    void unequippingWeaponReducesStatsByCorrectAmount() {
        Knight knight = new Knight();
        Dwarf dwarf = new Dwarf();
        Player player = new Player("Sven", dwarf, knight, new Point2D());
        Torch torch = new Torch();
        double expected = dwarf.getStartingStrength() * knight.getStrengthMultiplier();
        player.addWeaponToInventory(torch);
        player.equipWeapon(torch);
        player.unequipWeapon();
        assertEquals(expected, player.getStrength());
    }

    @Test
    void canNotEquipArmorIfNotInInventory() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.equipArmor(heavyArmor);
        assertFalse(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Knight() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        assertTrue(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Mage() {
        LightArmor lightArmor = new LightArmor();
        Player player = new Player("Sven", new Elf(), new Mage(), new Point2D());
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        assertTrue(player.getEquippedArmor() == lightArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Thief() {
        MediumArmor mediumArmor = new MediumArmor();
        Player player = new Player("Sven", new Human(), new Thief(), new Point2D());
        player.addArmorToInventory(mediumArmor);
        player.equipArmor(mediumArmor);
        assertTrue(player.getEquippedArmor() == mediumArmor);
    }

    @Test
    void canNotEquipArmorIfWrongRole() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        assertFalse(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canUnequipArmor() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        HeavyArmor heavyArmor = new HeavyArmor();
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        player.unequipArmor();
        assertTrue(player.getEquippedArmor() == null);
    }

    @Test
    void equippingArmorIncreasesStatsByCorrectAmount() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point2D());
        double[] expected = { player.getStrength() + (heavyArmor.getStrength() * knight.getStrengthMultiplier()),
                player.getDexterity() + (heavyArmor.getDexterity() * knight.getDexterityMultiplier()),
                player.getIntelligence() + (heavyArmor.getIntelligence() * knight.getIntelligenceMultiplier()),
                player.getHealth() + heavyArmor.getHealth(), player.getMana() + heavyArmor.getMana(),
                player.getArmorValue() + heavyArmor.getArmorValue() };
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        double[] equippedStats = { player.getStrength(), player.getDexterity(), player.getIntelligence(),
                player.getHealth(), player.getMana(), player.getArmorValue() };
        assertArrayEquals(expected, equippedStats);
    }

    @Test
    void unequippingArmorDecreasesStatsByCorrectAmount() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        double[] expected = { player.getStrength() - (heavyArmor.getStrength() * knight.getStrengthMultiplier()),
                player.getDexterity() - (heavyArmor.getDexterity() * knight.getDexterityMultiplier()),
                player.getIntelligence() - (heavyArmor.getIntelligence() * knight.getIntelligenceMultiplier()),
                player.getHealth() - heavyArmor.getHealth(), player.getMana() - heavyArmor.getMana(),
                player.getArmorValue() - heavyArmor.getArmorValue() };
        player.unequipArmor();
        double[] actual = { player.getStrength(), player.getDexterity(), player.getIntelligence(),
                player.getHealth(), player.getMana(), player.getArmorValue() };
        assertArrayEquals(expected, actual);
    }

    // Matcher som undersöker om värden i två olika arrayer är lika.
    // Utöver förbättrad läsbarhet så kan man se alla värden i arrayen samtidigt
    // ifall den misslyckas
    // vilket gör att man kan se ifall det var flera värden som inte stämde överens
    // under samma testkörning
    @Test
    void equippingArmorIncreasesStatsByCorrectAmount_Matcher() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point2D());
        double[] expected = { player.getStrength() + (heavyArmor.getStrength() * knight.getStrengthMultiplier()),
                player.getDexterity() + (heavyArmor.getDexterity() * knight.getDexterityMultiplier()),
                player.getIntelligence() + (heavyArmor.getIntelligence() * knight.getIntelligenceMultiplier()),
                player.getHealth() + heavyArmor.getHealth(), player.getMana() + heavyArmor.getMana(),
                player.getArmorValue() + heavyArmor.getArmorValue() };
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        double[] equippedStats = { player.getStrength(), player.getDexterity(), player.getIntelligence(),
                player.getHealth(), player.getMana(), player.getArmorValue() };
        assertThat(equippedStats, is(equalTo(expected)));
    }

    @Test
    void unequippingArmorDecreasesStatsByCorrectAmount_Matcher() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        double[] expected = { player.getStrength() - (heavyArmor.getStrength() * knight.getStrengthMultiplier()),
                player.getDexterity() - (heavyArmor.getDexterity() * knight.getDexterityMultiplier()),
                player.getIntelligence() - (heavyArmor.getIntelligence() * knight.getIntelligenceMultiplier()),
                player.getHealth() - heavyArmor.getHealth(), player.getMana() - heavyArmor.getMana(),
                player.getArmorValue() - heavyArmor.getArmorValue() };
        player.unequipArmor();
        double[] actual = { player.getStrength(), player.getDexterity(), player.getIntelligence(),
                player.getHealth(), player.getMana(), player.getArmorValue() };
        assertThat(actual, is(equalTo(expected)));
    }

    // Ger ett tydligare felmeddelande.
    // Expected: a collection with size[x]
    // but: collection size was [y]
    // istället för att bara ge siffror
    @Test
    void canNotHaveMoreThanFiveWeaponsInWeaponInventory_Matcher() {
        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Point2D());

        int expected = 5;

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertThat(player.getWeaponInventory(), hasSize(expected));
    }

    // Kan undersöka om en lista innehåller alla element oavsett ordning.
    @Test
    void removesThirdItemWhenTheFirstAndSecondArmorAreUneligibleForRemoval_Matcher() {
        Tome tome = new Tome();
        LightArmor lightArmor = new LightArmor();
        Shield shield = new Shield();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertThat(player.getArmorInventory(),
                containsInAnyOrder(tome, lightArmor, superiorHeavyArmor, heavyArmor, mediumArmor));
    }

    // Något bättre läsbarhet än assertTrue (x>y)
    @Test
    void equippingWeaponIncreasesPlayerStats_Matcher() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        FireSword fireSword = new FireSword();
        double nothingEquippedStats = player.getStrength();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        double weaponEquippedStats = player.getStrength();
        assertThat(weaponEquippedStats, greaterThan(nothingEquippedStats));
    }

}
